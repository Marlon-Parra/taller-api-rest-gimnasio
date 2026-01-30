package com.example.gimnasio.service;

import com.example.gimnasio.dto.ClienteCreateDTO;
import com.example.gimnasio.dto.ClienteResponseDTO;
import com.example.gimnasio.dto.RutinaResponseDTO;
import com.example.gimnasio.entity.Cliente;
import com.example.gimnasio.entity.Rutina;
import com.example.gimnasio.repository.ClienteRepository;
import com.example.gimnasio.repository.RutinaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final RutinaRepository rutinaRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          RutinaRepository rutinaRepository) {
        this.clienteRepository = clienteRepository;
        this.rutinaRepository = rutinaRepository;
    }

    // CREATE
    @Transactional
    public ClienteResponseDTO crearCliente(ClienteCreateDTO dto) {

        if (clienteRepository.existsByDocumento(dto.getDocumento())) {
            throw new RuntimeException("Documento ya registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setDocumento(dto.getDocumento());
        cliente.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        Cliente guardado = clienteRepository.save(cliente);
        return mapToResponse(guardado);
    }

    // LIST ALL
    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET by id
    public ClienteResponseDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return mapToResponse(cliente);
    }

    // UPDATE
    @Transactional
    public ClienteResponseDTO actualizarCliente(Long id, ClienteCreateDTO dto) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!cliente.getDocumento().equals(dto.getDocumento())
                && clienteRepository.existsByDocumento(dto.getDocumento())) {
            throw new RuntimeException("Documento ya registrado por otro cliente");
        }

        cliente.setNombre(dto.getNombre());
        cliente.setDocumento(dto.getDocumento());
        cliente.setActivo(dto.getActivo() != null ? dto.getActivo() : cliente.isActivo());

        Cliente actualizado = clienteRepository.save(cliente);
        return mapToResponse(actualizado);
    }

    // DELETE lógico
    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    // ASSIGN rutina (🔥 CORREGIDO)
    @Transactional
    public void asignarRutina(Long clienteId, Long rutinaId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Rutina rutina = rutinaRepository.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        // sincronizar ambos lados de la relación
        cliente.getRutinas().add(rutina);
        rutina.getClientes().add(cliente);

        clienteRepository.save(cliente);
    }

    // REMOVE rutina (🔥 RECOMENDADO)
    @Transactional
    public void quitarRutina(Long clienteId, Long rutinaId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Rutina rutina = rutinaRepository.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        cliente.getRutinas().remove(rutina);
        rutina.getClientes().remove(cliente);

        clienteRepository.save(cliente);
    }

    // LIST rutinas of client
    public List<RutinaResponseDTO> listarRutinas(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return cliente.getRutinas()
                .stream()
                .map(r -> new RutinaResponseDTO(
                        r.getId(),
                        r.getNombre(),
                        r.getNivel().name()))
                .collect(Collectors.toList());
    }

    // MAPPER
    private ClienteResponseDTO mapToResponse(Cliente c) {

        List<RutinaResponseDTO> rutinas = c.getRutinas()
                .stream()
                .map(r -> new RutinaResponseDTO(
                        r.getId(),
                        r.getNombre(),
                        r.getNivel().name()))
                .collect(Collectors.toList());

        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setDocumento(c.getDocumento());
        dto.setActivo(c.isActivo());
        dto.setRutinas(rutinas);

        return dto;
    }
}
