package ru.skypro.homework.dto.usersDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Login {
    private String username;
    private String password;
}
