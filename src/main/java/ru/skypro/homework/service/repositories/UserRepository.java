package ru.skypro.homework.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.service.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);
}