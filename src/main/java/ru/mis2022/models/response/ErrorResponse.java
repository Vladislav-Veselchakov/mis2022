package ru.mis2022.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse<T> extends Response<T> {

    private String text;

    public ErrorResponse(int code, String text) {
        this.success = false;
        this.code = code;
        this.text = text;
    }
}
