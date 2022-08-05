package ru.mis2022.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    protected Boolean success;
    protected T data;
    protected int code;

    public static <T> Response<T> ok() {
        return new SuccessResponse<>();
    }

    public static <T> Response<T> ok(T data) {
        return new SuccessResponse<>(data);
    }

    public static <T> Response<T> error(int code, String text) {
        return new ErrorResponse<>(code, text);
    }
}
