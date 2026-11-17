package com.universidad.posbodega.controller;

import com.universidad.posbodega.dto.RolRequest;
import com.universidad.posbodega.dto.RolResponse;
import com.universidad.posbodega.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolResponse>> listar() {
        return ResponseEntity.ok(rolService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RolResponse> crear(@RequestBody RolRequest request) {
        return ResponseEntity.ok(rolService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponse> actualizar(@PathVariable Long id, @RequestBody RolRequest request) {
        return ResponseEntity.ok(rolService.actualizar(id, request));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        rolService.desactivar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        rolService.activar(id);
        return ResponseEntity.ok().build();
    }
}