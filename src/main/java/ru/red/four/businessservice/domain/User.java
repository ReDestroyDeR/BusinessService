package ru.red.four.businessservice.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String description;
    private LocalDateTime birthDate;
}
