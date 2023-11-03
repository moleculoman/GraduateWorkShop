package ru.skypro.homework.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entities.AdsEntity;
import ru.skypro.homework.entities.UserEntity;

import java.util.List;

public interface AdsRepository extends JpaRepository<AdsEntity, Integer> {
    List<AdsEntity> findByUser(UserEntity user);
    AdsEntity findByEmail(String email);
}
