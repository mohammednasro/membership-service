package com.appswave.membership.member.facade.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appswave.membership.member.dto.MemberFilter;
import com.appswave.membership.member.dto.MemberRequest;
import com.appswave.membership.member.dto.MemberResponse;
import com.appswave.membership.member.entity.Member;
import com.appswave.membership.member.facade.MemberFacade;
import com.appswave.membership.member.mapper.MemberMapper;
import com.appswave.membership.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacadeImpl implements MemberFacade {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @Override
    public MemberResponse create(MemberRequest request) {
        Member saved = memberService.create( memberMapper.toEntity(request));
        log.info("Created member with id={} email={}", saved.getId(), saved.getEmail());
        return memberMapper.toResponse(saved);
    }

    @Override
    public MemberResponse getById(UUID id) {
        return memberMapper.toResponse(memberService.getById(id));
    }


    @Override
    public MemberResponse update(UUID id, MemberRequest request) {
        Member member = new Member();

        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
        member.setMobileNumber(request.getMobileNumber());
        member.setGender(request.getGender());
        member.setMembershipType(request.getMembershipType());
        member.setPersona(request.getPersona());
        
        return memberMapper.toResponse( memberService.update(id, member));
    }
 
    @Override
    public MemberResponse patch(UUID id, MemberRequest request) {
        Member member = new Member();
        Optional.ofNullable(request.getFirstName()).ifPresent(member::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(member::setLastName);
        Optional.ofNullable(request.getEmail()).ifPresent(member::setEmail);
        Optional.ofNullable(request.getMobileNumber()).ifPresent(member::setMobileNumber);
        Optional.ofNullable(request.getGender()).ifPresent(member::setGender);
        Optional.ofNullable(request.getMembershipType()).ifPresent(member::setMembershipType);
        Optional.ofNullable(request.getPersona()).ifPresent(member::setPersona);
        return memberMapper.toResponse( memberService.update(id, member));
    }

    @Override
    public void delete(UUID id) {
    	memberService.delete(id);

    }


    @Override
    public Page<MemberResponse> search(MemberFilter filter) {
        return memberService.search(filter).map(memberMapper::toResponse);
    }
}
