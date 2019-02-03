package ru.mironow.ui.exes;

import ru.mironow.statistics.Statistic;
import ru.mironow.ui.events.EventWindow;

/**
 * Окно задачи 1
 *
 * Created By Root to date 20.01.2019
 */
public interface E1Window extends ExeWindow {

    /**
     * Установка статуса доступности
     * @param enable статус доступности
     */
    void setEnabled(boolean enable);

    /**
     * клик по кнопке генеарции задачи
     * @param eventWindow событие при клике на кнопку
     */
    void clickByGenerateValue(EventWindow eventWindow);

    /**
     * Получить номер выбранной сложности
     * @return номер выбранной сложности
     */
    Integer getNumberDifucult();

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
     * Статус доступности для кнопки кодирования
     * @param enable статус доступности
     */
    void setEnabledCodeButton(boolean enable);
} 