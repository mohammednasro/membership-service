package com.appswave.membership.common.filter;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final EntityManager entityManager;

    public void enableDeletedFilter(boolean includeDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", includeDeleted);
    }
}
