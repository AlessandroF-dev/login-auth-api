package br.com.loginauthapi.service;

import br.com.loginauthapi.domain.user.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
}
