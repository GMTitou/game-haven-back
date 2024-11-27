package com.efrei.game_haven_backend.domain.jeux;

import com.efrei.game_haven_backend.domain.category.Category;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Jeux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private int reference;

    @Column(nullable = false, unique = true)
    private String nom;

    @ManyToOne
    @JoinColumn(nullable = false, name = "idCategory")
    private Category category;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float prix;

    @Column(nullable = false)
    private int quantite;

    @Column(nullable = false)
    private Date dateAjout;

    public Jeux() {}

    public Jeux(long id, int reference, String nom, Category category, String image, String description, Float prix, int quantite, Date dateAjout) {
        this.id = id;
        this.reference = reference;
        this.nom = nom;
        this.category = category;
        this.image = image;
        this.description = description;
        this.prix = prix;
        this.quantite = quantite;
        this.dateAjout = dateAjout;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }
}
