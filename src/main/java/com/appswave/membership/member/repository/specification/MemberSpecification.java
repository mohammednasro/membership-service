package com.appswave.membership.member.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.appswave.membership.member.dto.MemberFilter;
import com.appswave.membership.member.entity.Member;

import jakarta.persistence.criteria.Predicate;

public class MemberSpecification {

    public static Specification<Member> build(MemberFilter filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();


            if (filter.getFirstName() != null && !filter.getFirstName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + filter.getFirstName().toLowerCase() + "%"));
            }
            if (filter.getLastName() != null && !filter.getLastName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + filter.getLastName().toLowerCase() + "%"));
            }
            if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }
            if (filter.getMobileNumber() != null && !filter.getMobileNumber().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("mobileNumber")), "%" + filter.getMobileNumber().toLowerCase() + "%"));
            }


            if (filter.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), filter.getGender()));
            }
            if (filter.getMembershipType() != null) {
                predicates.add(cb.equal(root.get("membershipType"), filter.getMembershipType()));
            }
            if (filter.getPersona() != null) {
                predicates.add(cb.equal(root.get("persona"), filter.getPersona()));
            }


            predicates.add(cb.isFalse(root.get("deleted")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
