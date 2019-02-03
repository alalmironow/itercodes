package ru.mironow.ui.menuexes;

import org.springframework.beans.factory.annotation.Value;
import ru.mironow.statistics.Statistic;
import ru.mironow.ui.events.EventWindow;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Реализация окна с задачами
 *
 * Created By Root to date 20.01.2019
 */
public class MenuExesWindowImpl implements MenuExecWindow {

    @Value("${ui.menu_exec.title}")
    private String FRAME_TITLE;
    @Value("${ui.menu_exec.width}")
    private int WIDTH;
    @Value("${ui.menu_exec.height}")
    private int HEIGHT;

    private JFrame mainFrame;
    private ArrayList<JButton> buttons;

    private Statistic statistic;

    @Override
    public void show(Statistic statistic) {
        this.statistic = statistic;
        for(int i = 0; i < buttons.size(); i++) {
            if(i > statistic.getNumberLastExe()) {
                buttons.get(i).setEnabled(false);
            }
        }
        mainFrame.setVisible(true);
    }

    @Override
    public void dispose() {
        mainFrame.dispose();
    }

    @Override
    public Statistic getStatistic() {
        return statistic;
    }

    @Override
    public void setClickOneExeButtonByNumber(Integer numberButton, final EventWindow eventWindow) {
        buttons.get(numberButton).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventWindow.action();
            }
        });
    }

    @PostConstruct
    private void buildWindow() {
        mainFrame = new JFrame(FRAME_TITLE);
        mainFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
        Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((int)monitor.getWidth()/2 - WIDTH/2, (int) monitor.getHeight()/2 - HEIGHT/2);
        mainFrame.setResizable(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.setMaximumSize(new Dimension(200, 100));
        contentPanel.setLayout(new GridLayout(2,1,0,5));

        buttons = new ArrayList<>();
        JButton exeButton1 = new JButton("Кодирование");
        //JButton exeButton2 = new JButton("<html><p align=\"center\">Декодирование с </br>одной ошибкой<p></html>");
        buttons.add(exeButton1);
        //buttons.add(exeButton2);

        contentPanel.add(exeButton1);
        //contentPanel.add(exeButton2);

        mainFrame.getContentPane().add(contentPanel);
    }
}