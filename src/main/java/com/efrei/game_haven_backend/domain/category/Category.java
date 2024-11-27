package com.efrei.game_haven_backend.domain.category;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;


    @OneToMany(targetEntity = Jeux.class, mappedBy = "category")
    @JsonIgnore
    private List<Jeux> jeuxList;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
