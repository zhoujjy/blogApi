package com.zj.handler;

import com.zj.common.BusinessException;
import com.zj.common.ErrorCode;
import com.zj.common.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//对加了controller注解的方法进行拦截处理 aop的实现
@RestControllerAdvice
public class GlobalExceptionHandler {

    //处理exception异常
    @ExceptionHandler(Exception.class)
    public R doException(Exception ex){
        ex.printStackTrace();
        return R.fail(-999,"系统异常");
    }

    @ExceptionHandler(BusinessException.class)
    public R doBusinessException(BusinessException ex){
        ex.printStackTrace();
        return R.fail(ex.getCode(),ex.getMessage());
    }

    //全局异常处理优先于security自定义失败处理，因此自定义失败处理将不会生效，此处选择将其抛出交给security自定义异常处理
//    @ExceptionHandler(AuthenticationException.class)
//    public void accessDeniedException(AuthenticationException e) throws AuthenticationException {
//        throw e;
//    }
    //处理security异常
    @ExceptionHandler(AuthenticationException.class)
    public R accessDeniedException(AuthenticationException e){
        return R.fail(ErrorCode.LOGIN_ERROR,e.getMessage());
    }
    //权限认证异常
//    @ExceptionHandler(AccessDeniedException.class)
//    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
//        throw e;
//    }
    @ExceptionHandler(AccessDeniedException.class)
    public R accessDeniedException(AccessDeniedException e) {
        return R.fail(ErrorCode.ACCESSIONED_ERROR,"权限不足");
    }
}
