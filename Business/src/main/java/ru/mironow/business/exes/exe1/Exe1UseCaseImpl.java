package ru.mironow.business.exe1;

import org.springframework.stereotype.Component;
import ru.mironow.business.generatedata.GenerateAlphabit;
import ru.mironow.exes.DificultExe;
import ru.mironow.ui.events.EventWindow;
import ru.mironow.ui.exes.E1Window;
import ru.mironow.ui.menuexes.MenuExecWindow;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Component
public class Exe1UseCaseImpl implements Exe1UseCase {

    private final E1Window e1Window;
    private final MenuExecWindow menuExecWindow;
    private final GenerateAlphabit generateAlphabit;

    private List<String> alphabit;
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
                alphabit = generateAlphabit.getAlphabit(dificultExe);
                addExe(dificultExe);
                e1Window.setEnabled(true);
            }
        });
    }

    @Override
    public void addExe(DificultExe dificultExe) {
        alphabit = generateAlphabit.getAlphabit(dificultExe);
        Random random = new Random();
        codeMessage = "";
        for(int i = 0; i < generateAlphabit.getCountBit(dificultExe); i++) {
            codeMessage += alphabit.get(random.nextInt(alphabit.size()));
        }
       String textExe = "<html>Дан алфавит:";
       for(int i = 0; i < alphabit.size(); i++) {
           if(i % 10 == 0) {
               textExe += "<br>";
           }
           textExe += alphabit.get(i) + " ";
       }
       textExe += "<br>необхоимо закодировать <br>сообщение<br>";
       textExe += codeMessage;
        textExe += "</html>";

       e1Window.setTextExe(textExe);

       Integer size = generateAlphabit.getCountBit(dificultExe);

       e1Window.setCodeTable(size + 1, size + 1);
       e1Window.setEnabledCodeButton(true);
    }
}
