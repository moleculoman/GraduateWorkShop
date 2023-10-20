package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.exceptions.AdsNotFoundException;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.exceptions.UserWithEmailNotFoundException;
import ru.skypro.homework.mappers.*;
import ru.skypro.homework.service.*;
import ru.skypro.homework.service.entities.*;
import ru.skypro.homework.service.repositories.*;


import javax.transaction.Transactional;
import java.io.*;
import java.time.*;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ImageService imageService;
    private final AdsMapper adsMapper;
    private final CommentMapper commentMapper;


    //Получить список всех объявлений.

    @Override
    public AdsDTO getAllAds() {
        List<AdsEntity> adsList = adsRepository.findAll();
        List<AdDTO> adsDtoList = adsMapper.toDtos(adsList);
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setCount(adsList.size());
        adsDTO.setResults(adsDtoList);
        return adsDTO;
    }

    // Получает список объявлений, принадлежащих пользователю с указанным адресом электронной почты.

    @Override
    public AdsDTO getAdsMe(String email) {
        List<AdsEntity> adsList = adsRepository.findByUser(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email)));
        List<AdDTO> adsDtoList = adsMapper.toDtos(adsList);
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setResults(adsDtoList);
        adsDTO.setCount(adsList.size());
        return adsDTO;
    }

    //Добавляет новое объявление в базу данных.
    @Override
    public AdDTO addAd(CreateAdsDTO createAds, String email, MultipartFile image) {
        AdsEntity ads = adsMapper.toAdsFromCreateAds(createAds);
        adsRepository.save(ads);
        return adsMapper.toAdsDto(ads);
    }

    //Получает полную информацию об объявлении по его идентификатору.
    @Override
    public FullAdDTO getAds(Integer id) {
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        return adsMapper.toFullAds((List<AdsEntity>) ads);
    }


    //Удаляет объявление по его идентификатору.

    @Transactional
    @Override
    public void removeAd(Integer id) {
        commentRepository.deleteAllByAds_Id(id);
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        imageService.deleteFileIfNotNull(String.valueOf(ads.getImage()));
        log.trace("Removed Ads with id: ", id);
        adsRepository.delete(ads);
    }


    //Обновляет информацию об объявлении по его идентификатору.
    @Override
    public AdDTO updateAds(CreateAdsDTO createAds, Integer id) {
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        adsMapper.updateAds(createAds, (List<AdsEntity>) ads);
        adsRepository.save(ads);
        log.trace("Updated Ads with id: ", id);
        return adsMapper.toAdsDto(ads);
    }


    //Получает список комментариев к объявлению по его идентификатору
    @Override
    public CommentsDTO getComments(Integer id) {
        List<CommentEntity> commentList = commentRepository.findAllByAdsId(id);
        List<CommentDTO> commentDtos = commentMapper.toListDto(commentList);
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setResults(commentDtos);
        commentsDTO.setCount(commentDtos.size());
        return commentsDTO;
    }

    //Добавляет новый комментарий к объявлению.
    @Override
    public CommentDTO addComment(Integer id, CreateCommentDTO createComment, String email) {
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found"));
        CommentEntity comment = commentMapper.toCommentFromCreateComment(createComment);
        comment.setAds(ads);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(userRepository.findByEmail(email).get());
        commentRepository.save(comment);
        log.trace("Added comment with id: ", comment.getId());
        return commentMapper.toCommentDtoFromComment(comment);
    }


    //Удаляет комментарий по идентификаторам объявления и комментария.
    @Override
    @Transactional
    public void deleteComment(Integer adId, Integer id) {
        commentRepository.deleteByAdsIdAndId(adId, id);
        log.trace("Deleted comment with id: ", id);
    }

    //Обновляет текст комментария по идентификаторам объявления и комментария.
    @Override
    public CommentDTO updateComment(Integer adId, Integer id, CreateCommentDTO createComment) {
        CommentEntity comment = commentRepository.findCommentByIdAndAds_Id(id, adId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setText(createComment.getText());
        commentRepository.save(comment);
        log.trace("Updated comment with id: ", id);
        return commentMapper.toCommentDtoFromComment(comment);
    }

    //Обновляет изображение объявления по его идентификатору.
    @Override
    public void updateAdsImage(Integer id, MultipartFile image) {
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found"));
        imageService.deleteFileIfNotNull(String.valueOf(ads.getImage()));
        ads.setImage(imageService.saveImage(image, "/ads"));
        adsRepository.save(ads);
    }

    //Получает изображение по его имени.
    @Override
    public byte[] getImage(String name) throws IOException {
        return imageService.getImage(name);
    }

    //Получает объект CommentDto по идентификаторам объявления и комментария.
    @Override
    public CommentDTO getCommentDto(Integer adId, Integer id) {
        CommentEntity comment = commentRepository.findCommentByIdAndAds_Id(id, adId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        return commentMapper.toCommentDtoFromComment(comment);
    }

    public String getUserNameOfComment(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"))
                .getUser().getEmail();
    }
}
