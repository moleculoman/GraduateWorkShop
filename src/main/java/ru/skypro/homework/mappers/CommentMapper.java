package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.adsDTO.*;
import ru.skypro.homework.service.entities.AdsEntity;
import ru.skypro.homework.service.entities.CommentEntity;
import ru.skypro.homework.service.repositories.AdsRepository;
import ru.skypro.homework.service.repositories.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "author", source = "userEntity.id")
    @Mapping(target = "pk", source = "id")
    CommentDTO commentToCommentDto(CommentEntity Comment);
    List<CommentDTO> commentToCommentDto(List<CommentEntity> Comment);
    CommentEntity toCommentFromCreateComment(CreateCommentDTO createCommentDTO);

    @Mapping(target = "author", source = "userEntity.id")
    @Mapping(target = "authorImage", source = "userEntity.image")
    @Mapping(target = "authorFirstName", source = "userEntity.firstName")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "userEntity.id", ignore = true)
    @Mapping(target = "userEntity.image",ignore = true)
    @Mapping(target = "userEntity.firstName",ignore = true)
    CommentEntity commentDtoToComment(CommentDTO CommentDTO);
    List<CommentEntity> commentDtoToComment(List<CommentDTO> adsCommentDto);
    CommentDTO updateComment(CommentDTO commentDto);

}
