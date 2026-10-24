package com.example.service;

import com.example.feature.EmployeeFormatter;
import com.example.feature.SalaryRule;
import com.example.model.Employee;
import com.example.model.EmployeeDeleted;
import com.example.model.EmployeeEvent;
import com.example.model.Gender;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Long, Employee> employees = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public EmployeeService() {
        // Java 9+ List.of factory method and Java 8 forEach/method reference.
        List.of(
                new Employee(null, "Jagadeesh Gajula", "Engineering", 92000, "ananya@example.com", true, Gender.MALE),
                new Employee(null, "Ravi Kumar", "Finance", 78000, "ravi@example.com", false,Gender.MALE),
                new Employee(null, "Meera Shah", "Engineering", 105000, "meera@example.com", false,Gender.MALE),
                new Employee(null, "Laxmi Adky", "Human Resources", 65000, "laxmiadky@example.com", true,Gender.FEMALE),
                new Employee(null, "Divya Nair", "Finance", 88000, "divya@example.com", false,Gender.FEMALE)
        ).forEach(employee -> {
            long id = sequence.incrementAndGet();
            employees.put(id, withId(employee, id));
        });
    }

    public List<Employee> findAll() {
        // Stream.toList() is available since Java 16 and returns an unmodifiable list.
        return employees.values().stream()
                .sorted(Comparator.comparing(Employee::id))
                .toList();
    }

    public Optional<Employee> findById(long id) {
        // Optional makes absence explicit.
        return Optional.ofNullable(employees.get(id));
    }

    public Employee create(Employee input) {
        long id = sequence.incrementAndGet();
        Employee saved = withId(input, id);
        employees.put(id, saved);
        return saved;
    }

    public Optional<Employee> update(long id, Employee input) {
        if (!employees.containsKey(id)) {
            return Optional.empty();
        }
        Employee updated = withId(input, id);
        employees.put(id, updated);
        return Optional.of(updated);
    }

    public Optional<EmployeeEvent> delete(long id) {
        Employee removed = employees.remove(id);
        return removed == null
                ? Optional.empty()
                : Optional.of(new EmployeeDeleted(id));
    }

    public List<Employee> searchByName(String name) {
        String query = Optional.ofNullable(name).orElse("").trim().toLowerCase();
        Predicate<Employee> nameMatches = employee -> employee.name().toLowerCase().contains(query);
        return employees.values().stream()
                .filter(nameMatches)
                .sorted(Comparator.comparing(Employee::name))
                .toList();
    }

    public Map<String, Object> analytics() {
        List<Employee> all = findAll();

        // Java 8 groupingBy, mapping, averagingDouble and counting collectors.
        Map<String, Long> countByDepartment = all.stream()
                .collect(Collectors.groupingBy(Employee::department, Collectors.counting()));

        Map<String, Double> averageSalaryByDepartment = all.stream()
                .collect(Collectors.groupingBy(Employee::department,
                        Collectors.averagingDouble(Employee::salary)));

        // partitioningBy creates true/false groups.
        Map<Boolean, List<String>> activeNames = all.stream()
                .collect(Collectors.partitioningBy(Employee::active,
                        Collectors.mapping(Employee::name, Collectors.toList())));

        // Java 8 reduce.
        double totalSalary = all.stream()
                .map(Employee::salary)
                .reduce(0.0, Double::sum);

        // Java 8 joining collector.
        String employeeNames = all.stream()
                .map(Employee::name)
                .sorted()
                .collect(Collectors.joining(", "));

        // Java 8 toMap and method references.
        Map<Long, String> idToName = all.stream()
                .collect(Collectors.toMap(Employee::id, Employee::name));

        // Custom functional interface + lambda.
        SalaryRule highEarnerRule = employee -> employee.salary() >= 90000;
        long highEarners = all.stream().filter(highEarnerRule::isEligible).count();

        // Function<T,R> lambda example.
        Function<Employee, String> label = employee -> employee.name() + " - " + employee.department();
        List<String> labels = all.stream().map(label).toList();

        // Default method and static method from Java 8 interface.
        EmployeeFormatter formatter = Employee::toString;
        String firstShortLabel = all.stream().findFirst()
                .map(formatter::shortLabel)
                .orElse("No employees");

        return Map.ofEntries(
                Map.entry("totalEmployees", all.size()),
                Map.entry("countByDepartment", countByDepartment),
                Map.entry("averageSalaryByDepartment", averageSalaryByDepartment),
                Map.entry("activeAndInactiveNames", activeNames),
                Map.entry("totalSalary", totalSalary),
                Map.entry("employeeNames", employeeNames),
                Map.entry("idToName", idToName),
                Map.entry("highEarnersSalaryAtLeast90000", highEarners),
                Map.entry("labels", labels),
                Map.entry("firstShortLabel", firstShortLabel),
                Map.entry("normalizedDepartmentExample", EmployeeFormatter.normalizeDepartment(" Engineering "))
        );
    }

    private Employee withId(Employee employee, Long id) {
        return new Employee(id, employee.name(), employee.department(), employee.salary(),
                employee.email(), employee.active(),employee.gender());
    }
}
