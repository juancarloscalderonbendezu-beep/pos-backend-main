package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.PermisoResponse;
import com.universidad.posbodega.dto.RolRequest;
import com.universidad.posbodega.dto.RolResponse;
import com.universidad.posbodega.entity.Permiso;
import com.universidad.posbodega.entity.Rol;
import com.universidad.posbodega.repository.PermisoRepository;
import com.universidad.posbodega.repository.RolRepository;
import com.universidad.posbodega.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    @Override
    public List<RolResponse> listar() {
        return rolRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public RolResponse buscarPorId(Long id) {
        Rol rol = obtenerRol(id);
        return mapToResponse(rol);
    }

    @Override
    public RolResponse crear(RolRequest request) {
        if (rolRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un rol con ese nombre");
        }

        Rol rol = Rol.builder()
            .nombre(request.getNombre())
            .descripcion(request.getDescripcion())
            .activo(true)
            .permisos(new HashSet<>())
            .build();

        if (request.getPermisoIds() != null) {
            request.getPermisoIds().forEach(permisoId -> {
                Permiso permiso = permisoRepository.findById(permisoId)
                    .orElseThrow(() -> new RuntimeException("Permiso no encontrado: " + permisoId));
                rol.getPermisos().add(permiso);
            });
        }

        return mapToResponse(rolRepository.save(rol));
    }

    @Override
    public RolResponse actualizar(Long id, RolRequest request) {
        Rol rol = obtenerRol(id);

        if (!rol.getNombre().equals(request.getNombre()) && 
            rolRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un rol con ese nombre");
        }

        rol.setNombre(request.getNombre());
        rol.setDescripcion(request.getDescripcion());

        if (request.getPermisoIds() != null) {
            rol.getPermisos().clear();
            request.getPermisoIds().forEach(permisoId -> {
                Permiso permiso = permisoRepository.findById(permisoId)
                    .orElseThrow(() -> new RuntimeException("Permiso no encontrado: " + permisoId));
                rol.getPermisos().add(permiso);
            });
        }

        return mapToResponse(rolRepository.save(rol));
    }

    @Override
    public void desactivar(Long id) {
        Rol rol = obtenerRol(id);
        rol.setActivo(false);
        rolRepository.save(rol);
    }

    @Override
    public void activar(Long id) {
        Rol rol = obtenerRol(id);
        rol.setActivo(true);
        rolRepository.save(rol);
    }

    private Rol obtenerRol(Long id) {
        return rolRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    private RolResponse mapToResponse(Rol rol) {
        return RolResponse.builder()
            .id(rol.getId())
            .nombre(rol.getNombre())
            .descripcion(rol.getDescripcion())
            .activo(rol.getActivo())
            .permisos(rol.getPermisos().stream()
                .map(permiso -> PermisoResponse.builder()
                    .id(permiso.getId())
                    .nombre(permiso.getNombre())
                    .descripcion(permiso.getDescripcion())
                    .modulo(permiso.getModulo())
                    .activo(permiso.getActivo())
                    .build())
                .collect(Collectors.toSet()))
            .build();
    }
}