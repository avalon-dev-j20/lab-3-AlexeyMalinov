package ru.avalon.java.j20.labs.frames;

import ru.avalon.java.ui.AbstractFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Представление об окне выбора палитры цветов
 * <p>
 * Окну палитры цветов характеры следующие объекты:
 * <ol>
 * <li> controlPanel - панель регулировки цветов;
 * <li> colorPanel - панель вывода цвета;
 * <li> redSlider - регулировка оттенков красного цвета в палитре;
 * <li> greenSlider - регулировка оттенков зеленого цвета в палитре;
 * <li> blueSlider - регулировка оттенков синего цвета в палитре;
 * <li> clipboard - буфер обмена
 * </ol>
 */
public class ColorPicker extends AbstractFrame {
    /**
     * Слуштель событий создаваемых мышкой
     */
    private class MouseEventListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            updateToolTip();
        }
    }

    private JPanel controlPanel = new JPanel();
    private JPanel colorPanel = new JPanel();

    private JSlider redSlider = new JSlider();
    private JLabel redLabel = new JLabel("Red:");

    private JSlider greenSlider = new JSlider();
    private JLabel greenLabel = new JLabel("Green:");

    private JSlider blueSlider = new JSlider();
    private JLabel blueLabel = new JLabel("Blue");

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Clipboard clipboard = toolkit.getSystemClipboard();

    public static final String TITLE = "Color Picker";
    public static final int SLIDER_MIN_VALUE = 0;
    public static final int SLIDER_MAX_VALUE = 255;
    public static final int START_WINDOW_WIDTH = 700;
    public static final int START_WINDOW_HEIGHT = 300;
    public static final int SLIDER_MINOR_TICK_SPACING = 5;
    public static final int PARAMETER_LABEL_WIDTH = 40;
    public static final int PARAMETER_LABEL_HEIGHT = 0;
    public static final int START_SLIDER_POSITION = 125;
    public static final int BORDER_LINE_THICKNESS = 10;

    /**
     * Метод создающий все объекты окна при его открытии
     */
    @Override
    protected void onCreate() {
        setTitle(TITLE);
        setSize(START_WINDOW_WIDTH, START_WINDOW_HEIGHT);
        setLayout(new GridLayout());
        redSlider = createSlider();
        greenSlider = createSlider();
        blueSlider = createSlider();
        add(createColorPanel());
        add(createControlPanel());
        redSlider.addChangeListener(this::onSliderChange);
        greenSlider.addChangeListener(this::onSliderChange);
        blueSlider.addChangeListener(this::onSliderChange);
        MouseListener listener = new MouseEventListener();
        addMouseListener(listener);
    }

    /**
     * Создает панель вывода цвета
     *
     * @return
     */
    private JPanel createColorPanel() {
        colorPanel.setLayout(new BorderLayout());
        updateColor();
        Border border = BorderFactory.createLineBorder(this.getContentPane().getBackground(), BORDER_LINE_THICKNESS);
        colorPanel.setBorder(border);
        return colorPanel;
    }

    /**
     * Обновление выводимого цвета
     */
    private void updateColor() {
        colorPanel.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
    }

    /**
     * Создание панель регулировки цветов
     *
     * @return
     */
    private JPanel createControlPanel() {
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(createPanelForOneParameter(redLabel, redSlider));
        controlPanel.add(createPanelForOneParameter(greenLabel, greenSlider));
        controlPanel.add(createPanelForOneParameter(blueLabel, blueSlider));
        return controlPanel;
    }

    /**
     * Содание панели для одного регулируемого параметра
     *
     * @param label
     * @param slider
     * @return
     */
    private JPanel createPanelForOneParameter(JLabel label, JSlider slider) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Dimension dimension = new Dimension(PARAMETER_LABEL_WIDTH, PARAMETER_LABEL_HEIGHT);
        label.setPreferredSize(dimension);

        panel.add(label, BorderLayout.LINE_START);
        panel.add(slider);

        return panel;
    }


    /**
     * Создает ползунок
     *
     * @return
     */
    private JSlider createSlider() {
        JSlider slider = new JSlider(SLIDER_MIN_VALUE, SLIDER_MAX_VALUE);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(SLIDER_MINOR_TICK_SPACING);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(SLIDER_MAX_VALUE);
        slider.setPaintLabels(true);
        slider.setValue(START_SLIDER_POSITION);
        return slider;
    }

    /**
     * Определяет действия при изменении положении ползунка
     *
     * @param e
     */
    private void onSliderChange(ChangeEvent e) {
        updateColor();
    }

    /**
     * Обновляет всплывающую подсказку
     */
    private void updateToolTip() {
        Integer red = colorPanel.getBackground().getRed();
        Integer green = colorPanel.getBackground().getGreen();
        Integer blue = colorPanel.getBackground().getBlue();
        String hex = ("#" + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue)).toUpperCase();
        colorPanel.setToolTipText(hex);
        copyToClipboard(hex);
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

}
