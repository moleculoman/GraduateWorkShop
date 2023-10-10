package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.CommentsEntity;
import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    CommentsEntity findByNameLike(String text);

    CommentsEntity save();
    CommentsEntity getById();
    List<CommentsEntity> findByTextLike(String comments);
    }


