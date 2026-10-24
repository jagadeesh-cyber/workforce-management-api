package com.example.model;

public record EmployeeCreated(Long employeeId, String employeeName) implements EmployeeEvent {
}
