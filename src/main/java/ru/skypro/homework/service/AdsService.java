package ru.skypro.homework.service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.entities.UserEntity;

import java.io.IOException;

@Service
public interface AdsService {
    AdsDTO getAllAds();
    AdDTO findAllByUser_Email(UserEntity user);

    AdDTO addAd(CreateAdsDTO createAds, String email, MultipartFile image);
    //Обновляет информацию об объявлении по его идентификатору.
    @PreAuthorize("@securityService.canRefAdd(#id)")
    AdDTO updateAds(CreateAdsDTO createAds, Integer id, Authentication authentication);
    FullAdDTO getAds(Integer id);
    @PreAuthorize("@securityService.canRefAdd(#id)")
    void removeAd(Integer id, Authentication authentication);

    //Обновляет изображение объявления по его идентификатору.
    @PreAuthorize("@securityService.canRefAdd(#id)")
    void updateAdsImage(Integer id, MultipartFile image, Authentication authentication);

    //Получает изображение по его имени.
    byte[] getImage(String name) throws IOException;
}
