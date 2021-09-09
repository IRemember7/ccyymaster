package com.ccyy.manage.common.exception;

import com.ccyy.manage.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lianghanmao
 * @date 2021年07月22日 上午11:37
 * 捕获全局的异常,进行统一规范数据处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public BaseResponse handler(ShiroException e) {
        log.error("shiro权限异常:--------------->"+e.getMessage());
        return BaseResponse.fail(401, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse handler(RuntimeException e) {
        log.error("运行时异常:--------------->"+ e.getMessage());
        return BaseResponse.fail(500, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常:--------------->"+ e.getMessage());
        return BaseResponse.fail(500, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public BaseResponse handler(IllegalArgumentException e) {
        log.error("Assert异常:--------------->"+ e.getMessage());
        return BaseResponse.fail(500, e.getMessage());
    }
}
