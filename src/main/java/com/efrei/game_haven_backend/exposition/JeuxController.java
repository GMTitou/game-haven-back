package com.efrei.game_haven_backend.exposition;

import com.efrei.game_haven_backend.domain.jeux.Jeux;
import com.efrei.game_haven_backend.domain.jeux.JeuxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jeux")
public class JeuxController {

    @Autowired
    private JeuxService jeuxService;

    @GetMapping("/reference/{reference}")
    public Jeux getJeuxByReference(@PathVariable("reference") int reference) {
        return jeuxService.findJeuByReference(reference);
    }

    @PostMapping("/create")
    public Jeux createJeux(@RequestBody Jeux jeux) {
        return jeuxService.create(jeux);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Jeux>> findJeuxByCriteria(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Jeux> jeuxPage = jeuxService.findJeuxByCriteria(searchTerm, pageable);
        return ResponseEntity.ok(jeuxPage);
    }

    @PutMapping("/reference/{reference}")
    public Jeux updateJeuxByReference(@PathVariable int reference, @RequestBody Jeux updatedJeux) {
        return jeuxService.updateJeuxByReference(reference, updatedJeux);
    }
}
