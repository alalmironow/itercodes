package ru.mironow.business.exes.exe1;

import org.springframework.stereotype.Component;
import ru.mironow.business.data.GenerateAlphabit;
import ru.mironow.exes.DificultExe;
import ru.mironow.statistics.DontSaveStatistic;
import ru.mironow.statistics.PasswordWrong;
import ru.mironow.statistics.StatisticService;
import ru.mironow.ui.events.EventWindow;
import ru.mironow.ui.exes.E1Window;
import ru.mironow.ui.menuexes.MenuExecWindow;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class Exe1UseCaseImpl implements Exe1UseCase {

    private final E1Window e1Window;
    private final MenuExecWindow menuExecWindow;
    private final GenerateAlphabit generateAlphabit;
    private final StatisticService statisticService;

    private Map<String, ArrayList<Boolean>> alphabit;
    private ArrayList<ArrayList<Boolean>> processExec;
    private String codeMessage;

    public Exe1UseCaseImpl(E1Window e1Window, MenuExecWindow menuExecWindow, GenerateAlphabit generateAlphabit, StatisticService statisticService) {
        this.e1Window = e1Window;
        this.menuExecWindow = menuExecWindow;
        this.generateAlphabit = generateAlphabit;
        this.statisticService = statisticService;
    }

    @Override
    @PostConstruct
    public void setLogic() {
        e1Window.clickByGenerateValue(new EventWindow() {
            @Override
            public void action() {
                e1Window.setEnabled(false);
                DificultExe dificultExe = DificultExe.valueOfNumber(e1Window.getNumberDifucult());
                addExe(dificultExe);
                e1Window.setEnabled(true);
            }
        });
        e1Window.clickByExecuteButton(new EventWindow() {
            @Override
            public void action() {
                e1Window.setEnabled(false);
                ArrayList<ArrayList<Boolean>> data;
                try {
                    data = e1Window.getTable().getData();
                    if(checkExecution(data)) {
                        e1Window.getStatistic().setNumberLastExe(1);
                        e1Window.getStatistic().getDataStatistic().put(1, e1Window.getDificult());
                        System.out.println(e1Window.getStatistic().getDataStatistic());
                        while (true) {
                            String password = e1Window.showInputDialog(
                                    "<html>" + e1Window.getStatistic().getUserName()
                                            + ", вы выполнили Задание 1!<br>"
                                            + "Пригласите преподавателя для сохранения статистики<br>"
                                            + "Введите пароль для сохранения данных на сервере:",
                                    "ПОЗДРАВЛЯЕМ!!!"
                            );
                            if (password != null) {
                                try {
                                    statisticService.saveStatistic(e1Window.getStatistic(), password);
                                    menuExecWindow.show(e1Window.getStatistic());
                                    e1Window.setEnabled(true);
                                    e1Window.dispose();
                                    return;
                                } catch (PasswordWrong passwordWrong) {
                                    e1Window.showError("Неверный пароль!");
                                } catch (DontSaveStatistic dontSaveStatistic) {
                                    e1Window.showError("Невозможно сохранить статистику. Сервер не доступен!");
                                    e1Window.dispose();
                                    menuExecWindow.show(e1Window.getStatistic());
                                    break;
                                }
                            } else {
                                menuExecWindow.show(e1Window.getStatistic());
                                e1Window.dispose();
                                break;
                            }
                        }
                    } else {
                        e1Window.showError("<html>Задание выполнено неверно!<br>Проверьте закодированное сообщение!");
                    }
                } catch (Exception e) {
                    e1Window.showError("Не заполнены все ячейки в таблице");
                }
                e1Window.setEnabled(true);
            }
        });
        e1Window.setOnCloseOperation(new EventWindow() {
            @Override
            public void action() {
                menuExecWindow.show(e1Window.getStatistic());
            }
        });
    }

    @Override
    public void addExe(DificultExe dificultExe) {
        alphabit = generateAlphabit.getAlphabit(dificultExe);
        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(alphabit.keySet());
        codeMessage = "";
        Random random = new Random();
        for(int i = 0; i < generateAlphabit.getCountBit(dificultExe); i++) {
            codeMessage += keys.get(random.nextInt(keys.size())) + " ";
        }

        processExe(codeMessage);

        String textExe = "<html>Дан алфавит:";
        for(int i = 0; i < keys.size(); i++) {
           if(i % 10 == 0) {
               textExe += "<br>";
           }
           textExe += keys.get(i) + " ";
        }
        textExe += "<br>необхоимо закодировать <br>сообщение<br>";
        textExe += codeMessage;
        textExe += "</html>";

        e1Window.setTextExe(textExe);

        Integer size = generateAlphabit.getCountBit(dificultExe);
        e1Window.setCodeTable(size + 1, size + 1);
        e1Window.setEnabledCodeButton(true);
    }

    /**
     * Решить задачу
     * @param codeMessage строка, которую необходимо закодировать
     */
    private void processExe(String codeMessage) {
        ArrayList<String> lettersCode = new ArrayList<>(Arrays.asList(codeMessage.split(" ")));
        ArrayList<String> resultLetters = new ArrayList<>();
        for(String r : lettersCode) {
            if(r.trim().length() != 0) {
                resultLetters.add(r);
            }
        }

        processExec = new ArrayList<>();
        ArrayList<Boolean> lastStr = new ArrayList<>();
        Boolean finalResult = false;

        for(int i = 0; i < resultLetters.size(); i++) {
            ArrayList<Boolean> letterCode = alphabit.get(resultLetters.get(i));
            ArrayList<Boolean> resultCode = new ArrayList<>();
            Boolean result = false;
            for(int j = 0; j < letterCode.size(); j++) {
                Boolean el = letterCode.get(j);
                resultCode.add(el);
                result ^= el;
                if(i == 0) {
                    lastStr.add(el);
                } else {
                    lastStr.set(j, lastStr.get(j)^el);
                }
            }
            resultCode.add(result);
            processExec.add(resultCode);
        }

        processExec.add(lastStr);

        for(Boolean element : lastStr) {
            finalResult ^= element;
        }
        for(int i = 0; i < resultLetters.size(); i++) {
            finalResult ^= processExec.get(i).get(processExec.get(i).size() - 1);
        }
        lastStr.add(finalResult);

//        //show table
//        for(ArrayList<Boolean> el : processExec) {
//            for(Boolean ev : el) {
//                System.out.print((ev) ? "1 " : "0 ");
//            }
//            System.out.println();
//        }
    }

    /**
     * Проверка выполнения задания по полученным из таблицы данным
     * @param data данные из таблицы ввода
     * @return статус проверки значения
     */
    private Boolean checkExecution(ArrayList<ArrayList<Boolean>> data) {
        try {
            for (int i = 0; i < processExec.size(); i++) {
                for (int j = 0; j < processExec.get(i).size(); j++) {
                    if (processExec.get(i).get(j) != data.get(i).get(j)) {
                        return false;
                    }
                }
            }
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
