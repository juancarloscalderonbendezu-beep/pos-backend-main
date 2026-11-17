package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class RolResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private Set<PermisoResponse> permisos;
}