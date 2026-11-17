package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.PermisoRequest;
import com.universidad.posbodega.dto.PermisoResponse;
import com.universidad.posbodega.entity.Permiso;
import com.universidad.posbodega.repository.PermisoRepository;
import com.universidad.posbodega.service.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;

    @Override
    public List<PermisoResponse> listar() {
        return permisoRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public PermisoResponse buscarPorId(Long id) {
        Permiso permiso = obtenerPermiso(id);
        return mapToResponse(permiso);
    }

    @Override
    public PermisoResponse crear(PermisoRequest request) {
        if (permisoRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un permiso con ese nombre");
        }

        Permiso permiso = Permiso.builder()
            .nombre(request.getNombre())
            .descripcion(request.getDescripcion())
            .modulo(request.getModulo())
            .activo(true)
            .build();

        return mapToResponse(permisoRepository.save(permiso));
    }

    @Override
    public PermisoResponse actualizar(Long id, PermisoRequest request) {
        Permiso permiso = obtenerPermiso(id);

        if (!permiso.getNombre().equals(request.getNombre()) && 
            permisoRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un permiso con ese nombre");
        }

        permiso.setNombre(request.getNombre());
        permiso.setDescripcion(request.getDescripcion());
        permiso.setModulo(request.getModulo());

        return mapToResponse(permisoRepository.save(permiso));
    }

    @Override
    public void desactivar(Long id) {
        Permiso permiso = obtenerPermiso(id);
        permiso.setActivo(false);
        permisoRepository.save(permiso);
    }

    @Override
    public void activar(Long id) {
        Permiso permiso = obtenerPermiso(id);
        permiso.setActivo(true);
        permisoRepository.save(permiso);
    }

    private Permiso obtenerPermiso(Long id) {
        return permisoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
    }

    private PermisoResponse mapToResponse(Permiso permiso) {
        return PermisoResponse.builder()
            .id(permiso.getId())
            .nombre(permiso.getNombre())
            .descripcion(permiso.getDescripcion())
            .modulo(permiso.getModulo())
            .activo(permiso.getActivo())
            .build();
    }
}