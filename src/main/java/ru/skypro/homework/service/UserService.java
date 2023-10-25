package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.usersDTO.*;

import java.io.IOException;

public interface UserService {

    boolean setPassword(NewPasswordDTO newPassword, String email);

    UserDTO getUser(String email);

    UserDTO updateUser(UserDTO userDto, String email);

    void updateAvatar(MultipartFile image, String email);

    byte[] getImage(String name) throws IOException;
}
