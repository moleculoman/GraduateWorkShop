package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.service.entities.*;

import java.util.List;

@Mapper
public interface AdsMapper {
    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class );
    AdsEntity toAdsFromCreateAds(CreateAdsDTO createAds);

    @Mapping(source = "author.id", target = "author")
    AdDTO adsToAdsDto(AdsEntity ads);
    List<AdDTO> adsToAdsDto(List<AdsEntity> ads);

    @Mapping(source = "author", target = "author.id")
    @Mapping(target = "description", ignore = true)
    AdsEntity adsDtoToAds(AdDTO adsDto);
    List<AdsEntity> adsDtoToAds(List<AdDTO> adsDto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "userEntity.firstName")
    @Mapping(target = "authorLastName", source = "userEntity.lastName")
    @Mapping(target = "email", source = "userEntity.email")
    @Mapping(target = "phone", source = "userEntity.phone")
    FullAdDTO toFullAds(AdsEntity fullAds);
    AdsEntity createAdsToAds(CreateAdsDTO createAds);
    AdDTO updateAds(CreateAdsDTO createAds);
}
