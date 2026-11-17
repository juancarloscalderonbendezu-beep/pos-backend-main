package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.AuthRequest;
import com.universidad.posbodega.dto.AuthResponse;
import com.universidad.posbodega.entity.Usuario;
import com.universidad.posbodega.repository.UsuarioRepository;
import com.universidad.posbodega.security.JwtService;
import com.universidad.posbodega.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            usuario.setUltimoAcceso(LocalDateTime.now());
            usuarioRepository.save(usuario);

            return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .roles(usuario.getRoles().stream().map(rol -> rol.getNombre()).collect(Collectors.toSet()))
                .permisos(usuario.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.toSet()))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error en autenticación: " + e.getMessage());
        }
    }
}