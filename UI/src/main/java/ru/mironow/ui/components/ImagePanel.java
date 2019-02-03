package ru.mironow.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Панель с картинкой на фоне
 *
 * Created By Root to date 25.01.2019
 */
public class ImagePanel extends JPanel {

    private Image image;

    /**
     * Конструктор панели с фоном
     * @param resourcePath путь к файлу ресурсу
     */
    public ImagePanel(String resourcePath) {
        ClassLoader classloader = getClass().getClassLoader();
        java.net.URL url = classloader.getResource(resourcePath);
        ImageIcon imageIcon = new ImageIcon(url);
        image = imageIcon.getImage();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getRootPane().getWidth(), getRootPane().getHeight(),null);
    }
}