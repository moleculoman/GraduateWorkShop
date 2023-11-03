package ru.skypro.homework.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exceptions.AdsNotFoundException;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.entities.AdsEntity;
import ru.skypro.homework.entities.CommentEntity;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.CommentRepository;

import java.util.Optional;

@Log4j2
@Service
public class SecurityService {

    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;

    public SecurityService(AdsRepository adsRepository, CommentRepository commentRepository) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Проверяем, что пользователь удаляющий коментарий == создатель или АДМИН
     */
    public boolean canDeleteComment(int commentId, int adId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        CommentEntity commentEntity = checkForAdAndComment(adId, commentId);
        String name = commentEntity.getUser().getEmail();

        return authentication.getName().equals(name);
    }
    /**
     * Может ли пользователь работать с объявлениями
     */
    public boolean canRefAdd(int id) {
        Optional<AdsEntity> adEntity = Optional.ofNullable(adsRepository.findById(id).orElseThrow(() -> new AdsNotFoundException(String.format(
                "Объявление с индексом \"%s\" не найдено ",
                id))
        ));
        String name = adEntity.get().getUser().getEmail();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            log.info(authentication.getAuthorities());
            return true;
        }

        return authentication.getName().equals(name);
    }

    private CommentEntity checkForAdAndComment(int adId, int commentId) {
        adsRepository.findById(adId).orElseThrow(() -> new AdsNotFoundException(
                String.format("Объявление с индексом \"%s\" не найдено.", adId)
        ));

        return commentRepository.findCommentByIdAndAds_Id(adId, commentId)
                .orElseThrow(() -> new CommentNotFoundException(String.format(
                        "Комментарий с индексом \"%s\" не найден для объявления с индексом \"%s\".",
                        commentId, adId))
                );
    }
}
