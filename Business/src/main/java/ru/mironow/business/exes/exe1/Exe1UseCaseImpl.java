package ru.mironow.business.exes.exe1;

import org.springframework.stereotype.Component;
import ru.mironow.business.data.GenerateAlphabit;
import ru.mironow.exes.DificultExe;
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

    private HashMap<String, ArrayList<Boolean>> alphabit;
    private ArrayList<ArrayList<Boolean>> processExec;
    private String codeMessage;

    public Exe1UseCaseImpl(E1Window e1Window, MenuExecWindow menuExecWindow, GenerateAlphabit generateAlphabit) {
        this.e1Window = e1Window;
        this.menuExecWindow = menuExecWindow;
        this.generateAlphabit = generateAlphabit;
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
        System.out.println(size);
        e1Window.setCodeTable(size + 1, size + 1);
        e1Window.setEnabledCodeButton(true);
    }

    /**
     * Решить задачу
     * @param codeMessage строка, которую необходимо закодировать
     */
    private void processExe(String codeMessage) {
        ArrayList<String> lettersCode = new ArrayList<>(Arrays.asList(codeMessage.split(" ")));
        ArrayList<String> resultLettersCode = new ArrayList<>();

        for(String r : lettersCode) {
            if(r.trim().length() != 0) {
                resultLettersCode.add(r);
            }
        }

        ArrayList<Boolean> lastStr = new ArrayList<>();

        processExec = new ArrayList<>();
        Boolean result;
        for(String l : resultLettersCode) {
            ArrayList<Boolean> str = new ArrayList<>();
            result = false;
            for(Boolean b : alphabit.get(l)) {
                str.add(b);
                result ^= b;
            }
            str.add(result);
        }

    }
}
