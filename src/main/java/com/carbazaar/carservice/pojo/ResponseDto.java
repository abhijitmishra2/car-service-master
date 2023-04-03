package com.carbazaar.carservice.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseDto<T> {

    private boolean status;

    private String message;

    private String errorCode;

    private T data;

    private int httpStatusCode;

    private ResponseDto(boolean status) {
        this.status = status;
    }

    protected ResponseDto(boolean status, String message) {
        this(status);
        this.message = message;
    }

    protected ResponseDto(T data, int httpStatusCode) {
        this.data = data;
        this.httpStatusCode = httpStatusCode;
    }

    protected ResponseDto(boolean status, String message, T data) {
        this(status, message);
        this.data = data;
    }

    private ResponseDto(boolean status, String message, String errorCode, int httpStatusCode) {
        this(status, message);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(data, HttpStatus.OK.value());
    }

    public static <T> ResponseDto<T> success(T data, int httpStatusCode) {
        return new ResponseDto<>(data, httpStatusCode);
    }

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>(true, message, data);
    }

    public static <T> ResponseDto<T> success(String message) {
        return new ResponseDto<>(true, message, null);
    }

    public static <T> ResponseDto<T> failure(String message, String errorCode, int value) {
        return new ResponseDto<>(false, message, errorCode, value);
    }
}
