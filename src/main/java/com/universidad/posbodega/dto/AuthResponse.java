package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String token;
    private String email;
    private String nombre;
    private Set<String> roles;
    private Set<String> permisos;
}