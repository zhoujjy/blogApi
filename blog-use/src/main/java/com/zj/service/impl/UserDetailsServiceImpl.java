package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zj.common.BusinessException;
import com.zj.entity.LoginUser;
import com.zj.entity.Menu;
import com.zj.entity.User;
import com.zj.service.MenuService;
import com.zj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.List;
import java.util.stream.Collectors;

import static com.zj.common.ErrorCode.LOGIN_ERROR;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,username);
        User user = userService.getOne(queryWrapper);
        if(ObjectUtils.isEmpty(user)){
            throw new BusinessException(LOGIN_ERROR,"账号不存在");
        }
        List<Menu> menus = menuService.selectPermsByUserId(user.getId());
        List<String> perms = menus.stream().map(Menu::getMenuKey).collect(Collectors.toList());
        return new LoginUser(user,perms);
    }
}
