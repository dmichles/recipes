package com.example.recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userid;

    @NotBlank
    @Pattern(regexp =  ".+@.+\\..+")
    private String email;

    @NotBlank
    @Size (min = 8)
    private String password;
}
