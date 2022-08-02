package com.zj.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.common.BusinessException;
import com.zj.common.ErrorCode;
import com.zj.common.R;
import com.zj.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    //自定义认证异常处理
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(R.fail(ErrorCode.LOGIN_OUT,"未登录或登录超时"));
        WebUtils.renderString(response, json);

    }
}