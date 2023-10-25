package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.service.entities.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    AdsEntity toAdsFromCreateAds(CreateAdsDTO createAds);

    @Mapping(target = "author", source = "userEntity.id")
    @Mapping(target = "pk", source = "id")

    AdDTO toAdDto(AdsEntity ads);
    AdDTO toAdsDto(List<AdsEntity> ads);
    FullAdDTO toFullAds(AdsEntity ads);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "userEntity.firstName")
    @Mapping(target = "authorLastName", source = "userEntity.lastName")
    @Mapping(target = "email", source = "userEntity.email")
    @Mapping(target = "phone", source = "userEntity.phone")

    CreateAdsDTO updateAds(CreateAdsDTO createAds);
}
