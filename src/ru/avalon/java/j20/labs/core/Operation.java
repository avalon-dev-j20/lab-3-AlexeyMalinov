package ru.avalon.java.j20.labs.core;

/**
 * Перечисление всех доступных операций калькулятора
 */
public enum Operation {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVISION("/");

    private String title;

    Operation(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
