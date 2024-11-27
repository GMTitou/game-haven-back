package com.efrei.game_haven_backend.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import com.efrei.game_haven_backend.domain.jeux.Jeux;
import org.springframework.data.jpa.repository.query.QueryUtils;

public class JeuxRepositoryImpl implements JeuxRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Jeux> findByCriteria(String searchTerm, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Jeux> query = cb.createQuery(Jeux.class);
        Root<Jeux> jeuxRoot = query.from(Jeux.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchTerm != null && !searchTerm.isEmpty()) {
            String likePattern = "%" + searchTerm.toLowerCase() + "%";

            predicates.add(cb.like(cb.lower(jeuxRoot.get("nom")), likePattern));
            predicates.add(cb.like(cb.lower(jeuxRoot.get("description")), likePattern));
            predicates.add(cb.like(cb.lower(jeuxRoot.get("image")), likePattern));

            Join<Object, Object> categoryJoin = jeuxRoot.join("category", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(categoryJoin.get("name")), likePattern));

            predicates.add(cb.like(cb.function("CAST", String.class, jeuxRoot.get("reference")), likePattern));
            predicates.add(cb.like(cb.function("CAST", String.class, jeuxRoot.get("prix")), likePattern));
            predicates.add(cb.like(cb.function("CAST", String.class, jeuxRoot.get("quantite")), likePattern));
            predicates.add(cb.like(cb.function("CAST", String.class, jeuxRoot.get("note")), likePattern));

            predicates.add(cb.like(cb.lower(cb.function("CAST", String.class, jeuxRoot.get("etat"))), likePattern));
        }

        query.where(cb.or(predicates.toArray(new Predicate[0])));
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), jeuxRoot, cb));

        List<Jeux> jeuxList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Jeux> countRoot = countQuery.from(Jeux.class);
        countQuery.select(cb.count(countRoot)).where(cb.or(predicates.toArray(new Predicate[0])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(jeuxList, pageable, count);
    }

}
