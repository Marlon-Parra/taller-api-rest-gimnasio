package com.example.gimnasio.controller;

import com.example.gimnasio.dto.ClienteResponseDTO;
import com.example.gimnasio.dto.RutinaCreateDTO;
import com.example.gimnasio.dto.RutinaResponseDTO;
import com.example.gimnasio.service.RutinaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rutinas")
public class RutinaController {

    private final RutinaService rutinaService;

    public RutinaController(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @PostMapping
    public ResponseEntity<RutinaResponseDTO> crearRutina(@Valid @RequestBody RutinaCreateDTO dto) {
        return new ResponseEntity<>(rutinaService.crearRutina(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RutinaResponseDTO>> listarRutinas() {
        return ResponseEntity.ok(rutinaService.listarRutinas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutinaResponseDTO> obtenerRutina(@PathVariable Long id) {
        return ResponseEntity.ok(rutinaService.obtenerRutinaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutinaResponseDTO> actualizarRutina(
            @PathVariable Long id,
            @Valid @RequestBody RutinaCreateDTO dto) {
        return ResponseEntity.ok(rutinaService.actualizarRutina(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRutina(@PathVariable Long id) {
        rutinaService.eliminarRutina(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{rutinaId}/clientes")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes(@PathVariable Long rutinaId) {
        return ResponseEntity.ok(rutinaService.listarClientes(rutinaId));
    }
}
