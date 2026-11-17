package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.PermisoRequest;
import com.universidad.posbodega.dto.PermisoResponse;

import java.util.List;

public interface PermisoService {
    List<PermisoResponse> listar();
    PermisoResponse buscarPorId(Long id);
    PermisoResponse crear(PermisoRequest request);
    PermisoResponse actualizar(Long id, PermisoRequest request);
    void desactivar(Long id);
    void activar(Long id);
}