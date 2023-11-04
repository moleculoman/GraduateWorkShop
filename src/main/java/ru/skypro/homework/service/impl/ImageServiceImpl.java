package ru.skypro.homework.service.impl;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.usersDTO.UserDTO;
import ru.skypro.homework.entities.AdsEntity;
import ru.skypro.homework.entities.ImageEntity;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.*;

import java.io.*;
import java.nio.file.*;
import java.rmi.NotBoundException;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private static final String DOWNLOAD_DIRECTORY = "src/main/resources/images/";
    private AdsRepository adsRepository;
    private UserRepository repositoryUser;


    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    //Cохранение названия UUID картинки в БД и локально

    @Override
    public ImageEntity saveImage(MultipartFile image, String name) {
        return null;
    }

    @Override
    public byte[] getImage(String name) throws IOException {
        return new byte[0];
    }

    @Override
    public void deleteFileIfNotNull(String path) {
    }

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
    public static <T> T checkValidateObj(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }
    @Transactional
    public byte[] updateImage(Integer id, MultipartFile avatar) throws ChangeSetPersister.NotFoundException {
        checkValidateObj(avatar);
        AdsEntity ads = adsRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        try {
            byte[] bytes = avatar.getBytes();
            ads.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adsRepository.saveAndFlush(ads).getImage();
    }

    public byte[] showImage(Integer id) throws ChangeSetPersister.NotFoundException {
        return adsRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new).getImage();
    }

    public boolean updateAvatar(Authentication authentication, MultipartFile avatar) throws IOException {
        UserEntity user = UserMapper.customUserDetailsToUser((UserDTO) authentication.getPrincipal());
        user.setImage(checkValidateObj(avatar).getBytes());
        repositoryUser.save(user);
        return true;
    }

    public byte[] showAvatarOnId(Integer id) throws ChangeSetPersister.NotFoundException {
        return repositoryUser.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new).getImage();
    }


}