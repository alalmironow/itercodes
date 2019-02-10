package ru.mironow.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Таблица из полей ввода
 *
 * Created By Root to date 20.01.2019
 */
public class TableFromFields extends JPanel {

    private JTextField[][] textFields;
    private int x;
    private int y;

    public TableFromFields(int coordX, int coordY, int size, int x, int y, boolean enabled) {
        this.x = x;
        this.y = y;
        int sizeField = size/x;
        setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
        setBounds(coordX, coordY,x*(sizeField + sizeField/5), y*(sizeField + sizeField/5));
        Font font = new Font("Arial",Font.BOLD,sizeField);
        textFields = new JTextField[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                textFields[i][j] = generateCell(font, sizeField, enabled);
                add(textFields[i][j]);
            }
        }
    }

    public void createNewStructure(int coordX, int coordY, int size, int x, int y, boolean enabled) {
        this.x = x;
        this.y = y;
        int sizeField = size/x;
        removeAll();
        setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
        setBounds(coordX, coordY,x*(sizeField), x*(sizeField));
        Font font = new Font("Arial",Font.BOLD, sizeField);
        textFields = new JTextField[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                textFields[i][j] = generateCell(font, sizeField, enabled);
                add(textFields[i][j]);
            }
        }
        repaint();
    }

    /**
     * Получить данные
     * @return
     */
    public ArrayList<ArrayList<Boolean>> getData() throws Exception {
        ArrayList<ArrayList<Boolean>> result = new ArrayList<>();
        for(int i = 0; i < x; i++) {
            result.add(new ArrayList<Boolean>());
            for(int j = 0; j < y; j++) {
                Integer data = Integer.parseInt(textFields[i][j].getText());
                switch (data) {
                    case 0:
                    case 1:
                        result.get(i).add(data == 1);
                        break;
                    default:
                        throw new Exception();
                }
            }
        }
        return result;
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