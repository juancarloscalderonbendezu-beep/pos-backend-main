package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class UsuarioResponse {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private Boolean activo;
    private LocalDateTime ultimoAcceso;
    private Set<RolResponse> roles;
}