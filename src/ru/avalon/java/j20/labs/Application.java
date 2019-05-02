package ru.avalon.java.j20.labs;

import ru.avalon.java.j20.labs.core.Calculator;
import ru.avalon.java.j20.labs.core.Operation;
import ru.avalon.java.j20.labs.frames.ColorPicker;
import ru.avalon.java.j20.labs.frames.GuiForCalculator;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        JFrame colorPicker = new ColorPicker();
        colorPicker.setVisible(true);

        JFrame calculator = new GuiForCalculator();
        calculator.setVisible(true);
    }
}
