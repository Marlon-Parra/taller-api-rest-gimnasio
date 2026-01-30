package com.example.gimnasio.dto;

public class RutinaResponseDTO {

    private Long id;
    private String nombre;
    private String nivel;

    public RutinaResponseDTO() {
    }

    public RutinaResponseDTO(Long id, String nombre, String nivel) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
