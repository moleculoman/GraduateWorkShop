package ru.skypro.homework.mappers;

import org.mapstruct.*;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.usersDTO.*;
import ru.skypro.homework.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ///target - куда, source - откуда
    ///AdDTO - куда переносим, AdsEntity - откуда
    @Mapping(target = "email", source = "username")
    UserEntity toUser(Register register);

    default UserDTO toUserDto(UserEntity user) {
        return null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateUserFromUserDto(UserDTO userDto, @MappingTarget UserEntity user);
}
