package ua.project.SafeSellSafeBuy.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ua.project.SafeSellSafeBuy.models.User;

import java.util.Collection;
import java.util.Collections;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final User user;

    public UserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SHOW_ACCOUNT, WITHDRAW_MONEY, SEND_MONEY or
        // ROLE_ADMIN, ROLE_USER
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//Need what will be get inform for authentication
    public User getUser(){
        return this.user;
    }
}
