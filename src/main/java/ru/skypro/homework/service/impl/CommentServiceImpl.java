package ru.skypro.homework.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.exceptions.*;
import ru.skypro.homework.mappers.*;
import ru.skypro.homework.service.*;
import ru.skypro.homework.service.entities.*;
import ru.skypro.homework.service.repositories.*;


import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    //Получает список комментариев к объявлению по его идентификатору
    @Override
    public CommentsDTO getComments(Integer id) {
        List<CommentEntity> commentList = commentRepository.findAll();
        return getCommentsDTO(commentList);
    }

    //Добавляет новый комментарий к объявлению.
    @Override
    public CommentDTO addComment(Integer id, CreateCommentDTO createComment, String email) {
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found"));
        CommentEntity comment = commentMapper.toCommentFromCreateComment(createComment);
        commentRepository.save(comment);
        log.trace("Added comment with id: ", comment.getId());
        return commentMapper.commentToCommentDto(comment);
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
        return commentMapper.commentToCommentDto(comment);
    }


    //Получает объект CommentDto по идентификаторам объявления и комментария.
    @Override
    public CommentDTO getCommentDto(Integer adId, Integer id) {
        CommentEntity comment = commentRepository.findCommentByIdAndAds_Id(id, adId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        return commentMapper.commentToCommentDto(comment);
    }

    public String getUserNameOfComment(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"))
                .getUser().getEmail();
    }

    private CommentsDTO getCommentsDTO(List<CommentEntity> commentList) {
        List<CommentDTO> commentDtoList = commentMapper.commentToCommentDto(commentList);
        CommentsDTO commentsDTO = new CommentsDTO();
        if (!commentDtoList.isEmpty()) {
            commentsDTO.setCount(commentDtoList.size());
            commentsDTO.setResults(commentDtoList);
        }
        return commentsDTO;
    }



}
