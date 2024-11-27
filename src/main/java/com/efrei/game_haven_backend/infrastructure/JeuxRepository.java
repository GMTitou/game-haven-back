package com.efrei.game_haven_backend.infrastructure;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JeuxRepository extends JpaRepository<Jeux, Integer> {

    @Query("SELECT j FROM Jeux j WHERE j.nom LIKE %:searchTerm%")
    Page<Jeux> pageableJeux(String searchTerm, Pageable pageable);
}
