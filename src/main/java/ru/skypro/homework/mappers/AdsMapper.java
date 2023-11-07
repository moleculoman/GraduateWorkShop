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
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "id", ignore = true)
    AdsEntity toAdsFromCreateAds(CreateAdsDTO createAds);

    @Named("imageToPathString")
    default String imageToPathString(ImageEntity image) {
        return image != null ? ("/ads/image/" + image.getId()) : null;
    }

    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPathString")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "user.id")
    AdDTO adsToAdsDto(AdsEntity ads);
    List<AdDTO> adsToAdsDto(List<AdsEntity> ads);

    /*@Mapping(target = "user.id",source = "author")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPathString")
    AdsEntity adsDtoToAds(AdDTO adsDto);
    List<AdsEntity> adsDtoToAds(List<AdDTO> adsDto);*/

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorLastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPathString")

    FullAdDTO toFullAds(AdsEntity fullAds);
    AdsEntity createAdsToAds(CreateAdsDTO createAds);
    AdDTO updateAds(CreateAdsDTO createAds);


}
