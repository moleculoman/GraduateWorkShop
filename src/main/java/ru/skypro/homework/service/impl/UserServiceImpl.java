package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.usersDTO.*;
import ru.skypro.homework.exceptions.*;
import ru.skypro.homework.mappers.*;
import ru.skypro.homework.service.*;
import ru.skypro.homework.entities.*;
import ru.skypro.homework.repositories.*;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ImageService imageService;
    private final UserMapper userMapper;

    //Устанавливает новый пароль пользователю.

    @Override
    public boolean setPassword(NewPasswordDTO newPassword, String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (encoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(newPassword.getNewPassword()));
                userRepository.save(user);
                log.trace("Updated password");
                return true;
            }
        }
        log.trace("Password not update");
        return false;
    }

    //Получает объект UserDto по адресу электронной почты пользователя.
    @Override
    public UserDTO getUser(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
        return userMapper.toUserDto(user);
    }

    //Обновляет данные пользователя.
    @Override
    public UserDTO updateUser(UserDTO userDto, String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        userMapper.updateUserFromUserDto(userDto, user);
        userRepository.save(user);
        log.trace("User updated");
        return userMapper.toUserDto(user);
    }

    //Обновляет аватар пользователя
    @Override
    public void updateAvatar(MultipartFile image, String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserWithEmailNotFoundException(email));
        imageService.deleteFileIfNotNull(String.valueOf(user.getImage()));
        user.setImage(imageService.saveImage(image, "/users"));
        userRepository.save(user);
        log.trace("Avatar updated");
    }

    //Получает изображение по его имени
    @Override
    public byte[] getImage(String name) throws IOException {
        return imageService.getImage(name);
    }
}
