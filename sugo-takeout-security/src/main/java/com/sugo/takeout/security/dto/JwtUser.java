package com.sugo.takeout.security.dto;

import com.sugo.takeout.bean.model.Role;
import com.sugo.takeout.bean.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
public class JwtUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private User user;
    private Map<String, Object> attachData;
    private Collection<? extends GrantedAuthority> authorities;



    public JwtUser() {
    }

    /**
     * 写一个能直接使用user创建jwtUser的构造器
     * @param user 用户对象
     */
    public JwtUser(User user, Role role, Map<String, Object> attachData) {
        this.user = user;
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(role.getName()));
        this.attachData = attachData;
    }

    public JwtUser(User user, Role role) {
        this(user, role, new HashMap<>());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
