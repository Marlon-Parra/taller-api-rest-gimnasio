package com.example.gimnasio.service;

import com.example.gimnasio.dto.ClienteResponseDTO;
import com.example.gimnasio.dto.RutinaCreateDTO;
import com.example.gimnasio.dto.RutinaResponseDTO;
import com.example.gimnasio.entity.Cliente;
import com.example.gimnasio.entity.NivelRutina;
import com.example.gimnasio.entity.Rutina;
import com.example.gimnasio.repository.RutinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutinaService {

    private final RutinaRepository rutinaRepository;

    public RutinaService(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    // CREATE
    public RutinaResponseDTO crearRutina(RutinaCreateDTO dto) {
        if (rutinaRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("Nombre de rutina ya existente");
        }

        Rutina rutina = new Rutina();
        try {
            rutina.setNivel(NivelRutina.valueOf(dto.getNivel()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nivel inválido. Valores permitidos: BASICO, INTERMEDIO, AVANZADO");
        }
        rutina.setNombre(dto.getNombre());

        Rutina guardada = rutinaRepository.save(rutina);
        return mapToResponse(guardada);
    }

    // LIST ALL
    public List<RutinaResponseDTO> listarRutinas() {
        return rutinaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET by id
    public RutinaResponseDTO obtenerRutinaPorId(Long id) {
        Rutina r = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        return mapToResponse(r);
    }

    // UPDATE
    public RutinaResponseDTO actualizarRutina(Long id, RutinaCreateDTO dto) {
        Rutina rutina = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        if (!rutina.getNombre().equals(dto.getNombre())
                && rutinaRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("Nombre de rutina ya existente");
        }

        try {
            rutina.setNivel(NivelRutina.valueOf(dto.getNivel()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nivel inválido. Valores permitidos: BASICO, INTERMEDIO, AVANZADO");
        }

        rutina.setNombre(dto.getNombre());
        Rutina actualizado = rutinaRepository.save(rutina);
        return mapToResponse(actualizado);
    }

    // DELETE
    public void eliminarRutina(Long id) {
        if (!rutinaRepository.existsById(id)) {
            throw new RuntimeException("Rutina no encontrada");
        }
        rutinaRepository.deleteById(id);
    }

    // LIST clientes of rutina
    public List<ClienteResponseDTO> listarClientes(Long rutinaId) {
        Rutina rutina = rutinaRepository.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        return rutina.getClientes()
                .stream()
                .map(c -> {
                    ClienteResponseDTO dto = new ClienteResponseDTO();
                    dto.setId(c.getId());
                    dto.setNombre(c.getNombre());
                    dto.setDocumento(c.getDocumento());
                    dto.setActivo(c.isActivo());
                    dto.setRutinas(null); // evitar anidar
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private RutinaResponseDTO mapToResponse(Rutina r) {
        return new RutinaResponseDTO(r.getId(), r.getNombre(), r.getNivel().name());
    }
}
