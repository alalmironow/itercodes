package ru.mironow.ui.login;

import ru.mironow.ui.events.EventWindow;

/**
 * Интерфейс окна для авторизации пользователя
 *
 * Created By Root to date 19.01.2019
 */
public interface LoginWindow {

    /**
     * Показать окно авторизаиции
     */
    void show();

    /**
     * Скрыть окно
     */
    void dispose();

    /**
     * Нажатие на кнопку войти
     */
    void clickByLoginButton(LoginHandler loginHandler);

    /**
     * Вывести ошибку
     * @param message сообщение ошибки
     */
    void showError(String message);

    /**
     * Показать ошибку с кликом
     * @param message сообщение об ошибке
     * @param eventWindow событие клика
     */
    void showErrorWithClick(String message, EventWindow eventWindow);

    /**
     * Установка статуса доступности
     * @param enable статус доступности
     */
    void setEnabled(boolean enable);
} 