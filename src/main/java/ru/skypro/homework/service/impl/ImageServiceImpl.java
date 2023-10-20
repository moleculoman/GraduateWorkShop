package ru.skypro.homework.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.*;
import ru.skypro.homework.service.entities.*;
import ru.skypro.homework.service.repositories.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class ImageServiceImpl extends ImageService {

    private final ImageRepository imageRepository;
    private static final String DOWNLOAD_DIRECTORY = "src/main/resources/images/";


    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    //Cохранение названия UUID картинки в БД и локально

    @Override
    public ImageEntity downloadImage(MultipartFile image) {
        try {
            String imageId = UUID.randomUUID().toString();
            ImageEntity imageEntity = new ImageEntity();
            byte[] imageData = image.getBytes();

            File directory = new File(DOWNLOAD_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File imageFile = new File(directory, imageId + ".jpg");

            try (FileOutputStream fileOutputStream = new FileOutputStream(imageFile)) {
                fileOutputStream.write(imageData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            imageEntity.setImageName(imageId);
            imageRepository.saveAndFlush(imageEntity);
            return imageEntity;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения изображения " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<byte[]> getUserImage(String id) {
        try {
            String imagePath = DOWNLOAD_DIRECTORY + id + ".jpg";
            byte[] imageBytes = loadFile(imagePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки изображения " + e.getMessage());
        }
    }

    private byte[] loadFile(String path) throws IOException {
        Path imagePath = Paths.get(path);
        return Files.readAllBytes(imagePath);
    }
}