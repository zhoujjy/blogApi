package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.Dto.params.LoginParam;
import com.zj.common.R;
import com.zj.entity.User;


public interface UserService extends IService<User> {

    R login(LoginParam loginParam);

    R getCurrentUser();

    R logout();

    R register(LoginParam loginParam);
}

