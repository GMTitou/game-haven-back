package com.efrei.game_haven_backend.exposition;

import com.efrei.game_haven_backend.domain.avis.Avis;
import com.efrei.game_haven_backend.domain.avis.AvisService;
import com.efrei.game_haven_backend.domain.jeux.Jeux;
import com.efrei.game_haven_backend.domain.jeux.JeuxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avis")
public class AvisController {

    @Autowired
    private AvisService avisService;
    @Autowired
    private JeuxService jeuxService;

    @PostMapping("/create")
    public Avis createAvis(@RequestBody Avis avis) {
        return avisService.save(avis);
    }

    @GetMapping("/findAll")
    public List<Avis> findAll() {
        return avisService.findAll();
    }

    @GetMapping("/jeu/{reference}/pageable")
    public Page<Avis> getAvisByJeuxPaginated(
            @PathVariable int reference,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Jeux jeux = jeuxService.findJeuByReference(reference);
        return avisService.getAvisByJeux(jeux, page, size);
    }
}
