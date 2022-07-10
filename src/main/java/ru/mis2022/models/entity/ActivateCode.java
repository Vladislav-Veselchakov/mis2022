package ru.mis2022.models.entity;


import java.util.Date;

public class ActivateCode {
    private Long id;
    private String code;
    private Date date;
    private Patient patient;

    @Override
    public String toString() {
        return code;
    }
}
