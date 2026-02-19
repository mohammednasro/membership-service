package com.appswave.membership.member.service;

import com.appswave.membership.member.dto.MemberFilter;
import com.appswave.membership.member.entity.Member;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MemberService {

    Member create(Member member);
    Member getById(UUID id);
    Member update(UUID id, Member member);
    void delete(UUID id);
    Page<Member> search(MemberFilter filter);
}
