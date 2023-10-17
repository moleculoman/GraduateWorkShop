package ru.skypro.homework.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.service.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}
