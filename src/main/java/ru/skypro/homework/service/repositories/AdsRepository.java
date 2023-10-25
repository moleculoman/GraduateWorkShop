package ru.skypro.homework.service.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.service.entities.AdsEntity;
import ru.skypro.homework.service.entities.UserEntity;

import java.util.List;

public interface AdsRepository extends JpaRepository<AdsEntity, Integer> {
    //List<AdsEntity> findByUser(UserEntity user);
    AdsEntity findByEmail(String email);
}
