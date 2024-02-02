package com.ame.rest;


import com.ame.dto.RestResponse;
import com.ame.dto.RestResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.Set;

@CrossOrigin
public abstract class BaseController {

    // log
    protected abstract Logger getLogger();

    @ExceptionHandler(value = {Exception.class})
    public RestResponse exception(Exception e, HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();

        restResponse.setMessage(e.getMessage()).setSuccess(false).setCode(RestResponseCode.ERROR)
            .setPath(request.getRequestURI()).setException(e.getClass().getName());
        getLogger().error("{}接口请求失败，内容如下：{}", request.getRequestURI(), e.getMessage(), e);
        return restResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String errorMessage = allErrors.stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse(e.getMessage());
        restResponse.setMessage(errorMessage).setSuccess(false).setCode(RestResponseCode.ERROR)
            .setPath(request.getRequestURI()).setException(e.getClass().getName());
        getLogger().error("{}接口参数验证失败1，内容如下：{}", request.getRequestURI(), errorMessage, e);
        return restResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RestResponse handle(ConstraintViolationException e, HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String errorMessage =
            violations.stream().findFirst().map(ConstraintViolation::getMessage).orElse(e.getMessage());
        restResponse.setMessage(errorMessage).setSuccess(false).setCode(RestResponseCode.ERROR)
            .setPath(request.getRequestURI()).setException(e.getClass().getName());
        getLogger().error("{}接口参数验证失败2，内容如下：{}", request.getRequestURI(), errorMessage, e);
        return restResponse;
    }

}
