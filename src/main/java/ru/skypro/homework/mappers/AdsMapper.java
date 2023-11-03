package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.entities.AdsEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    AdsEntity toAdsFromCreateAds(CreateAdsDTO createAds);

    @Mapping(source = "author.id", target = "author")
    AdDTO adsToAdsDto(AdsEntity ads);
    List<AdDTO> adsToAdsDto(List<AdsEntity> ads);

    @Mapping(source = "author", target = "author.id")
    @Mapping(target = "description", ignore = true)
    AdsEntity adsDtoToAds(AdDTO adsDto);
    List<AdsEntity> adsDtoToAds(List<AdDTO> adsDto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "users.firstName")
    @Mapping(target = "authorLastName", source = "users.lastName")
    @Mapping(target = "email", source = "users.email")
    @Mapping(target = "phone", source = "users.phone")
    FullAdDTO toFullAds(AdsEntity fullAds);
    AdsEntity createAdsToAds(CreateAdsDTO createAds);
    AdDTO updateAds(CreateAdsDTO createAds);
}
