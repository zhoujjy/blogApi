package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.entity.Menu;

import java.util.List;


public interface MenuService extends IService<Menu> {


    List<Menu> selectPermsByUserId(Integer id);
}

