package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.*;

import java.io.IOException;

public interface AdsService {

    AdsDTO getAllAds();

    AdsDTO getAdsMe(String email);

    AdDTO addAd(CreateAdsDTO createAds, String email, MultipartFile image);

    //Обновляет информацию об объявлении по его идентификатору.
    AdDTO updateAds(CreateAdsDTO createAds, Integer id);

    CommentsDTO getComments(Integer id);

    CommentDTO addComment(Integer id, CreateCommentDTO createComment, String email);

    FullAdDTO getAds(Integer id);

    void removeAd(Integer id);

    void deleteComment(Integer adId, Integer id);

    CommentDTO updateComment(Integer adId, Integer id, CreateCommentDTO createComment);

    void updateAdsImage(Integer id, MultipartFile image);

    byte[] getImage(String name) throws IOException;

    CommentDTO getCommentDto(Integer adId,Integer id);
}
