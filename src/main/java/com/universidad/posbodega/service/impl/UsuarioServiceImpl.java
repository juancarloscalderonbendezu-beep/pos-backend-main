package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.RolResponse;
import com.universidad.posbodega.dto.UsuarioRequest;
import com.universidad.posbodega.dto.UsuarioResponse;
import com.universidad.posbodega.entity.Rol;
import com.universidad.posbodega.entity.Usuario;
import com.universidad.posbodega.repository.RolRepository;
import com.universidad.posbodega.repository.UsuarioRepository;
import com.universidad.posbodega.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = obtenerUsuario(id);
        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponse crear(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        Usuario usuario = Usuario.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            .activo(true)
            .roles(new HashSet<>())
            .build();

        if (request.getRolIds() != null) {
            request.getRolIds().forEach(rolId -> {
                Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolId));
                usuario.getRoles().add(rol);
            });
        }

        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        Usuario usuario = obtenerUsuario(id);

        if (!usuario.getEmail().equals(request.getEmail()) && 
            usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        usuario.setEmail(request.getEmail());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRolIds() != null) {
            usuario.getRoles().clear();
            request.getRolIds().forEach(rolId -> {
                Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolId));
                usuario.getRoles().add(rol);
            });
        }

        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void desactivar(Long id) {
        Usuario usuario = obtenerUsuario(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public void activar(Long id) {
        Usuario usuario = obtenerUsuario(id);
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    private Usuario obtenerUsuario(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        return UsuarioResponse.builder()
            .id(usuario.getId())
            .email(usuario.getEmail())
            .nombre(usuario.getNombre())
            .apellido(usuario.getApellido())
            .activo(usuario.getActivo())
            .ultimoAcceso(usuario.getUltimoAcceso())
            .roles(usuario.getRoles().stream()
                .map(rol -> RolResponse.builder()
                    .id(rol.getId())
                    .nombre(rol.getNombre())
                    .descripcion(rol.getDescripcion())
                    .activo(rol.getActivo())
                    .build())
                .collect(Collectors.toSet()))
            .build();
    }
}