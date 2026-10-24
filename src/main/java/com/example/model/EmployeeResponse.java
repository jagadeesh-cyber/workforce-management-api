package com.example.model;

import java.time.LocalDateTime;

public record EmployeeResponse(Employee employee, LocalDateTime servedAt) {
}
