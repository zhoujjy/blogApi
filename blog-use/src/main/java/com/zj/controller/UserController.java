package com.zj.controller;

import com.zj.Dto.params.LoginParam;
import com.zj.common.R;
import com.zj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param loginParam
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginParam loginParam){
        return userService.login(loginParam);
    }

    /**
     * 当前登录
     * @return
     */
    @GetMapping("/currentUser")
    public R getCurrentUser(){
        return userService.getCurrentUser();
    }

    @GetMapping("/logout")
    public R logout(){
        return userService.logout();
    }

    @PostMapping("/register")
    public R register(@RequestBody LoginParam loginParam){
        return userService.register(loginParam);
    }


}
