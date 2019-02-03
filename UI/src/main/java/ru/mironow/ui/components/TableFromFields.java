package ru.mironow.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Таблица из полей ввода
 *
 * Created By Root to date 20.01.2019
 */
public class TableFromFields extends JPanel {

    private JTextField[][] textFields;
    private int x;
    private int y;

    public TableFromFields(int coordX, int coordY, int sizeField, int x, int y, boolean enabled) {
        this.x = x;
        this.y = y;
        setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
        setBounds(coordX, coordY,x*sizeField, y*sizeField);
        Font font = new Font("Arial",Font.BOLD,sizeField);
        textFields = new JTextField[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                textFields[i][j] = generateCell(font, sizeField, enabled);
                add(textFields[i][j]);
            }
        }
    }

    /**
     * Получить данные
     * @return
     */
    public Integer[][] getData() {
        Integer[][] data = new Integer[x][y];
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                data[i][j] = Integer.parseInt(textFields[i][j].getText());
            }
        }
        return data;
    }

    private JTextField generateCell(Font font, int sizeField, boolean enabled) {
        final JTextField field = new JTextField(1);
        field.setPreferredSize(new Dimension(sizeField, sizeField));
        field.setEnabled(enabled);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setFont(font);
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if((e.getKeyChar() != '1' && e.getKeyChar() != '0') || field.getText().length() >= 1) {
                    e.consume();
                }
            }
        });
        return field;
    }
}