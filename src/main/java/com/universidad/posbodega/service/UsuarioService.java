package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.UsuarioRequest;
import com.universidad.posbodega.dto.UsuarioResponse;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponse> listar();
    UsuarioResponse buscarPorId(Long id);
    UsuarioResponse crear(UsuarioRequest request);
    UsuarioResponse actualizar(Long id, UsuarioRequest request);
    void desactivar(Long id);
    void activar(Long id);
}