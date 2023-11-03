package ru.skypro.homework.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.entities.UserEntity;
import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {
    private UserEntity user;

    // Конструктор класса MyUserDetails,
    // принимающий объект класса AuthUser.
    public MyUserDetails(UserEntity user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ user.getRole());
        return Collections.singletonList(authority);
    }

    @Override
    // Возвращает пароль пользователя.
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    // Возвращает имя пользователя.
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    // Возвращает true, если аккаунт пользователя не истек.
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // Возвращает true, если аккаунт пользователя не заблокирован.
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // Возвращает true, если учётные данные пользователя не истекли.
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // Возвращает true, если пользователь включён (активен).
    public boolean isEnabled() {
        return true;
    }
}