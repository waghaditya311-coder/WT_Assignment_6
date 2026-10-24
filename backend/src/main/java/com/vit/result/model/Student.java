package com.vit.result.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(unique = true)
    private String prn;

    @NotBlank
    private String name;

    @NotBlank
    private String department;

    @Min(1) @Max(8)
    private int semester;

    private String division;
    private String email;
    private String phone;
}
