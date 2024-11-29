package com.efrei.game_haven_backend.domain.jeux;

import com.efrei.game_haven_backend.domain.Category;
import com.efrei.game_haven_backend.domain.Etat;
import com.efrei.game_haven_backend.domain.avis.Avis;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Jeux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private int reference;

    @Column(nullable = false, unique = true)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, length = 500)
    private String image;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private Float prix;

    @Column(nullable = false)
    private int quantite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Etat etat;

    @Column(nullable = false)
    private Float note;

    @Column(nullable = false)
    private Date dateAjout;

    @OneToMany(mappedBy = "jeux", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Avis> avis;


    public Jeux() {}

    public Jeux(long id, int reference, String nom, Category category, String image, String description, Float prix, int quantite, Etat etat, Float note, Date dateAjout, List<Avis> avis) {
        this.id = id;
        this.reference = reference;
        this.nom = nom;
        this.category = category;
        this.image = image;
        this.description = description;
        this.prix = prix;
        this.quantite = quantite;
        this.etat = etat;
        this.note = note;
        this.dateAjout = dateAjout;
        this.avis = avis;
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

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Float getNote() {
        return note;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public List<Avis> getAvis() {
        return avis;
    }

    public void setAvis(List<Avis> avis) {
        this.avis = avis;
    }
}
