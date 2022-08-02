package com.zj.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;
    private List<String> permissions;
    @JsonIgnore //该字段不被序列化到redis中
    private  List<SimpleGrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked= true;
    private boolean credentialsNonExpired= true;
    private boolean enabled= true;

    //没有无参构造，反序列化会报错
    public LoginUser(){}

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //把permissions中String类型的权限信息封装成GrantedAuthority的实现类
        //map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。permission->new SimpleGrantedAuthority(permission)
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        this.password = user.getPassword();
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        this.username = user.getAccount();
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
