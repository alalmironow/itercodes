package ru.mironow.ui.login;

import org.springframework.beans.factory.annotation.Value;
import ru.mironow.ui.events.EventWindow;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Реализация интерфейса окна для авторизации
 *
 * Created By Root to date 19.01.2019
*/
public class LoginWindowImpl implements LoginWindow {

    @Value("${ui.login.title}")
    private String TITLE_FRAME;
    @Value("${ui.login.width}")
    private int WIDTH;
    @Value("${ui.login.height}")
    private int HEIGHT;

    private JFrame mainFrame;
    private JTextField loginField;
    private JButton loginButton;

    @Override
    public void show() {
        mainFrame.setVisible(true);
    }

    @Override
    public void dispose() {
        mainFrame.setVisible(false);
    }

    @Override
    public void clickByLoginButton(final LoginHandler loginHandler) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valueFromLoginField = loginField.getText();
                loginHandler.click(valueFromLoginField);
            }
        });
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showErrorWithClick(String message, EventWindow eventWindow) {
        JOptionPane.showMessageDialog(mainFrame, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
        eventWindow.action();
    }

    @Override
    public void setEnabled(boolean enable) {
        mainFrame.setEnabled(enable);
    }

    @PostConstruct
    private void buildWindow() {
        mainFrame = new JFrame(TITLE_FRAME);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setResizable(false);
        Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((int)monitor.getWidth()/2 - WIDTH/2, (int) monitor.getHeight()/2 - HEIGHT/2);
        Container content = mainFrame.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setMaximumSize(new Dimension(200, 150));
        jPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        jPanel.setLayout(new GridLayout(3,1));

        JLabel labelLoginField = new JLabel("<html>Введите ваше ФИО<br>(через пробел, используя <br>только русские буквы)</html>");
        jPanel.add(labelLoginField);

        loginField = new JTextField("");
        jPanel.add(loginField);

        loginButton = new JButton("Войти");
        jPanel.add(loginButton);

        content.add(jPanel);
    }
}