package ru.avalon.java.j20.labs.core;

import java.util.regex.Pattern;

/**
 * Представление о строителе чисел типа Double.
 * Строит числа из цифр
 */
public class DoubleNumberBuilder implements NumberBuilder<Double> {
    private Double result = 0d;
    private Double exponent = 0d;
    private boolean hasSeparator = false;
    private boolean empty = true;
    private Pattern digitPattern = Pattern.compile("[0-9]");
    private Pattern separatorPattern = Pattern.compile("[//.,//,]");


    /**
     * {@inheritDoc}
     *
     * @param text
     */
    @Override
    public NumberBuilder append(String text) {
        if (isDigit(text)) {
            addDigit(text);
            empty = false;
        } else if (!hasSeparator && isSeparator(text)) {
            exponent = -1d;
            hasSeparator = true;
            empty = false;
        }
        return this;
    }


    /**
     * Расчитывает результат при добавлении новой цифры
     *
     * @param digit
     */
    private void addDigit(String digit) {
        if (!hasSeparator) {
            result = result * Math.pow(10d, exponent) + Double.valueOf(digit);
            exponent = 1d;
        } else {
            result += Math.pow(10d, exponent) * Double.valueOf(digit);
            exponent--;
        }
    }

    /**
     * Проверяет является ли строка цифрой
     *
     * @param text
     * @return
     */
    private boolean isDigit(String text) {
        return digitPattern.matcher(text).find();
    }

    /**
     * Проверяет является ли строка разделителем
     *
     * @param text
     * @return
     */
    private boolean isSeparator(String text) {
        return separatorPattern.matcher(text).find();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Double getResult() {
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String toString() {
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete() {
        result = 0d;
        exponent = 0d;
        empty = true;
        hasSeparator = false;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return empty;
    }
}
