package ru.mis2022.models.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiValidationException extends RuntimeException {
    private final int code;
    private final String message;
}
