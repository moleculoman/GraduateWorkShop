package ru.skypro.homework.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.repositories.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        Optional<UserEntity> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(userEmail);
        }
        return new MyUserDetails(user.get());
    }
}