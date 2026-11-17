package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PermisoRequest {
    private String nombre;
    private String descripcion;
    private String modulo;
}