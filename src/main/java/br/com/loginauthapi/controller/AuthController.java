package br.com.loginauthapi.controller;

import br.com.loginauthapi.dto.AuthResponse;
import br.com.loginauthapi.dto.LoginRequestDTO;
import br.com.loginauthapi.dto.RegisterRequestDTO;
import br.com.loginauthapi.dto.ResponseDTO;
import br.com.loginauthapi.exception.DuplicateDataException;
import br.com.loginauthapi.exception.ResultNotFoundException;
import br.com.loginauthapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "User Authentication", description = "Registro e autenticação de usuário")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Autenticar login", description = "Retorna um response com token e o nome do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
        Optional<ResponseDTO> authenticate = authService.authenticate(requestDTO);
        return authenticate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Registrar usuário", description = "Retorna um response com token e o nome do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")

    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
        Optional<ResponseDTO> authenticate = authService.register(requestDTO);
        return authenticate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponse> handleDadosInvalidosException(Exception ex) {
        Date date = new Date();
        AuthResponse response = new AuthResponse(ex.getMessage(), date);

        if (ex.getClass().equals(ResultNotFoundException.class) || ex.getClass().equals(DuplicateDataException.class)) {
            response = new AuthResponse(ex.getMessage(), date);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
