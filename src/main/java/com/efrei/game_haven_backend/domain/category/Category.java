package com.efrei.game_haven_backend.domain.category;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // Changement de int à long

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Jeux> jeuxList;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Jeux> getJeuxList() {
        return jeuxList;
    }

    public void setJeuxList(List<Jeux> jeuxList) {
        this.jeuxList = jeuxList;
    }
}
