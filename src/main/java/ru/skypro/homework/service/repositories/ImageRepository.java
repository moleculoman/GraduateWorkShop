package ru.skypro.homework.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.service.entities.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}