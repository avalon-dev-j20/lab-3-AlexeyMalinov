package ru.avalon.java.j20.labs.core;

/**
 * Представление о калькуляторе выполняющим арифметические действия
 * над аргументами имеющими тип Double
 * <p>
 * <ol>
 * <li>argument1 - первый аргумент
 * <li>argument2 - второй аргумент
 * <li>operation - арифметическая операция
 * </ol>
 */
public class Calculator {
    private Double argument1;
    private Double argument2;
    private Operation operation;

    /**
     * Задает первый аргумент
     *
     * @param argument1
     */
    public void setArgument1(Double argument1) {
        this.argument1 = argument1;
    }

    /**
     * Задает второй аргумент
     *
     * @param argument2
     */
    public void setArgument2(Double argument2) {
        this.argument2 = argument2;
    }

    /**
     * Задает тип операции из поддерживаемых
     *
     * @param operation
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    /**
     * Возвращаяет значение первого аргумента или null если аргумент не задан
     *
     * @return
     */
    public Double getArgument1() {
        return argument1;
    }

    /**
     * Возвращает значение второго аргумент или null если аргумент не задан
     *
     * @return
     */
    public Double getArgument2() {
        return argument2;
    }

    /**
     * Возвращает тип операуции
     *
     * @return
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Производит вычисление
     *
     * @return
     */
    public String calculate() {
        if (argument1 == null) return "0";
        if ((argument2 != null) && (operation != null)) {
            switch (operation) {
                case PLUS:
                    argument1 += argument2;
                    break;
                case MINUS:
                    argument1 -= argument2;
                    break;
                case MULTIPLY:
                    argument1 *= argument2;
                    break;
                case DIVISION:
                    argument1 /= argument2;
                    break;
            }
        }
        return argument1.toString();
    }

    /**
     * Сбрасывает значения превого и второго аргумента и тип операции на null
     */
    public void reset() {
        deleteArgument1();
        deleteArgument2();
        deleteOperation();
    }

    /**
     * Сбрасывает значение первого аргумента на null
     */
    public void deleteArgument1() {
        argument1 = null;
    }

    /**
     * Сбрасывает значение второго аргумента на null
     */
    public void deleteArgument2() {
        argument2 = null;
    }

    /**
     * Сбрасывает тип операции на null
     */
    public void deleteOperation() {
        operation = null;
    }
}
