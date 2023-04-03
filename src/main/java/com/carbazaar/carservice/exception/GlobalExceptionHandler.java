package com.carbazaar.carservice.exception;

import com.carbazaar.carservice.pojo.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> ResponseDto<T> handleApiValidationException(ApiValidationException e) {

        log.error("Got ApiValidationException with Message: {}", e.getMessage(), e.getError());

        return ResponseDto.failure(e.getMessage(), e.getCode(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> ResponseDto<T> handleAssetNotFoundException(AssetNotFoundException e) {

        log.error("Got AssetNotFoundException with Message: {}", e.getMessage(), e.getError());

        return ResponseDto.failure(e.getMessage(), e.getCode(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResponseDto<T> handleInternalServerException(HttpServerErrorException.InternalServerError e) {
        System.out.println(e);
        log.error("Got Exception with Message: {}", e.getMessage(), e.getCause());

        return ResponseDto.failure(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResponseDto<T> handleException(Exception e) {

        Arrays.stream(e.getStackTrace()).forEach(System.out::println);
        log.error("Got Exception with Message: {}", e.getMessage(), e.getCause());

        return ResponseDto.failure(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
