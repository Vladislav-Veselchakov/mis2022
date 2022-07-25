package ru.mis2022.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mis2022.models.exception.ApiValidationException;
import ru.mis2022.models.response.Response;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiValidationException.class)
    public <T>Response<T> handleConflict(ApiValidationException ex) {
        return Response.error(ex.getCode(), ex.getMessage());
    }
}
