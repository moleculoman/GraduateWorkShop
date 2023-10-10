package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.CommentsEntity;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<AdsEntity, Long>{


    AdsEntity findByNameLike(String title);
    AdsEntity findByIdBetween(int from, int to);
    AdsEntity getById();
    List<CommentsEntity> findByTitle(String comments);
    }


