package ru.avalon.java.j20.labs.frames;

import ru.avalon.java.j20.labs.core.Calculator;
import ru.avalon.java.j20.labs.core.DoubleNumberBuilder;
import ru.avalon.java.j20.labs.core.NumberBuilder;
import ru.avalon.java.j20.labs.core.Operation;
import ru.avalon.java.ui.AbstractFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * Представление о графическом интерфейсе калькулятора
 * <p>
 * Графический интерфейс имеет следующие основные элементы:
 * <ol> calculator - калькулятор который непосредственно выполняет все вычисления;
 * <li> numberBuilder - строитель чисел позволяющий построить;
 * <li> numberButtons - кнопки набора цифр;
 * <li> operationButtons - кнопка выбора операции;
 * <li> separatorButton - кнопка разделителя;
 * <li> equallyButton - кнопка подсчета результата;
 * <li> resetButton - кнопка сброса;
 * <li> label - отображение текущего результата;
 * <li> clipboard - буфер обмена
 * </ol>
 */
public class GuiForCalculator extends AbstractFrame {

    /**
     * Слушатель событий позволяющий обработать изменение размер окна.
     * Создан в связи с тем, что метод setMaximumSize() не работает.
     * К сожалению даже с данной реализацией наблюдаются проблемы с зависанием окна.
     * Хотя само ограничение работает.
     */
    private class ComponentEventListener extends ComponentAdapter {

        /**
         * {@inheritDoc}
         *
         * @param e
         */
        @Override
        public void componentResized(ComponentEvent e) {
            setSize(Math.max(Math.min(getWidth(), MAX_WINDOW_WIDTH), MIN_WINDOW_WIDTH),
                    Math.max(Math.min(getHeight(), MAX_WINDOW_HEIGHT), MIN_WINDOW_HEIGHT)
            );
        }
    }

    private Calculator calculator = new Calculator();
    private NumberBuilder<Double> numberBuilder = new DoubleNumberBuilder();

    private JButton[] numberButtons = new JButton[10];
    private List<JButton> operationButtons = new ArrayList<>();

    private JButton equallyButton = new JButton("=");
    private JButton separatorButton = new JButton(".");
    private JButton resetButton = new JButton("CE");

    private JLabel label = new JLabel();

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Clipboard clipboard = toolkit.getSystemClipboard();

    private boolean resultObtained = false;

    public static final int GRIDLAYOUT_VERTICAL_INDENT = 10;
    public static final int GRIDLAYOUT_HORIZONTAL_INDENT = 10;
    public static final int BORDER_LINE_THICKNESS = 10;
    public static final int GRIDLAYOUT_MAIN_PANEL_ROWS = 6;
    public static final int GRIDLAYOUT_MAIN_PANEL_COLS = 1;
    public static final int MIN_WINDOW_WIDTH = 400;
    public static final int MIN_WINDOW_HEIGHT = 500;
    public static final int MAX_WINDOW_WIDTH = 800;
    public static final int MAX_WINDOW_HEIGHT = 900;
    public static final int DIGITS_FONT_SIZE = 50;
    public static final int BUTTON_LABEL_FONT_SIZE = 20;
    public static final String START_DIGIT = "0";

    /**
     * Метод создающий все объекты окна при его открытии
     */
    @Override
    protected void onCreate() {
        initializeNumberButton(numberButtons);
        initializeOperationButton(operationButtons);
        customizeLabel(label);
        addComponentListener(new ComponentEventListener());
        setListenersForButtons();
        setTitle("Calculator");
        setSize(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        setLayout(new GridLayout(
                GRIDLAYOUT_MAIN_PANEL_ROWS,
                GRIDLAYOUT_MAIN_PANEL_COLS,
                GRIDLAYOUT_HORIZONTAL_INDENT,
                GRIDLAYOUT_VERTICAL_INDENT));
        JPanel mainPanel = (JPanel) getContentPane();
        Border border = BorderFactory.createLineBorder(getBackground(), BORDER_LINE_THICKNESS);
        mainPanel.setBorder(border);
        add(label);
        add(createSingleLinePanel(numberButtons[7], numberButtons[8], numberButtons[9], operationButtons.get(0)));
        add(createSingleLinePanel(numberButtons[4], numberButtons[5], numberButtons[6], operationButtons.get(1)));
        add(createSingleLinePanel(numberButtons[1], numberButtons[2], numberButtons[3], operationButtons.get(2)));
        add(createSingleLinePanel(resetButton, numberButtons[0], separatorButton, operationButtons.get(3)));
        add(createSingleLinePanel(equallyButton));
    }

    /**
     * Настраивает отображение текущего результата вычисления
     *
     * @param label
     */
    private void customizeLabel(JLabel label) {
        updateResult(START_DIGIT);
        label.setVerticalAlignment(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setFont(new Font(null, Font.BOLD, DIGITS_FONT_SIZE));
    }

    /**
     * Инициализирует массив кнопок цифр
     *
     * @param buttons
     */
    private void initializeNumberButton(JButton[] buttons) {
        for (int i = 0; i < 10; i++) {
            buttons[i] = new JButton(Integer.toString(i));
        }
    }

    /**
     * Инициализирует список доступных операций
     *
     * @param buttons
     */
    private void initializeOperationButton(List<JButton> buttons) {
        for (Operation operation : Operation.values()) {
            buttons.add(new JButton(operation.getTitle()));
        }
    }

    /**
     * Определяет действия выполняемые при нажатии на кнопку c цифрами или разделителем.
     *
     * @param e
     */
    private void onNumberButtonClick(ActionEvent e) {
        numberBuilder.append(e.getActionCommand());
        updateResult(numberBuilder.toString());
    }

    /**
     * Определяет действия выполняемые при нажатии на кнопку с арифметической операцией.
     *
     * @param e
     */
    private void onOperationButtonClick(ActionEvent e) {
        calculate();
        for (Operation operation : Operation.values()) {
            if (e.getActionCommand().equals(operation.getTitle())) {
                calculator.setOperation(operation);
            }
        }
        resultObtained = false;
    }

    /**
     * Определяет действия выполняемые при нажатии на кнопку вычисления результата
     *
     * @param e
     */
    private void onEquallyButton(ActionEvent e) {
        resultObtained = false;
        calculate();
    }

    /**
     * Вычисляет результат
     */
    private void calculate() {
        if (resultObtained) return;
        setCalculatorArguments();
        updateResult(calculator.calculate());
        resultObtained = true;
    }

    /**
     * Определяет действия выполняемые при нажатии на кнопку сброса всех предыдущих вычислений
     *
     * @param e
     */
    private void onResetButton(ActionEvent e) {
        resultObtained = false;
        calculator.reset();
        numberBuilder.delete();
        updateResult(START_DIGIT);
    }

    /**
     * Задает значение аргументов
     */
    private void setCalculatorArguments() {
        if (numberBuilder.isEmpty()) return;
        if (calculator.getArgument1() == null) {
            calculator.setArgument1(numberBuilder.getResult());
        } else {
            calculator.setArgument2(numberBuilder.getResult());
        }
        numberBuilder.delete();
    }

    /**
     * Задает слушателей для всех кнопок калькулятора
     */
    private void setListenersForButtons() {
        for (JButton button : numberButtons) {
            button.addActionListener(this::onNumberButtonClick);
        }
        for (JButton button : operationButtons) {
            button.addActionListener(this::onOperationButtonClick);
        }
        separatorButton.addActionListener(this::onNumberButtonClick);
        equallyButton.addActionListener(this::onEquallyButton);
        resetButton.addActionListener(this::onResetButton);
    }

    /**
     * Создает однострочную панель из компонент передаваемых на вход
     *
     * @param components
     * @return
     */
    private JPanel createSingleLinePanel(Component... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, components.length, GRIDLAYOUT_VERTICAL_INDENT, GRIDLAYOUT_HORIZONTAL_INDENT));
        for (Component comp : components) {
            comp.setFont(new Font(null, Font.BOLD, BUTTON_LABEL_FONT_SIZE));
            panel.add(comp);
        }
        return panel;
    }

    /**
     * Копирует текст в буфер обмена
     *
     * @param text
     */
    private void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }

    /**
     * Обновляет выводимый результат вычисления
     *
     * @param text
     */
    private void updateResult(String text) {
        label.setText(text);
        copyToClipboard(text);
    }
}
