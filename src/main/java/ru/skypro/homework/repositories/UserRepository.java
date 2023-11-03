package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByNameLike(String username);
    UserEntity save();
    UserEntity getById(Integer id);
}