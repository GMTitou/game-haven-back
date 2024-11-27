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


    @PostMapping("/create")
    public Jeux createJeux(@RequestBody Jeux jeux) {
        return jeuxService.create(jeux);
    }

//    @GetMapping("/pageable")
//    public ResponseEntity<Page<Jeux>> getJeuxPageable(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Jeux> jeuxPage = jeuxService.getJeux(pageable);
//        return ResponseEntity.ok(jeuxPage);
//    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Jeux>> getJeuxPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchTerm) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Jeux> jeuxPage = jeuxService.getJeux(searchTerm, pageable);
        return ResponseEntity.ok(jeuxPage);
    }
}
