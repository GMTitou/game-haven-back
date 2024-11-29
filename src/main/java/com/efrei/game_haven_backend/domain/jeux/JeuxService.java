package com.efrei.game_haven_backend.domain.jeux;

import com.efrei.game_haven_backend.infrastructure.JeuxRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class JeuxService {

    @Autowired
    private JeuxRepository jeuxRepository;

    @Autowired
    private EntityManager entityManager;

    public Jeux create(Jeux jeux) {
        Random random = new Random();
        int reference = 10000000 + random.nextInt(90000000);
        jeux.setReference(reference);
        if (jeux.getDateAjout() == null) {
            jeux.setDateAjout(new Date());
        }
        return jeuxRepository.save(jeux);
    }

    public Page<Jeux> findJeuxByCriteria(String searchTerm, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Jeux> criteria = builder.createQuery(Jeux.class);
        Root<Jeux> root = criteria.from(Jeux.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchTerm != null && !searchTerm.isEmpty()) {
            String likePattern = "%" + searchTerm.toLowerCase() + "%";

            predicates.add(builder.or(
                    builder.like(builder.lower(root.get("nom")), likePattern),
                    builder.like(builder.lower(root.get("etat")), likePattern),
                    builder.like(builder.lower(root.get("category")), likePattern),
                    builder.like(
                            builder.function("to_char", String.class, root.get("reference"), builder.literal("9999999999")),
                            likePattern
                    ),
                    builder.like(
                            builder.function("to_char", String.class, root.get("prix"), builder.literal("99999999D99")),
                            likePattern
                    ),
                    builder.like(
                            builder.function("to_char", String.class, root.get("note"), builder.literal("999")),
                            likePattern
                    ),
                    builder.like(
                            builder.function("to_char", String.class, root.get("quantite"), builder.literal("9999")),
                            likePattern
                    )
            ));
        }

        // Application des prédicats
        criteria.where(predicates.toArray(new Predicate[0]));

        // Exécution de la requête paginée
        TypedQuery<Jeux> query = entityManager.createQuery(criteria);
        int totalRows = query.getResultList().size();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Jeux> result = query.getResultList();

        entityManager.close();
        return new PageImpl<>(result, pageable, totalRows);
    }

    public Jeux findJeuByReference(int reference) {
        return jeuxRepository.findByReference(reference);
    }

    public Jeux updateJeuxByReference(int reference, Jeux updatedJeux) {
        Jeux existingJeux = jeuxRepository.findByReference(reference);

        if (existingJeux == null) {
            throw new RuntimeException("Jeu avec la référence " + reference + " non trouvé.");
        }

        existingJeux.setNom(updatedJeux.getNom());
        existingJeux.setImage(updatedJeux.getImage());
        existingJeux.setDescription(updatedJeux.getDescription());
        existingJeux.setPrix(updatedJeux.getPrix());
        existingJeux.setQuantite(updatedJeux.getQuantite());
        existingJeux.setEtat(updatedJeux.getEtat());
        existingJeux.setCategory(updatedJeux.getCategory());
        existingJeux.setNote(updatedJeux.getNote());
        existingJeux.setDateAjout(new Date());

        return jeuxRepository.save(existingJeux);
    }
}
