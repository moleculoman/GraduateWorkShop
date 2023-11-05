package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.entities.CommentEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "pk", source = "id")
    CommentDTO commentToCommentDto(CommentEntity Comment);
    List<CommentDTO> commentToCommentDto(List<CommentEntity> Comment);
    CommentEntity toCommentFromCreateComment(CreateCommentDTO createCommentDTO);

    @Mapping(target = "user.image", source = "authorImage")
    @Mapping(target = "user.firstName", source = "authorFirstName")
    @Mapping(target = "id", source = "pk")
    CommentEntity commentDtoToComment(CommentDTO CommentDTO);
    List<CommentEntity> commentDtoToComment(List<CommentDTO> adsCommentDto);
    CommentDTO updateComment(CommentDTO commentDto);

}
