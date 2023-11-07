package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.entities.CommentEntity;
import ru.skypro.homework.entities.ImageEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Named("localDateTimeToLong")
    default long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.ofHours(3)).toInstant().toEpochMilli();
    }

    @Named("imageToPathString")
    default String imageToPathString(ImageEntity image) {
        return image != null ? ("/ads/image/" + image.getId()) : null;
    }

    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorImage", source = "user.image", qualifiedByName = "imageToPathString")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "createdAt", qualifiedByName = "localDateTimeToLong")
    @Mapping(target = "text", source = "text")
    CommentDTO commentToCommentDto(CommentEntity Comment);
    List<CommentDTO> commentToCommentDto(List<CommentEntity> Comment);
    CommentEntity toCommentFromCreateComment(CreateCommentDTO createCommentDTO);

    @Mapping(target = "user.firstName", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.image", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CommentEntity commentDtoToComment(CommentDTO CommentDTO);
    /*
    List<CommentEntity> commentDtoToComment(List<CommentDTO> adsCommentDto);
    CommentDTO updateComment(CommentDTO commentDto);
    */

}
