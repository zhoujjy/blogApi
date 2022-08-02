package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.Dto.UserDto;
import com.zj.Dto.params.LoginParam;
import com.zj.common.R;
import com.zj.entity.LoginUser;
import com.zj.entity.User;
import com.zj.mapper.UserMapper;
import com.zj.service.UserService;
import com.zj.utils.JwtUtil;
import com.zj.utils.RedisUtils;
import com.zj.utils.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.zj.common.RedisPrefix.BLOG_LOGIN_KEY;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtils redisUtils;

    @Value("${blog-config.tokenTTL}")
    private Integer tokenTTl;

    /**
     * 登录
     * @param loginParam
     * @return
     */
    @Override
    public R login(LoginParam loginParam) {
        //AuthenticationManage authenticate进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginParam.getAccount(), loginParam.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //认证成功后获得LoginUser对象
        LoginUser loginUser =(LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String token = JwtUtil.createJWT(user.getId().toString());
        //存入redis
        String redisKey = BLOG_LOGIN_KEY+user.getId();
        redisUtils.setObject(redisKey,loginUser,tokenTTl, TimeUnit.MINUTES);
        return R.success(token);
    }

    /**
     * 获得当前登录用户
     * @return
     */
    @Override
    public R getCurrentUser() {
        User user = ThreadLocalUtil.getUser();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return R.success(userDto);
    }

    @Override
    public R logout() {
        User user = ThreadLocalUtil.getUser();
        String redisKey = null;
        try {
            redisKey = BLOG_LOGIN_KEY+user.getId();
        }catch (Exception e){
            return R.success(null);
        }
        redisUtils.removeValue(redisKey);
        return R.success(null);
    }

    @Override
    public R register(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        String avatar = loginParam.getAvatar();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return R.fail(10001,"输入参数不正确");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null){
            return R.fail(10001,"账号已存在");
        }
        User newUser = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setAccount(account);
        newUser.setPassword(encoder.encode(password));
        newUser.setNickname(nickname);
        newUser.setAvatar(avatar);
        newUser.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userMapper.insert(newUser);
        return R.success(null);
    }
}

