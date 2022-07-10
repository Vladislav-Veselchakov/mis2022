package ru.mis2022.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mis2022.models.exception.ApiValidationException;
import ru.mis2022.models.response.Response;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalRestControllerExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    protected Response<Object> globalException(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), stringWriter.toString());
    }

    @ResponseBody
    @ExceptionHandler(ApiValidationException.class)
    protected Response<Void> ApiValidationExceptionHandler(ApiValidationException exception) {
        return Response.error(exception.getCode(), exception.getMessage());
    }
}
