package ru.mironow.business.login;

import org.springframework.stereotype.Component;
import ru.mironow.business.exes.menuexes.MenuExesUseCase;
import ru.mironow.exes.DificultExe;
import ru.mironow.statistics.NotLoadStatistic;
import ru.mironow.statistics.Statistic;
import ru.mironow.statistics.StatisticService;
import ru.mironow.ui.events.EventWindow;
import ru.mironow.ui.exes.E1Window;
import ru.mironow.ui.login.LoginHandler;
import ru.mironow.ui.login.LoginWindow;
import ru.mironow.ui.menuexes.MenuExecWindow;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created By Root to date 19.01.2019
 */
@Component
public class LoginUseCaseImpl implements LoginUseCase {

    private final MenuExesUseCase menuExesUseCase;

    private final LoginWindow loginWindow;
    private final MenuExecWindow menuExecWindow;
    private final E1Window e1Window;

    private final StatisticService statisticService;


    public LoginUseCaseImpl(MenuExesUseCase menuExesUseCase, LoginWindow loginWindow, MenuExecWindow menuExecWindow, E1Window e1Window, StatisticService statisticService) {
        this.menuExesUseCase = menuExesUseCase;
        this.loginWindow = loginWindow;
        this.menuExecWindow = menuExecWindow;
        this.e1Window = e1Window;
        this.statisticService = statisticService;
    }

    @Override
    public void start() {
        loginWindow.show();
    }

    @PostConstruct
    private void setLogic() {
        /**
         * Клик по кнопке 'Войти'
         */
        loginWindow.clickByLoginButton(new LoginHandler() {
            @Override
            public void click(String loginField) {
                login(loginField);
            }
        });
    }

    /**
     * Авторизация в системе
     * @param loginField имя пользователя
     */
    private void login(String loginField) {
        if(loginField == null || loginField.trim().length() == 0) {
            loginWindow.showError("Введите ФИО пользователя");
            return;
        }
        loginField = loginField.trim();
        if(loginField.length() > 100) {
            loginWindow.showError("Размер поля ФИО не должен превышать 100 символов");
            return;
        }
        loginWindow.setEnabled(false);
        Statistic statistic = null;
        try {
            statistic = statisticService.loadStatisticByUserName(loginField);
        } catch (NotLoadStatistic notLoadStatistic) {
            loginWindow.showErrorWithClick(notLoadStatistic.getMessage(), new EventWindow() {
                @Override
                public void action() {
                    loginWindow.dispose();
                }
            });
            statistic = new Statistic();
            statistic.setUserName(loginField);
            statistic.setNumberLastExe(0);
            statistic.setDataStatistic(new HashMap<Integer, DificultExe>());
        }
        loginWindow.dispose();
        menuExecWindow.show(statistic);
    }
}