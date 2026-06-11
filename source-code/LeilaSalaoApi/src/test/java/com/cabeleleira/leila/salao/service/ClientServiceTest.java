package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.Client;
import com.cabeleleira.leila.salao.domain.User;
import com.cabeleleira.leila.salao.dto.ClientToListResponseDTO;
import com.cabeleleira.leila.salao.dto.CreateClientRequestDTO;
import com.cabeleleira.leila.salao.dto.CreateUserRequestDTO;
import com.cabeleleira.leila.salao.enums.UsersRole;
import com.cabeleleira.leila.salao.repository.ClientRepository;
import com.cabeleleira.leila.salao.service.interfaces.IUserService;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientService - Testes Unitários")
class ClientServiceTest {
    @Mock
    private IUserService userService;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private UUID clientId;
    private User userFixture;
    private Client clientFixture;

    @BeforeEach
    void setUp() {
        clientId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        userFixture = new User(userId, "cliente@test.com", "hash", UsersRole.CLIENT, true, Instant.now());
        clientFixture = new Client(clientId, userFixture, "Maria Silva", "81999990000", Instant.now());
    }


    @Test
    @DisplayName("findAll - deve retornar lista com todos os clientes")
    void findAll_deveRetornarListaDeClientes() {
        Client client2 = new Client(UUID.randomUUID(), userFixture, "Ana Paula", "81988880000", Instant.now());
        when(clientRepository.findAll()).thenReturn(List.of(clientFixture, client2));

        List<ClientToListResponseDTO> result = clientService.findAll();

        assertEquals(2, result.size());
        assertEquals("Maria Silva", result.get(0).name());
        assertEquals("Ana Paula", result.get(1).name());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - deve retornar lista vazia quando não há clientes")
    void findAll_deveRetornarListaVazia_quandoNaoHaClientes() {
        when(clientRepository.findAll()).thenReturn(List.of());
        List<ClientToListResponseDTO> result = clientService.findAll();
        assertTrue(result.isEmpty());
    }

    // ─────────────────────────────────────────────
    //  findById
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("findById - deve retornar cliente quando ID existe")
    void findById_deveRetornarCliente_quandoIdExiste() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientFixture));

        Client result = clientService.findById(clientId);

        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("Maria Silva", result.getName());
        assertEquals("81999990000", result.getPhoneNumber());
    }

    @Test
    @DisplayName("findById - deve lançar NotFoundObjectException quando ID não existe")
    void findById_deveLancarNotFoundObjectException_quandoIdNaoExiste() {
        UUID idInexistente = UUID.randomUUID();
        when(clientRepository.findById(idInexistente)).thenReturn(Optional.empty());

        NotFoundObjectException ex = assertThrows(
                NotFoundObjectException.class,
                () -> clientService.findById(idInexistente)
        );

        assertEquals("Não encontrou o cliente", ex.getMessage());
    }

    @Test
    @DisplayName("create - deve criar cliente e retornar UUID gerado")
    void create_deveCriarClienteERetornarId() {
        CreateUserRequestDTO userDto = new CreateUserRequestDTO("joao@test.com", "senha123");
        CreateClientRequestDTO dto = new CreateClientRequestDTO(userDto, "João Souza", "81977770000");

        UUID novoClienteId = UUID.randomUUID();
        Client clienteSalvo = new Client(novoClienteId, userFixture, "João Souza", "81977770000", Instant.now());

        when(userService.createRoleClient(userDto)).thenReturn(userFixture);
        when(clientRepository.save(any(Client.class))).thenReturn(clienteSalvo);

        UUID resultId = clientService.create(dto);

        assertNotNull(resultId);
        assertEquals(novoClienteId, resultId);
        verify(userService, times(1)).createRoleClient(userDto);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("create - deve propagar exceção do userService quando email duplicado")
    void create_devePropagarExcecao_quandoUserServiceLancaExcecao() {
        CreateUserRequestDTO userDto = new CreateUserRequestDTO("duplicado@test.com", "senha");
        CreateClientRequestDTO dto = new CreateClientRequestDTO(userDto, "Duplicado", "11900000000");

        when(userService.createRoleClient(userDto))
                .thenThrow(new RuntimeException("Email duplicado"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> clientService.create(dto)
        );

        assertEquals("Email duplicado", ex.getMessage());
        verify(clientRepository, never()).save(any());
    }

    @Test
    @DisplayName("deleteById - deve deletar cliente quando ID existe")
    void deleteById_deveDeletarCliente_quandoIdExiste() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientFixture));
        doNothing().when(clientRepository).deleteById(clientId);

        assertDoesNotThrow(() -> clientService.deleteById(clientId));

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    @DisplayName("deleteById - deve lançar NotFoundObjectException quando ID não existe")
    void deleteById_deveLancarNotFoundObjectException_quandoIdNaoExiste() {
        UUID idInexistente = UUID.randomUUID();
        when(clientRepository.findById(idInexistente)).thenReturn(Optional.empty());

        NotFoundObjectException ex = assertThrows(
                NotFoundObjectException.class,
                () -> clientService.deleteById(idInexistente)
        );

        verify(clientRepository, never()).deleteById(any());
    }
}
