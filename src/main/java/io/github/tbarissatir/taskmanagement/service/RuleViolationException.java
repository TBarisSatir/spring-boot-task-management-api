package io.github.tbarissatir.taskmanagement.service;

public class RuleViolationException extends RuntimeException {
    public RuleViolationException(String rule) {
        super("Task violates rule: " + rule);
    }
}

