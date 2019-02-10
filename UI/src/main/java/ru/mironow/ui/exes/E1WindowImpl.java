package ru.mironow.ui.exes;

import org.springframework.beans.factory.annotation.Value;
import ru.mironow.exes.DificultExe;
import ru.mironow.statistics.Statistic;
import ru.mironow.ui.components.TableFromFields;
import ru.mironow.ui.events.EventWindow;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Реализация интерфейса окна задачи 1
 *
 * Created By Root to date 20.01.2019
 */
public class E1WindowImpl implements E1Window {

    @Value("${ui.exe1.title}")
    private String FRAME_TITLE;
    @Value("${ui.exe1.width}")
    private int WIDTH;
    @Value("${ui.exe1.height}")
    private int HEIGHT;

    private final int heightTable = 400;
    private final int tableCoordX = 300;
    private final int tableCoordY = 80;

    private JFrame mainFrame;

    private ButtonGroup difucults;
    private JButton generateButton;
    private JButton execButton;
    private JLabel textExe;
    private TableFromFields tableFromFields;

    private Statistic statistic;

    @PostConstruct
    private void buildWindow() {
        mainFrame = new JFrame(FRAME_TITLE);
        mainFrame.setTitle(FRAME_TITLE);
        mainFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        mainFrame.setResizable(false);
        Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((int)monitor.getWidth()/2 - WIDTH/2, (int) monitor.getHeight()/2 - HEIGHT/2);
        Container content = mainFrame.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel root = new JPanel();
        root.setLayout(null);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6,0,1,5));
        leftPanel.setBounds(10,10, 200, 200);

        Label label = new Label("Выберите сложность");
        difucults = new ButtonGroup();
        JRadioButton easy = new JRadioButton("Легкая", true);
        easy.setMnemonic(0);
        difucults.add(easy);
        JRadioButton medium = new JRadioButton("Средняя", false);
        medium.setMnemonic(1);
        difucults.add(medium);
        JRadioButton hard = new JRadioButton("Сложная", false);
        hard.setMnemonic(2);
        difucults.add(hard);

        generateButton = new JButton("Генерация");
        execButton = new JButton("Кодировать");
        execButton.setEnabled(false);

        textExe = new JLabel();
        textExe.setBounds(10, 220, 200, 300);

        leftPanel.add(label);
        leftPanel.add(easy);
        leftPanel.add(medium);
        leftPanel.add(hard);
        leftPanel.add(generateButton);
        leftPanel.add(execButton);

        tableFromFields = new TableFromFields(tableCoordX, tableCoordY, heightTable,3,3, true);

        root.add(leftPanel);
        root.add(tableFromFields);
        root.add(textExe);
        mainFrame.getContentPane().add(root);
    }

    @Override
    public void show(Statistic statistic) {
        mainFrame.setVisible(true);
        execButton.setEnabled(false);
        textExe.setText("");
        setCodeTable(3,3);
        this.statistic = statistic;
    }

    @Override
    public void dispose() {
        mainFrame.dispose();
    }

    @Override
    public void setOnCloseOperation(final EventWindow eventWindow) {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventWindow.action();
                super.windowClosing(e);
            }
        });
    }

    @Override
    public Statistic getStatistic() {
        return statistic;
    }

    @Override
    public void setEnabled(boolean enable) {
        mainFrame.setEnabled(enable);
    }

    @Override
    public void clickByGenerateValue(final EventWindow eventWindow) {
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventWindow.action();
            }
        });
    }

    @Override
    public void clickByExecuteButton(final EventWindow eventWindow) {
        execButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventWindow.action();
            }
        });
    }

    @Override
    public Integer getNumberDifucult() {
        return difucults.getSelection().getMnemonic();
    }

    @Override
    public DificultExe getDificult() {
        return DificultExe.valueOfNumber(getNumberDifucult());
    }

    @Override
    public void setTextExe(String textExe) {
        this.textExe.setText(textExe);
    }

    @Override
    public void setCodeTable(int rows, int cols) {
        tableFromFields.createNewStructure(tableCoordX, tableCoordY, heightTable,rows, cols, true);
    }

    @Override
    public void setEnabledCodeButton(boolean enable) {
        execButton.setEnabled(enable);
    }

    @Override
    public TableFromFields getTable() {
        return tableFromFields;
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Внимание! Ошибка!", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String showInputDialog(String message, String title) {
        JPasswordField pf = new JPasswordField();
        Object[] messageData = {message, pf};
        int returnCode = JOptionPane.showConfirmDialog(mainFrame, messageData,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        String result = null;
        if(returnCode == JOptionPane.OK_OPTION) {
            result = pf.getText();;
        }
        return result;
    }
}