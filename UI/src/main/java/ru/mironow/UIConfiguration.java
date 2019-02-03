package ru.mironow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.mironow.ui.exes.E1Window;
import ru.mironow.ui.exes.E1WindowImpl;
import ru.mironow.ui.login.LoginWindow;
import ru.mironow.ui.login.LoginWindowImpl;
import ru.mironow.ui.menuexes.MenuExecWindow;
import ru.mironow.ui.menuexes.MenuExesWindowImpl;

/**
 * Конфигурация UI
 *
 * Created By Root to date 19.01.2019
 */
@Configuration
@PropertySource("classpath:application-ui.properties")
public class UIConfiguration {

    @Bean
    public LoginWindow loginWindow() {
        return new LoginWindowImpl();
    }

    @Bean
    public MenuExecWindow menuExecWindow() {
        return new MenuExesWindowImpl();
    }

    @Bean
    public E1Window e1Window() {
        return new E1WindowImpl();
    }
} 