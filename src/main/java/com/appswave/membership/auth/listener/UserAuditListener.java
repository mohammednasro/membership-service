package com.appswave.membership.auth.listener;

import com.appswave.membership.auth.entity.User;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuditListener {

    @PrePersist
    public void prePersist(User user) {
        log.info("[AUDIT] Creating User: email={}, firstName={}, lastName={}",
                user.getEmail(), user.getFirstName(), user.getLastName());
    }

    @PreUpdate
    public void preUpdate(User user) {
    	
        if (user.isDeleted()) {
            log.info("[AUDIT] Soft Deleting User: id={}, email={}, isDeleted={}",
                    user.getId(), user.getEmail(), user.isDeleted());
        } else {
            log.info("[AUDIT] Updating User: id={}, email={}, firstName={}, lastName={}",
                    user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
        }
    }

    @PreRemove
    public void preRemove(User user) {
        log.info("[AUDIT] Deleting User (soft): id={}, email={}", user.getId(), user.getEmail());
    }
}
