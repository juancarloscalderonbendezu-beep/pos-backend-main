package com.universidad.posbodega.controller;

import com.universidad.posbodega.dto.PermisoRequest;
import com.universidad.posbodega.dto.PermisoResponse;
import com.universidad.posbodega.service.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {

    private final PermisoService permisoService;

    @GetMapping
    public ResponseEntity<List<PermisoResponse>> listar() {
        return ResponseEntity.ok(permisoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(permisoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PermisoResponse> crear(@RequestBody PermisoRequest request) {
        return ResponseEntity.ok(permisoService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermisoResponse> actualizar(@PathVariable Long id, @RequestBody PermisoRequest request) {
        return ResponseEntity.ok(permisoService.actualizar(id, request));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        permisoService.desactivar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        permisoService.activar(id);
        return ResponseEntity.ok().build();
    }
}