package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.exceptions.UserWithEmailNotFoundException;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.security.MyUserDetailsService;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.repositories.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private final MyUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(MyUserDetailsService userDetailsService, UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return passwordEncoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (userRepository.findByEmail(register.getUsername()).isPresent()) {
            throw new UserWithEmailNotFoundException(String.format("Пользователь с ником \"%s\" cуществует",
                    register.getUsername())
            );
        }

        var userEntity = userMapper.toUser(register);
        userRepository.saveAndFlush(userEntity);
        return true;
    }
}