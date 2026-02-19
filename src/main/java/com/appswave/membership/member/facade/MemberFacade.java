package com.appswave.membership.member.facade;

import com.appswave.membership.member.dto.MemberFilter;
import com.appswave.membership.member.dto.MemberRequest;
import com.appswave.membership.member.dto.MemberResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MemberFacade {

    MemberResponse create(MemberRequest request);
    MemberResponse getById(UUID id);
    MemberResponse update(UUID id, MemberRequest request);
    MemberResponse patch(UUID id, MemberRequest request);
    void delete(UUID id);
    Page<MemberResponse> search(MemberFilter filter);
}
