package ru.avalon.java.j20.labs.core;

/**
 * Абстрактное представление о строителе числа
 */
public interface NumberBuilder<E extends Number> {

    /**
     * Добавляет элемент в строитель числа
     */
    NumberBuilder append(String digit);

    /**
     * Очищает строитель от всех ранее добавленных элементов
     */
    void delete();

    /**
     * Возвращает результат построения числа
     *
     * @return число
     */
    E getResult();

    /**
     * Возвращает true если в строитель не добавлен ни один элемент
     *
     * @return
     */
    boolean isEmpty();
}
