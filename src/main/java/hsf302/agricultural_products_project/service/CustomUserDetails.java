package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {


 private  User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public String getFullName() {
        return user.getUserFullName();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getAddress() {
        return user.getAddress();
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    public User getUser() {
        return user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
        return user.getStatus().equals("active");
    }
}
