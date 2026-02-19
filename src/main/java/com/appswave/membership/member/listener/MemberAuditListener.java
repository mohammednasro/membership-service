package com.appswave.membership.member.listener;

import org.slf4j.MDC;

import com.appswave.membership.member.entity.Member;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberAuditListener {

    @PrePersist
    public void prePersist(Member member) {
        log.info("[AUDIT] Creating Member: firstName={}, lastName={}, email={}",
                member.getFirstName(), member.getLastName(), member.getEmail());
    }

    @PreUpdate
    public void preUpdate(Member member) {

        String correlationId = MDC.get("correlationId");
        String userId = MDC.get("userId");
        String email = MDC.get("email");
        String role = MDC.get("role");

        if (member.isDeleted()) {
            log.info("[AUDIT] Soft Deleting Member: firstName={}, lastName={}, email={}, isDeleted={}, userId={}, emailLogged={}, role={}, correlationId={}",
                    member.getFirstName(), member.getLastName(), member.getEmail(),
                    member.isDeleted(), userId, email, role, correlationId);
        } else {
            log.info("[AUDIT] Updating Member: firstName={}, lastName={}, email={}, userId={}, emailLogged={}, role={}, correlationId={}",
                    member.getFirstName(), member.getLastName(), member.getEmail(),
                    userId, email, role, correlationId);
        }
    }
}
