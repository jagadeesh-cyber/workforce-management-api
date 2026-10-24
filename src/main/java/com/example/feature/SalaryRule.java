package com.example.feature;


import com.example.model.Employee;

@FunctionalInterface
public interface SalaryRule {
    boolean isEligible(Employee employee);
}
