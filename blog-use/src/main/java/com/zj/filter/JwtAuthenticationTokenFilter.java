package com.zj.filter;


import com.zj.common.BusinessException;
import com.zj.common.ErrorCode;
import com.zj.common.RedisPrefix;
import com.zj.entity.LoginUser;
import com.zj.utils.JwtUtil;

import com.zj.utils.RedisUtils;
import com.zj.utils.ThreadLocalUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//默认的过滤器在不同的版本中可能存在问题，比如一个请求会被多次过滤，所以选择继承spring提供的OncePerRequestFilter。
    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行，springsecurity后面的过滤器会拦截
            filterChain.doFilter(request, response);
            return;//防止请求返回时继续执行过滤器
        }
        //解析token
        String userId;
        try {
            userId = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
//            throw new BusinessException(ErrorCode.LOGIN_OUT,"未登录或登录超时");
            filterChain.doFilter(request, response);
            return;//防止请求返回时继续执行过滤器
        }
        //从redis中获取用户信息
        String redisKey = RedisPrefix.BLOG_LOGIN_KEY + userId;
        LoginUser loginUser =  redisUtils.getObject(redisKey, LoginUser.class);
        if(loginUser == null){
//            throw new BusinessException(ErrorCode.LOGIN_OUT,"未登录或登录超时");
            filterChain.doFilter(request, response);
            return;//防止请求返回时继续执行过滤器
        }


        //保存信息
        ThreadLocalUtil.saveUser(loginUser.getUser());
        //刷新时间
        redisUtils.refreshTime(redisKey,20L, TimeUnit.MINUTES);

        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
//        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();

        //三参数构造会设置认证状态为已经认证，就不需要后面的认证了
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        //放行
        filterChain.doFilter(request, response);

        ThreadLocalUtil.removeUser();
    }
}