package com.efrei.game_haven_backend.domain.avis;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import com.efrei.game_haven_backend.infrastructure.AvisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvisService {

    @Autowired
    private AvisRepository avisRepository;

    public Avis save(Avis avis) {
        return avisRepository.save(avis);
    }

    public List<Avis> findAll() {
        return avisRepository.findAll();
    }

    public Page<Avis> getAvisByJeux(Jeux jeux, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return avisRepository.findByJeux(jeux, pageable);
    }
}
