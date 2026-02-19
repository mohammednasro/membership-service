package com.appswave.membership.member.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appswave.membership.common.filter.FilterService;
import com.appswave.membership.member.dto.MemberFilter;
import com.appswave.membership.member.entity.Member;
import com.appswave.membership.member.exception.MemberException;
import com.appswave.membership.member.exception.enums.MemberExceptionType;
import com.appswave.membership.member.repository.MemberRepository;
import com.appswave.membership.member.repository.specification.MemberSpecification;
import com.appswave.membership.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final FilterService filterService;

	@Override
	@Transactional
	public Member create(Member member) {
		Member saved = memberRepository.save(member);
		log.info("Created member with id={} email={}", saved.getId(), saved.getEmail());

		return saved;
	}

	@Override
	@Transactional(readOnly = true)
	public Member getById(UUID id) {
		filterService.enableDeletedFilter(false);
		return memberRepository.findById(id).orElseThrow(() -> {
			log.warn("Member not found, id={}", id);
			return new MemberException(MemberExceptionType.NOT_FOUND);
		});
	}

	@Override
	@Transactional
	public Member update(UUID id, Member member) {
		filterService.enableDeletedFilter(false);
		Member existing = getById(id);

		existing.setFirstName(member.getFirstName());
		existing.setLastName(member.getLastName());
		existing.setEmail(member.getEmail());
		existing.setMobileNumber(member.getMobileNumber());
		existing.setGender(member.getGender());
		existing.setMembershipType(member.getMembershipType());
		existing.setPersona(member.getPersona());
		Member saved = memberRepository.save(existing);
		log.info("Updated member id={}", saved.getId());

		return saved;
	}

	@Override
	@Transactional
	public void delete(UUID id) {
		filterService.enableDeletedFilter(false);
		Member existing = getById(id);
		memberRepository.delete(existing);
		log.info("Soft deleted member id={}", id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Member> search(MemberFilter filter) {
		filterService.enableDeletedFilter(false);

		Specification<Member> spec = MemberSpecification.build(filter);

        Sort.Direction direction = Sort.Direction.fromString(filter.getSortDirection());
        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(),
                Sort.by(direction, filter.getSortBy()));
        Page<Member> page = memberRepository.findAll(spec, pageRequest);

		log.info("Searched members: page={}, size={}, total={}", filter.getPage(), filter.getSize(),
				page.getTotalElements());

		return page;
	}
}
