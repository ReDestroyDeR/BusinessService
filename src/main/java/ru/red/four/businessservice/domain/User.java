package ru.red.four.businessservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("users")
public class User {
    @Id
    private Long id;
    private String username;
    private String description;
    private LocalDate birthDate;

    public User enrichWithId(Long id) {
        this.setId(id);
        return this;
    }
}
