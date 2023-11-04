package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.io.IOException;
/**
 * Контроллер для работы с картинками
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageServiceImpl imageService;

    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserImage(@PathVariable String id) throws IOException {
        return imageService.getImage(id);
    }
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@adsService.getById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateImage(@PathVariable Integer id, @RequestParam("image") MultipartFile avatar) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(imageService.updateImage(id, avatar));
    }


    @Operation(hidden = true)
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showImage(@PathVariable("id") Integer id) throws ChangeSetPersister.NotFoundException {
        return imageService.showImage(id);
    }
    @PatchMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(Authentication authentication, MultipartFile avatar) throws IOException {
        return ResponseEntity.ok(imageService.updateAvatar(authentication, avatar));
    }

    @Operation(hidden = true)
    @GetMapping(value = "/user/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showAvatarOnId(@PathVariable("id") Integer id) throws ChangeSetPersister.NotFoundException {
        return imageService.showAvatarOnId(id);
    }
}
