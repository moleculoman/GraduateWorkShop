package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.entities.AdsEntity;
import ru.skypro.homework.exceptions.*;
import ru.skypro.homework.mappers.*;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.*;
import javax.transaction.Transactional;
import java.io.*;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final ImageService imageService;
    private final AdsMapper adsMapper;


    //Получить список всех объявлений.
    @Override
    public AdsDTO getAllAds() {
        List<AdsEntity> adsList = adsRepository.findAll();
        return getAdsDTO(adsList);
    }

    // Получает список объявлений, принадлежащих пользователю с указанным адресом электронной почты.

    @Override
    public AdDTO getAdsMe(String email) {
        return adsMapper.adsToAdsDto(adsRepository.findByEmail(email));
    }

    //Добавляет новое объявление в базу данных.
    @Override
    public AdDTO addAd(CreateAdsDTO createAds, String email, MultipartFile image) {
        AdsEntity ads = adsMapper.toAdsFromCreateAds(createAds);
        adsRepository.save(ads);
        return adsMapper.adsToAdsDto(ads);
    }

    //Получает полную информацию об объявлении по его идентификатору.
    @Override
    public FullAdDTO getAds(Integer id) {
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        return adsMapper.toFullAds(ads);
    }


    //Удаляет объявление по его идентификатору.

    @Transactional
    @Override
    public void removeAd(Integer id, Authentication authentication) {
        commentRepository.deleteAllByAds_Id(id);
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        imageService.deleteFileIfNotNull(String.valueOf(ads.getImage()));
        log.trace("Removed Ads with id: ", id);
        adsRepository.delete(ads);
    }


    //Обновляет информацию об объявлении по его идентификатору.
    @Override
    public AdDTO updateAds(CreateAdsDTO createAds, Integer id, Authentication authentication) {
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found by id: " + id));
        adsMapper.updateAds(createAds);
        adsRepository.save(ads);
        log.trace("Updated Ads with id: ", id);
        return adsMapper.adsToAdsDto(ads);
    }

    //Обновляет изображение объявления по его идентификатору.
    @Override
    public void updateAdsImage(Integer id, MultipartFile image, Authentication authentication) {
        AdsEntity ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads not found"));
        imageService.deleteFileIfNotNull(String.valueOf(ads.getImage()));
        ads.setImage(imageService.saveImage(image, "/ads"));
        adsRepository.save(ads);
    }

    //Получает изображение по его имени.
    @Override
    public byte[] getImage(String name) throws IOException {
        return imageService.getImage(name);
    }


    private AdsDTO getAdsDTO(List<AdsEntity> adsList) {
        List<AdDTO> adsDtoList = adsMapper.adsToAdsDto(adsList);
        AdsDTO adsDTO = new AdsDTO();
        if (!adsDtoList.isEmpty()) {
            adsDTO.setCount(adsDtoList.size());
            adsDTO.setResults(adsDtoList);
        }
        return adsDTO;
    }

}
