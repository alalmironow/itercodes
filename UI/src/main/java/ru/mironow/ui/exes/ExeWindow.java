package ru.mironow.ui.exes;

import ru.mironow.statistics.Statistic;

public interface ExeWindow {

    /**
     * Вывести на экран
     * @param statistic статистика
     */
    void show(Statistic statistic);

    /**
     * Получить статистику
     * @return статистика пользователя
     */
    Statistic getStatistic();
}
