package com.efrei.game_haven_backend.domain.jeux;

import com.efrei.game_haven_backend.domain.Etat;
import com.efrei.game_haven_backend.infrastructure.JeuxRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return jeuxRepository.save(jeux);
    }

//    public Page<Jeux> getJeux(Pageable pageable) {
//        return jeuxRepository.findAll(pageable);
//    }

    public Page<Jeux> getJeux(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return jeuxRepository.findAll(pageable);
        }
        return jeuxRepository.findByCriteria(searchTerm, pageable);
    }
}
