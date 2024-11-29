package com.efrei.game_haven_backend.infrastructure;

import com.efrei.game_haven_backend.domain.avis.Avis;
import com.efrei.game_haven_backend.domain.jeux.Jeux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {

    Page<Avis> findByJeux(Jeux jeux, Pageable pageable);
}
