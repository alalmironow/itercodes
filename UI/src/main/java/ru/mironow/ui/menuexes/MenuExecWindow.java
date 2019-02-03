package ru.mironow.ui.menuexes;

import ru.mironow.statistics.Statistic;
import ru.mironow.ui.events.EventWindow;

/**
 * Окно меню с задачами
 *
 * Created By Root to date 20.01.2019
 */
public interface MenuExecWindow {

    /**
     * Показать окно
     * @param statistic статистика
     */
    void show(Statistic statistic);

    /**
     * Закрыть окно
     */
    void dispose();

    /**
     * Вернуть объект статистика
     * @return статистика
     */
    Statistic getStatistic();

    /**
     * Установить действие на кнопку задачи по номеру кнопки
     * @param numberButton номер кнопки
     * @param eventWindow действие при событии
     */
    void setClickOneExeButtonByNumber(Integer numberButton, EventWindow eventWindow);
} 