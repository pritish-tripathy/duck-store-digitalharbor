package com.duckstore.controller;

import com.duckstore.dto.AddDuckRequest;
import com.duckstore.dto.DuckResponse;
import com.duckstore.dto.UpdateDuckRequest;
import com.duckstore.service.DuckService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ducks")
public class DuckController {

    private final DuckService duckService;

    public DuckController(DuckService duckService) {
        this.duckService = duckService;
    }

    @PostMapping
    public ResponseEntity<Void> addDuck(
            @Valid @RequestBody AddDuckRequest request) {

        duckService.addDuck(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DuckResponse>> getAllDucks() {

        List<DuckResponse> ducks = duckService.getAllDucks();

        return ResponseEntity.ok(ducks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDuck(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateDuckRequest request) {

        duckService.updateDuck(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDuck(
            @PathVariable Integer id) {

        duckService.deleteDuck(id);

        return ResponseEntity.noContent().build();
    }
}