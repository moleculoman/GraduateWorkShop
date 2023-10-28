package ru.skypro.homework.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.service.entities.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByAdsId(Integer id);

    void deleteByAdsIdAndId(Integer adId, Integer id);

    Optional<CommentEntity> findCommentByIdAndAds_Id(Integer id, Integer adsId);

    void deleteAllByAds_Id(Integer id);
}
