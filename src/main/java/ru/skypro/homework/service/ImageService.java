package ru.skypro.homework.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entities.ImageEntity;

import java.io.IOException;


@Service
public interface ImageService {
    ImageEntity saveImage(MultipartFile image, String name);

    byte[] getImage(String name) throws IOException;

    void deleteFileIfNotNull(String path);

    ImageEntity downloadImage(MultipartFile image);
}
