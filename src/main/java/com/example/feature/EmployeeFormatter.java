package com.example.feature;


import com.example.model.Employee;

public interface EmployeeFormatter {
    String format(Employee employee);

    // Java 8 default method: implementation shared by implementors.
    default String shortLabel(Employee employee) {
        return employee.name() + " (" + employee.department() + ")";
    }

    // Java 8 static interface method.
    static String normalizeDepartment(String department) {
        return department == null ? "" : department.trim().toLowerCase();
    }
}
