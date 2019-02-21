package ru.mironow.ui.exes;

import ru.mironow.ui.components.TableFromFields;
import ru.mironow.ui.events.EventWindow;

/**
 * Окно задачи 2
 *
 * Created By Root to date 21.02.2019
 */
public interface E2Window extends ExeWindow {

    /**
     * Установка статуса доступности
     * @param enable статус доступности
     */
    void setEnabled(boolean enable);


    /**
     * Статус доступности для кнопки кодирования
     * @param enable статус доступности
     */
    void setEnabledCodeButton(boolean enable);

    /**
     * клик по кнопке генеарции задачи
     * @param eventWindow событие при клике на кнопку
     */
    void clickByGenerateValue(EventWindow eventWindow);

    /**
     * клик по кнопке исполнения
     * @param eventWindow события в окне
     */
    void clickByExecuteButton(EventWindow eventWindow);

    /**
     * установить текст задачи
     * @param textExe текст задачи
     */
    void setTextExe(String textExe);

    /**
     * Установить таблицу для кодировки
     * @param rows строки
     * @param cols колонки
     */
    void setCodeTable(int rows, int cols);

    /**
     * Вернуть матрицу с данными
     * @return матрица с заполненными пользователем данными
     */
    TableFromFields getTable();

    /**
     * вывести ошибку на экран
     * @param message сообщение об ошибке
     */
    void showError(String message);

    /**
     * Вывод сообщения с полем для ввода
     * @param message сообщение
     * @param title заголовок
     * @return строка введенная в поле ввода
     */
    String showInputDialog(String message, String title);
}