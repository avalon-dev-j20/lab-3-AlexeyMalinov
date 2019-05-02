package ru.avalon.java.j20.labs.frames;

import ru.avalon.java.j20.labs.core.Calculator;
import ru.avalon.java.j20.labs.core.NumberBuilder;
import ru.avalon.java.j20.labs.core.Operation;
import ru.avalon.java.ui.AbstractFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class GuiForCalculator extends AbstractFrame {

    private class ComponentEventListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            int width = getWidth();
            int height = getHeight();
            if (width < MIN_WINDOW_WIDTH) {
                width = MIN_WINDOW_WIDTH;
            } else if (width > MAX_WINDOW_WIDTH) {
                width = MAX_WINDOW_WIDTH;
            }
            if (height < MIN_WINDOW_HEIGHT) {
                height = MIN_WINDOW_HEIGHT;
            } else if (height >= MAX_WINDOW_HEIGHT) {
                height = MAX_WINDOW_HEIGHT;
            }
            setSize(width, height);
        }
    }

    private Calculator calculator = new Calculator();
    private NumberBuilder numberBuilder = new NumberBuilder();

    private JButton[] numberButtons = new JButton[10];
    private List<JButton> operationButtons = new ArrayList<>();

    private JButton equallyButton = new JButton("=");
    private JButton separatorButton = new JButton(".");
    private JButton resetButton = new JButton("CE");

    private JLabel label = new JLabel();

    private boolean resultIsNotObtained = true;

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

    private void customizeLabel(JLabel label) {
        label.setText(START_DIGIT);
        label.setVerticalAlignment(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setFont(new Font(null, Font.BOLD, DIGITS_FONT_SIZE));
    }

    private void initializeNumberButton(JButton[] buttons) {
        for (int i = 0; i < 10; i++) {
            buttons[i] = new JButton(Integer.toString(i));
        }
    }

    private void initializeOperationButton(List<JButton> buttons) {
        for (Operation operation : Operation.values()) {
            buttons.add(new JButton(operation.getTitle()));
        }
    }

    private void onNumberButtonClick(ActionEvent e) {
        numberBuilder.append(e.getActionCommand());
        label.setText(numberBuilder.toString());
    }

    private void onOperationButtonClick(ActionEvent e) {
        calculate();
        for (Operation operation : Operation.values()) {
            if (e.getActionCommand().equals(operation.getTitle())) {
                calculator.setOperation(operation);
            }
        }
        resultIsNotObtained = true;
    }

    private void onEquallyButton(ActionEvent e) {
        resultIsNotObtained = true;
        calculate();
    }

    private void calculate() {
        if (resultIsNotObtained) {
            setCalculatorArguments();
            label.setText(calculator.calculate());
        }
        resultIsNotObtained = false;
    }

    private void onResetButton(ActionEvent e) {
        resultIsNotObtained = true;
        calculator.reset();
        numberBuilder.delete();
        label.setText(START_DIGIT);
    }

    private void setCalculatorArguments() {
        if (numberBuilder.isEmpty()) return;
        if (calculator.getArgument1() == null) {
            calculator.setArgument1(numberBuilder.getResult());
        } else {
            calculator.setArgument2(numberBuilder.getResult());
        }
        numberBuilder.delete();
    }

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

    private JPanel createSingleLinePanel(Component... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, components.length, GRIDLAYOUT_VERTICAL_INDENT, GRIDLAYOUT_HORIZONTAL_INDENT));
        for (Component comp : components) {
            comp.setFont(new Font(null, Font.BOLD, BUTTON_LABEL_FONT_SIZE));
            panel.add(comp);
        }
        return panel;
    }
}
