package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UsuarioRequest {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private Set<Long> rolIds;
}