package br.com.loginauthapi.service;

import br.com.loginauthapi.domain.user.User;
import br.com.loginauthapi.exception.ResultNotFoundException;
import br.com.loginauthapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> register (User request){
        return Optional.of(repository.save(request));
    }
}
