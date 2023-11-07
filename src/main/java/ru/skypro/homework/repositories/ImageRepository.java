package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entities.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}
