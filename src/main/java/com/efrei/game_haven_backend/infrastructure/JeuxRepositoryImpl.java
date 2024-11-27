package com.efrei.game_haven_backend.infrastructure;

import com.efrei.game_haven_backend.domain.category.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

        // Racine pour la table Jeux
        Root<Jeux> jeuxRoot = query.from(Jeux.class);

        // Jointure avec Category
        Join<Jeux, Category> categoryJoin = jeuxRoot.join("category", JoinType.LEFT);

        // Liste des prédicats
        List<Predicate> predicates = new ArrayList<>();

        // Modèle pour le LIKE
        String likePattern = "%" + searchTerm.toLowerCase() + "%";

        // Ajout des prédicats pour les champs de la table Jeux
        predicates.add(cb.like(cb.lower(jeuxRoot.get("nom")), likePattern));
        predicates.add(cb.like(cb.lower(jeuxRoot.get("description")), likePattern));
        predicates.add(cb.like(cb.lower(jeuxRoot.get("image")), likePattern));
        predicates.add(
                cb.like(
                        cb.lower(cb.function("CAST", String.class, jeuxRoot.get("reference"), cb.literal("varchar"))),
                        likePattern
                )
        );
        predicates.add(
                cb.like(
                        cb.lower(cb.function("CAST", String.class, jeuxRoot.get("prix"), cb.literal("varchar"))),
                        likePattern
                )
        );

        // Ajout du prédicat pour le champ "name" de la table Category
        predicates.add(cb.like(cb.lower(categoryJoin.get("name")), likePattern));

        // Ajout des prédicats au WHERE avec OR
        query.where(cb.or(predicates.toArray(new Predicate[0])));

        // Ajout d'un ordre (tri par id, par exemple)
        query.orderBy(cb.asc(jeuxRoot.get("id")));

        // Création de la requête
        TypedQuery<Jeux> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Récupération des résultats
        List<Jeux> resultList = typedQuery.getResultList();

        // Construction de la requête pour compter les résultats totaux
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Jeux> countRoot = countQuery.from(Jeux.class);
        Join<Jeux, Category> countJoin = countRoot.join("category", JoinType.LEFT);
        countQuery.select(cb.count(countRoot))
                .where(cb.or(predicates.toArray(new Predicate[0])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        // Retourne la page
        return new PageImpl<>(resultList, pageable, count);
    }
}