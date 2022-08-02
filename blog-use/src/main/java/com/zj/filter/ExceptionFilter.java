package com.zj.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.common.BusinessException;
import com.zj.common.ErrorCode;
import com.zj.common.R;
import com.zj.common.RedisPrefix;
import com.zj.entity.LoginUser;
import com.zj.utils.JwtUtil;
import com.zj.utils.RedisUtils;
import com.zj.utils.ThreadLocalUtil;
import com.zj.utils.WebUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Component
public class ExceptionFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        }catch (BusinessException e){
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(R.fail(e.getCode(),e.getMessage()));
            WebUtils.renderString(response, json);
        }
    }
}