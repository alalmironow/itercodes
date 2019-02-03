package ru.mironow.statistics;

/**
 * Исключение, если не возможно загрузить статистику
 *
 * Created By alex on date 30.01.19 20:46
 */
public class NotLoadStatistic extends Exception {

    public NotLoadStatistic(String messgae) {
        super(messgae);
    }
}
