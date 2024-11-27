package com.efrei.game_haven_backend.domain.jeux;

import com.efrei.game_haven_backend.infrastructure.JeuxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class JeuxService {

    @Autowired
    private JeuxRepository jeuxRepository;

    public Jeux create(Jeux jeux) {
        Random random = new Random();
        int reference = 10000000 + random.nextInt(90000000);
        jeux.setReference(reference);
        return jeuxRepository.save(jeux);
    }

    public Page<Jeux> getJeux(Pageable pageable) {
        return jeuxRepository.findAll(pageable);
    }
}
