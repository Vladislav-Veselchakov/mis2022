package ru.mis2022.models.dto.talon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TalonsBusyTotal {
    protected String date;
    protected Long busyTalons;
    protected Long totalTalons;
}
