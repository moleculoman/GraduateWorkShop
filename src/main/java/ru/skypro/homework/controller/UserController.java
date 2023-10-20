package ru.skypro.homework.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.usersDTO.*;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.UserService;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/ads")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Обновление пароля")
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDTO> setPassword(@RequestBody NewPasswordDTO newPassword,
                                                      Authentication authentication) {
        userService.setPassword(newPassword, authentication.getName());
        return ResponseEntity.ok(newPassword);
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto,
                                              Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(userDto, authentication.getName()));
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam MultipartFile image,
                                             Authentication authentication) {
        userService.updateAvatar(image, authentication.getName());
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}