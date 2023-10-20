package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.service.entities.*;
import java.util.*;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    AdsEntity toAdsFromCreateAds(CreateAdsDTO createAds);

    @Mapping(target = "author", source = "userEntity.id")
    @Mapping(target = "pk", source = "id")
    AdDTO toAdsDto(AdsEntity ads);

    List<AdDTO> toDtos(List<AdsEntity> adsList);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "userEntity.firstName")
    @Mapping(target = "authorLastName", source = "userEntity.lastName")
    @Mapping(target = "email", source = "userEntity.email")
    @Mapping(target = "phone", source = "userEntity.phone")
    default FullAdDTO toFullAds(List<AdsEntity> ads) {
        return null;
    }

    default void updateAds(CreateAdsDTO createAds, @MappingTarget List<AdsEntity> ads) {

    }
}
