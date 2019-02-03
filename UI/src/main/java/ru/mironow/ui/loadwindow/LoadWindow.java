package ru.mironow.ui.loadwindow;

import ru.mironow.ui.components.ImagePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Класс для начального окна загрузки экрана
 *
 * Created By Root to date 24.01.2019
 */
public class LoadWindow extends JFrame {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;

    public LoadWindow() {
        bulidWindow();
    }

    /**
     * Метод для сборки окна загрузки
     */
    private void bulidWindow() {
        setSize(WIDTH, HEIGHT);
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(
                (int)(resolution.getWidth()/2 - WIDTH/2),
                (int)(resolution.getHeight()/2 - HEIGHT/2)
        );
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setUndecorated(true);

        ImagePanel root = new ImagePanel("load.png");
        root.setLayout(new FlowLayout());
        root.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font font = new Font(Font.SANS_SERIF,  Font.BOLD, 30);
        JLabel nameProgramm = new JLabel(
                "<html><font color=\"white\">Циклические коды</font></html>"
        );
        JLabel loadText = new JLabel("<html><font color=\"white\">Загрузка...</font></html>");
        nameProgramm.setFont(font);
        loadText.setFont(font);

        root.add(nameProgramm);
        root.add(loadText);
        getContentPane().add(root);
    }
}