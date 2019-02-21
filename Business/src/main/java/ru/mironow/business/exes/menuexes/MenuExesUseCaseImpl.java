package ru.mironow.business.exes.menuexes;

import org.springframework.stereotype.Component;
import ru.mironow.ui.events.EventWindow;
import ru.mironow.ui.exes.E1Window;
import ru.mironow.ui.exes.E2Window;
import ru.mironow.ui.exes.ExeWindow;
import ru.mironow.ui.menuexes.MenuExecWindow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Реализация бизнес-логики
 *
 * Created By Root to date 20.01.2019
 */
@Component
public class MenuExesUseCaseImpl implements MenuExesUseCase {

    private final MenuExecWindow menuExecWindow;

    private final ArrayList<ExeWindow> exeWindowArrayList;

    private final E1Window e1Window;
    private final E2Window e2Window;

    public MenuExesUseCaseImpl(MenuExecWindow menuExecWindow, E1Window e1Window, E2Window e2Window) {
        this.menuExecWindow = menuExecWindow;
        this.e1Window = e1Window;
        this.e2Window = e2Window;

        exeWindowArrayList = new ArrayList<>();
    }


    @Override
    @PostConstruct
    public void setLogic() {

        exeWindowArrayList.add(e1Window);
        exeWindowArrayList.add(e2Window);

        for(int i = 0; i < exeWindowArrayList.size(); i++) {
            final int finalI = i;
            menuExecWindow.setClickOneExeButtonByNumber(i, new EventWindow() {
                @Override
                public void action() {
                    menuExecWindow.dispose();
                    exeWindowArrayList.get(finalI).show(menuExecWindow.getStatistic());
                }
            });
        }

    }
}