package com.example.controller;


import com.example.model.EmployeeCreated;
import com.example.model.EmployeeDeleted;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/features")
public class FeatureController {

    @GetMapping("/java17")
    public Map<String, Object> java17Features() {
        // Java 15 text block.
        String textBlockExample = """
                Java 17 feature examples:
                - records
                - sealed interfaces
                - pattern matching for instanceof
                - switch expressions
                """;

        Object event = new EmployeeCreated(42L, "Sample Employee");

        // Java 16 pattern matching for instanceof.
        String patternMatchingResult;
        if (event instanceof EmployeeCreated created) {
            patternMatchingResult = "Created employee " + created.employeeName()
                    + " with id " + created.employeeId();
        } else if (event instanceof EmployeeDeleted deleted) {
            patternMatchingResult = "Deleted employee id " + deleted.employeeId();
        } else {
            patternMatchingResult = "Unknown event";
        }

        // Java 14 switch expression (standard Java 17; no preview flags needed).
        String switchResult = switch ("CREATE") {
            case "CREATE" -> "CREATE:" + ((EmployeeCreated) event).employeeId();
            case "DELETE" -> "DELETE:" + ((EmployeeDeleted) event).employeeId();
            default -> "UNKNOWN";
        };

        return Map.of(
                "textBlock", textBlockExample,
                "patternMatchingInstanceof", patternMatchingResult,
                "switchExpression", switchResult,
                "recordExample", event
        );
    }

    @GetMapping("/java8")
    public Map<String, Object> java8Features() throws ExecutionException, InterruptedException {
        // CompletableFuture is available since Java 8.
        CompletableFuture<String> asyncGreeting = CompletableFuture.supplyAsync(
                () -> "Hello from CompletableFuture"
        );
        String greeting = asyncGreeting.thenApply(String::toUpperCase).get();

        return Map.of(
                "lambda", "employees.stream().filter(e -> e.active())",
                "methodReference", "Employee::name",
                "optional", "Optional.ofNullable(value).orElse(defaultValue)",
                "streams", "filter, map, sorted, reduce, groupingBy, partitioningBy",
                "completableFuture", greeting
        );
    }
}
