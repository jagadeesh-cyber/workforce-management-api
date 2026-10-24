package com.example.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record Employee(
        Long id,
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Department is required") String department,
        @PositiveOrZero(message = "Salary must be zero or positive") double salary,
        @Email(message = "Email must be valid") @NotBlank(message = "Email is required") String email,
        boolean active,
        Gender gender
) {
}
