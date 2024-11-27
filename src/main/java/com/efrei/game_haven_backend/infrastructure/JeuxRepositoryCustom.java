package com.efrei.game_haven_backend.infrastructure;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JeuxRepositoryCustom {
    Page<Jeux> findByCriteria(String searchTerm, Pageable pageable);
}
