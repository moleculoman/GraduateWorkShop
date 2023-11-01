package ru.skypro.homework.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ru.skypro.homework.service.entities.UserEntity;
import ru.skypro.homework.service.repositories.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        Optional<UserEntity> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(userEmail);
        }
        return new MyUserDetails(user.get());
    }
}