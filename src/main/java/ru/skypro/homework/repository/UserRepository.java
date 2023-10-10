package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
        User findByNameLike(String username);
        User save();
        User getById();
}
