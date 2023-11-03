package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entities.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByAdsId(Integer id);

    void deleteByAdsIdAndId(Integer adId, Integer id);

    Optional<CommentEntity> findCommentByIdAndAds_Id(Integer id, Integer adsId);

    void deleteAllByAds_Id(Integer id);
}
