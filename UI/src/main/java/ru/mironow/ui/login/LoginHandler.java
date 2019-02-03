package ru.mironow.ui.login;

/**
 * Обработчик нажатия на кнопку Логин
 */
public interface LoginHandler {

    /**
     * Клик по кнопке login
     * @param loginField значение из поля loginField
     */
    void click(String loginField);
}
