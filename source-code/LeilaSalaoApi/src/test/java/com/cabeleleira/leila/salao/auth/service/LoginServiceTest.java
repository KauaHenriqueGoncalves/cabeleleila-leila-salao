package com.cabeleleira.leila.salao.auth.service;

import com.cabeleleira.leila.salao.auth.dto.LoginRequestDTO;
import com.cabeleleira.leila.salao.auth.dto.LoginResponseDTO;
import com.cabeleleira.leila.salao.domain.User;
import com.cabeleleira.leila.salao.enums.UsersRole;
import com.cabeleleira.leila.salao.service.interfaces.IUserService;
import com.cabeleleira.leila.salao.shared.exceptions.AccessDeniedException;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginService - Testes Unitários")
class LoginServiceTest {
    @Mock
    private IUserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    private UUID userId;
    private User userFixture;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        userFixture = new User(
                userId,
                "leila@salao.com",
                "$2a$10$hashedSenha",
                UsersRole.ADMIN,
                true,
                Instant.now()
        );
    }

    @Test
    @DisplayName("login - deve retornar LoginResponseDTO com id e role quando credenciais válidas")
    void login_deveRetornarLoginResponse_quandoCredenciaisValidas() {
        LoginRequestDTO dto = new LoginRequestDTO("leila@salao.com", "senha123");

        when(userService.findByEmail("leila@salao.com")).thenReturn(userFixture);
        when(passwordEncoder.matches("senha123", "$2a$10$hashedSenha")).thenReturn(true);

        LoginResponseDTO result = loginService.login(dto);

        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals("admin", result.role());
        verify(userService, times(1)).findByEmail("leila@salao.com");
        verify(passwordEncoder, times(1)).matches("senha123", "$2a$10$hashedSenha");
    }

    @Test
    @DisplayName("login - role retornada deve ser 'admin' para usuário ADMIN")
    void login_deveRetornarRoleAdmin_quandoUsuarioAdmin() {
        LoginRequestDTO dto = new LoginRequestDTO("leila@salao.com", "senha123");
        when(userService.findByEmail(anyString())).thenReturn(userFixture);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        LoginResponseDTO result = loginService.login(dto);

        assertEquals("admin", result.role());
    }

    @Test
    @DisplayName("login - role retornada deve ser 'client' para usuário CLIENT")
    void login_deveRetornarRoleClient_quandoUsuarioClient() {
        User clientUser = new User(UUID.randomUUID(), "cliente@test.com", "hash", UsersRole.CLIENT, true, Instant.now());
        LoginRequestDTO dto = new LoginRequestDTO("cliente@test.com", "senha");

        when(userService.findByEmail("cliente@test.com")).thenReturn(clientUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        LoginResponseDTO result = loginService.login(dto);

        assertEquals("client", result.role());
    }

    @Test
    @DisplayName("login - deve lançar AccessDeniedException quando email não encontrado")
    void login_deveLancarAccessDeniedException_quandoEmailNaoEncontrado() {
        LoginRequestDTO dto = new LoginRequestDTO("inexistente@email.com", "qualquerSenha");

        when(userService.findByEmail("inexistente@email.com"))
                .thenThrow(new NotFoundObjectException("Não foi encontrado usuário"));

        AccessDeniedException ex = assertThrows(
                AccessDeniedException.class,
                () -> loginService.login(dto)
        );

        assertEquals("Credenciais Invalidas", ex.getMessage());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("login - deve lançar AccessDeniedException quando senha incorreta")
    void login_deveLancarAccessDeniedException_quandoSenhaIncorreta() {
        LoginRequestDTO dto = new LoginRequestDTO("leila@salao.com", "senhaErrada");

        when(userService.findByEmail("leila@salao.com")).thenReturn(userFixture);
        when(passwordEncoder.matches("senhaErrada", "$2a$10$hashedSenha")).thenReturn(false);

        AccessDeniedException ex = assertThrows(
                AccessDeniedException.class,
                () -> loginService.login(dto)
        );

        assertEquals("Credenciais Invalidas", ex.getMessage());
    }

    @Test
    @DisplayName("login - não deve revelar se email existe ou não (mesma mensagem de erro)")
    void login_mensagemDeErroDeveSericaParaEmailEParaSenha() {

        // Erro de email
        when(userService.findByEmail("emailerrado@test.com"))
                .thenThrow(new NotFoundObjectException("Não foi encontrado usuário"));
        AccessDeniedException exEmail = assertThrows(
                AccessDeniedException.class,
                () -> loginService.login(new LoginRequestDTO("emailerrado@test.com", "qualquer"))
        );

        // Erro de senha
        when(userService.findByEmail("leila@salao.com")).thenReturn(userFixture);
        when(passwordEncoder.matches("senhaErrada", "$2a$10$hashedSenha")).thenReturn(false);
        AccessDeniedException exSenha = assertThrows(
                AccessDeniedException.class,
                () -> loginService.login(new LoginRequestDTO("leila@salao.com", "senhaErrada"))
        );

        assertEquals(exEmail.getMessage(), exSenha.getMessage(),
                "Mensagem de erro deveria ser idêntica para email ou senha errados");
    }
}
