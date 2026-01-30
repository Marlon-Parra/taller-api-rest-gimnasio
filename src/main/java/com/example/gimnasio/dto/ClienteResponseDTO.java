package com.example.gimnasio.dto;

import java.util.List;

public class ClienteResponseDTO {

    private Long id;
    private String nombre;
    private String documento;
    private boolean activo;
    private List<RutinaResponseDTO> rutinas;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Long id, String nombre, String documento, boolean activo, List<RutinaResponseDTO> rutinas) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.activo = activo;
        this.rutinas = rutinas;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public boolean isActivo() {
        return activo;
    }

    public List<RutinaResponseDTO> getRutinas() {
        return rutinas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setRutinas(List<RutinaResponseDTO> rutinas) {
        this.rutinas = rutinas;
    }
}
