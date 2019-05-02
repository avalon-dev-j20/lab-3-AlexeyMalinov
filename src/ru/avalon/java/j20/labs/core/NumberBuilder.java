package ru.avalon.java.j20.labs.core;

import java.util.regex.Pattern;

public class NumberBuilder {
    private Double result = 0d;
    private Double exponent = 0d;
    private boolean hasSeparator = false;
    private boolean empty = true;
    private Pattern digitPattern = Pattern.compile("[0-9]");
    private Pattern separatorPattern = Pattern.compile("[//.,//,]");


    public void append(String text) {
        if (isDigit(text)) {
            addDigit(text);
            empty = false;
        } else if (isSeparator(text) && !hasSeparator) {
            exponent = -1d;
            hasSeparator = true;
            empty = false;
        }
    }

    private void addDigit(String text) {
        if (!hasSeparator) {
            result = result * Math.pow(10d, exponent) + Double.valueOf(text);
            exponent = 1d;
        } else {
            result += Math.pow(10d, exponent) * Double.valueOf(text);
            exponent--;
        }
    }

    private boolean isDigit(String text) {
        return digitPattern.matcher(text).find();
    }

    private boolean isSeparator(String text) {
        return separatorPattern.matcher(text).find();
    }

    public Double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return result.toString();
    }

    public void delete() {
        result = 0d;
        exponent = 0d;
        empty = true;
        hasSeparator = false;
    }

    public boolean isEmpty() {
        return empty;
    }
}
