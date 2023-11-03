package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.adsDTO.*;
import javax.transaction.Transactional;

@Service
public interface CommentService {
    //Получает список комментариев к объявлению по его идентификатору
    CommentsDTO getComments(Integer id);

    //Добавляет новый комментарий к объявлению.
    CommentDTO addComment(Integer id, CreateCommentDTO createComment, String email);

    //Удаляет комментарий по идентификаторам объявления и комментария.
    @Transactional
    void deleteComment(Integer adId, Integer id, Authentication authentication);

    //Обновляет текст комментария по идентификаторам объявления и комментария.
    CommentDTO updateComment(Integer adId, Integer id, CreateCommentDTO createComment, Authentication authentication);

    //Получает объект CommentDto по идентификаторам объявления и комментария.
    CommentDTO getCommentDto(Integer adId, Integer id);

}
