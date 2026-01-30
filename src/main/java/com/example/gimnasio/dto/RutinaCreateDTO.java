package com.example.gimnasio.dto;

import jakarta.validation.constraints.NotBlank;

public class RutinaCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El nivel es obligatorio (BASICO, INTERMEDIO, AVANZADO)")
    private String nivel;

    public RutinaCreateDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
