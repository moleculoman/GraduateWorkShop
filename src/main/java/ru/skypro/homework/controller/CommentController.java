package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.*;
import lombok.experimental.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.service.*;

/**
 * Контроллер для работы с комментариями
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/ads")
public class CommentController {
    private final CommentService commentService;

    /**
     * Метод получения комментариев объявления
     */
    @Operation(summary = "Получение комментариев объявления")
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO> receivingAdComments(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }
    /**
     * Метод добавления комментария к объявлению
     */
    @Operation(summary = "Добавление комментария к объявлению")
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateCommentDTO createComment,
                                                 Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(id, createComment, authentication.getName()));
    }
    /**
     * Метод удаления комментария к объявлению
     */
    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
    /**
     * Метод добавления комментария
     */
    @Operation(summary = "Обновление комментария")
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateCommentDTO createComment) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, createComment));
    }
}
