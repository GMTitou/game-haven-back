package com.efrei.game_haven_backend.infrastructure;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JeuxRepository extends JpaRepository<Jeux, Integer>, JeuxRepositoryCustom {
}
