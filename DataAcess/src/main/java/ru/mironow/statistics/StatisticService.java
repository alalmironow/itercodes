package ru.mironow.statistics;

/**
 * Сервис для загрузки статистики
 *
 * Created By alex on date 30.01.19 20:39
 */
public interface StatisticService {

    /**
     * Загрузить или создать статистику по имени пользователя
     * @param userName имя пользователя
     * @throws NotLoadStatistic исключение если статистика не загружена
     * @return статистика
     */
    Statistic loadStatisticByUserName(String userName) throws NotLoadStatistic;
}
