package com.efrei.game_haven_backend.infrastructure;

import com.efrei.game_haven_backend.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
