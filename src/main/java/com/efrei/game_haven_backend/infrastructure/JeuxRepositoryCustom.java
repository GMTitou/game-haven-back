package com.efrei.game_haven_backend.infrastructure;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface JeuxRepositoryCustom {

    @Query(value = """
    SELECT j.* 
    FROM jeux j 
    LEFT JOIN category c ON c.id = j.id_category 
    WHERE 
        LOWER(CAST(j.reference AS VARCHAR)) LIKE :searchTerm
        OR LOWER(CAST(j.prix AS VARCHAR)) LIKE :searchTerm
        OR LOWER(j.nom) LIKE :searchTerm
        OR LOWER(j.description) LIKE :searchTerm
        OR LOWER(c.name) LIKE :searchTerm
    """, nativeQuery = true)
    Page<Jeux> findByCriteria(String searchTerm, Pageable pageable);
}
