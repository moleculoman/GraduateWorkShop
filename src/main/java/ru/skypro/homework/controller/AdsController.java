package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import lombok.*;
import lombok.experimental.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.security.SecurityService;
import ru.skypro.homework.service.*;


/**
 * Контроллер для работы c объявлениями
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/ads")
public class AdsController {
    private final AdsService adsService;
    /**
     * Метод получения всех объявлений
     */
    @Operation(summary = "Получение всех объявлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }
    /**
     * Метод добавления всех объявлений
     */
    @Operation(summary = "Добавление объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Unauthorized")}
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(Authentication authentication,
                                       @RequestPart("properties") CreateAdsDTO createAds,
                                       @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(adsService.addAd(createAds, authentication.getName(), image));
    }
    /**
     * Метод получения информации о объявлении
     */
    @Operation(summary = "Получение информации об объявлении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FullAdDTO> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getAds(id));
    }    /**
     * Метод удаления объявления
     */
    @Operation(summary = "Удаление объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Integer id,
                                      Authentication authentication) {
        adsService.removeAd(id,authentication);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
    /**
     * Метод обновления информации об объявлении
     */
    @Operation(summary = "Обновление информации об объявлении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateInfoByAd(@RequestBody CreateAdsDTO createAds,
                                            @PathVariable Integer id,
                                            Authentication authentication) {
        return ResponseEntity.ok(adsService.updateAds(createAds, id,authentication));
    }
    /**
     * Метод получения объявлений авторизованного пользователя
     */
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<AdDTO> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adsService.getAdsMe(authentication.getName()));
    }
    /**
     * Метод обновления картинки объявления
     */
    @Operation(summary = "Обновление картинки объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    @PatchMapping(value = "/{id}/image" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable Integer id,
                                            @RequestParam MultipartFile image,
                                            Authentication authentication)
    {
        adsService.updateAdsImage(id, image,authentication);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}