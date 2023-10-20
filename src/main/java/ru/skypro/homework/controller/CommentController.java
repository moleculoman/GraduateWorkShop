package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.*;
import lombok.experimental.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.service.AdsService;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/ads")
public class CommentController {
    private final AdsService adsService;

    @Operation(summary = "Получение комментариев объявления")
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO> receivingAdComments(@PathVariable int id) {
        return ResponseEntity.ok(adsService.getComments(id));
    }

    @Operation(summary = "Добавление комментария к объявлению")
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateCommentDTO createComment,
                                                 Authentication authentication) {
        return ResponseEntity.ok(adsService.addComment(id, createComment, authentication.getName()));
    }

    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        adsService.deleteComment(adId, commentId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Обновление комментария")
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateCommentDTO createComment) {
        return ResponseEntity.ok(adsService.updateComment(adId, commentId, createComment));
    }
}
