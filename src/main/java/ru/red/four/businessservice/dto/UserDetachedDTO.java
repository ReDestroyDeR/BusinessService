package ru.red.four.businessservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDetachedDTO {
    private String username;
    private String description;
    private LocalDateTime birthDate;
}
