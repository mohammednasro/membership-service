package com.appswave.membership.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.appswave.membership.member.dto.MemberRequest;
import com.appswave.membership.member.dto.MemberResponse;
import com.appswave.membership.member.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member toEntity(MemberRequest request);

    MemberResponse toResponse(Member member);
}
