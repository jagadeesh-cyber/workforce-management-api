package com.example.model;

// Java 17 sealed types restrict the permitted implementations.
public sealed interface EmployeeEvent permits EmployeeCreated, EmployeeDeleted {
    Long employeeId();
}
