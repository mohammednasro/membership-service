package com.appswave.membership.member.repository;

import com.appswave.membership.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID>, JpaSpecificationExecutor<Member> {

    Optional<Member> findByEmailAndDeletedFalse(String email);

    Optional<Member> findByIdAndDeletedFalse(UUID id);

    boolean existsByEmailAndDeletedFalse(String email);

}
