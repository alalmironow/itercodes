package ru.mironow.ui.exes;

import ru.mironow.exes.DificultExe;
import ru.mironow.statistics.Statistic;
import ru.mironow.ui.events.EventWindow;

public interface ExeWindow {

    /**
     * Вывести на экран
     * @param statistic статистика
     */
    void show(Statistic statistic);

    /**
     * Скрыть окно
     */
    void dispose();

    /**
     * Установить событие при закрытии окна
     * @param eventWindow событие
     */
    void setOnCloseOperation(EventWindow eventWindow);

    /**
     * Получить статистику
     * @return статистика пользователя
     */
    Statistic getStatistic();

    /**
     * Получить номер выбранной сложности
     * @return номер выбранной сложности
     */
    Integer getNumberDifucult();

    /**
     * Получить сложность задания
     * @return сложность задания
     */
    DificultExe getDificult();
}
