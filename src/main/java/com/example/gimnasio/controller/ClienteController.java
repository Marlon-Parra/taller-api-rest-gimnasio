package com.example.gimnasio.controller;

import com.example.gimnasio.dto.ClienteCreateDTO;
import com.example.gimnasio.dto.ClienteResponseDTO;
import com.example.gimnasio.dto.RutinaResponseDTO;
import com.example.gimnasio.service.ClienteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@Valid @RequestBody ClienteCreateDTO dto) {
        return new ResponseEntity<>(clienteService.crearCliente(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerCliente(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteCreateDTO dto) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clienteId}/rutinas/{rutinaId}")
    public ResponseEntity<Void> asignarRutina(
            @PathVariable Long clienteId,
            @PathVariable Long rutinaId) {
        clienteService.asignarRutina(clienteId, rutinaId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{clienteId}/rutinas")
    public ResponseEntity<List<RutinaResponseDTO>> listarRutinasCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(clienteService.listarRutinas(clienteId));
    }

    @DeleteMapping("/{clienteId}/rutinas/{rutinaId}")
    public ResponseEntity<Void> quitarRutina(
            @PathVariable Long clienteId,
            @PathVariable Long rutinaId) {
        clienteService.quitarRutina(clienteId, rutinaId);
        return ResponseEntity.noContent().build();
    }
}
