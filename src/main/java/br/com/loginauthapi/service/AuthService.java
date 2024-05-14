package br.com.loginauthapi.service;

import br.com.loginauthapi.domain.user.User;
import br.com.loginauthapi.dto.LoginRequestDTO;
import br.com.loginauthapi.dto.RegisterRequestDTO;
import br.com.loginauthapi.dto.ResponseDTO;
import br.com.loginauthapi.exception.DuplicateDataException;
import br.com.loginauthapi.exception.InvalidDataException;
import br.com.loginauthapi.exception.ResultNotFoundException;
import br.com.loginauthapi.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public Optional<ResponseDTO> authenticate(LoginRequestDTO requestDTO) {
        Optional<User> opUser = userService.findByEmail(requestDTO.email());
        if (opUser.isPresent()) {
            if (passwordEncoder.matches(requestDTO.password(), opUser.get().getPassword())) {
                String token = tokenService.generateToken(opUser.get());
                return Optional.of(new ResponseDTO(opUser.get().getName(), token));
            }
        }

        throw new ResultNotFoundException("User not found");
    }

    public Optional<ResponseDTO> register(RegisterRequestDTO requestDTO) {
        this.validateRequest(requestDTO);

        Optional<User> opUser = userService.findByEmail(requestDTO.email());
        if (opUser.isEmpty()) {
            User newUser = User.builder()
                    .password(passwordEncoder.encode(requestDTO.password()))
                    .email(requestDTO.email())
                    .name(requestDTO.name())
                    .build();

            userService.register(newUser);

            String token = tokenService.generateToken(newUser);
            return Optional.of(new ResponseDTO(newUser.getName(), token));
        }
        throw new DuplicateDataException("Email already in use");
    }

    public void validateRequest(RegisterRequestDTO requestDTO) {
        if (null == requestDTO.name() || requestDTO.name().isEmpty()) {
            throw new InvalidDataException("The name cannot be empty");
        }
        if (null == requestDTO.email() || requestDTO.email().isEmpty()) {
            throw new InvalidDataException("The email address cannot be empty");
        }
        if (null == requestDTO.password() || requestDTO.password().isEmpty()) {
            throw new InvalidDataException("The password cannot be empty");
        }
    }
}