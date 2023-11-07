package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
/**
 * Контроллер для работы с картинками
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserImage(@PathVariable String id) throws IOException {
        return imageService.getImage(id);
    }
}
