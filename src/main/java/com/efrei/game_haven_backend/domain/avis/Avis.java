package com.efrei.game_haven_backend.domain.avis;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import com.efrei.game_haven_backend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column()
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "jeux_id", nullable = false)
    @JsonBackReference
    private Jeux jeux;


    public Avis() {}

    public Avis(long id, String contenu, User user, Jeux jeux) {
        this.id = id;
        this.contenu = contenu;
        this.user = user;
        this.jeux = jeux;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Jeux getJeux() {
        return jeux;
    }

    public void setJeux(Jeux jeux) {
        this.jeux = jeux;
    }
}
