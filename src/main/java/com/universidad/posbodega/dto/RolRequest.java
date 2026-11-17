package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class RolRequest {
    private String nombre;
    private String descripcion;
    private Set<Long> permisoIds;
}