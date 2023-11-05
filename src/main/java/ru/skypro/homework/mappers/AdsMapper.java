package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.entities.AdsEntity;
import ru.skypro.homework.entities.ImageEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    AdsEntity toAdsFromCreateAds(CreateAdsDTO createAds);

    @Mapping(target = "author",qualifiedByName = "mapImageEntityToString", source = "user.id")
    @Named("mapImageEntityToString")
    default String mapImageEntityToString(ImageEntity imageEntity) {
        return imageEntity != null ? (imageEntity.toString()) : null;
    }
    AdDTO adsToAdsDto(AdsEntity ads);
                                          ///target - куда, source - откуда
           ///AdDTO - куда переносим, AdsEntity - откуда
    List<AdDTO> adsToAdsDto(List<AdsEntity> ads);


    @Mapping(target = "user.id",source = "author")
    AdsEntity adsDtoToAds(AdDTO adsDto);
    List<AdsEntity> adsDtoToAds(List<AdDTO> adsDto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorLastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phone", source = "user.phone")
    FullAdDTO toFullAds(AdsEntity fullAds);
    AdsEntity createAdsToAds(CreateAdsDTO createAds);
    AdDTO updateAds(CreateAdsDTO createAds);


}
