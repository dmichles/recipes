package com.example.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String category;

    @UpdateTimestamp
    private LocalDateTime date;

    @NotNull
    @NotBlank
    private String description;

    @Size(min = 1)
    @NotEmpty
    @ElementCollection
    private List<String> ingredients;

    @Size(min = 1)
    @NotEmpty
    @ElementCollection
    private List<String> directions;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userid1", referencedColumnName = "userid")
    @JsonIgnore
    private User user;

}
