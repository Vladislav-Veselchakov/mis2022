package ru.mis2022.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> extends Response<T> {

    public SuccessResponse(T data) {
        this.code = 200;
        this.success = true;
        this.data = data;
    }

    public SuccessResponse() {
        this.code = 200;
        this.success = true;
    }
}
