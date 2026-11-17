package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.RolRequest;
import com.universidad.posbodega.dto.RolResponse;

import java.util.List;

public interface RolService {
    List<RolResponse> listar();
    RolResponse buscarPorId(Long id);
    RolResponse crear(RolRequest request);
    RolResponse actualizar(Long id, RolRequest request);
    void desactivar(Long id);
    void activar(Long id);
}