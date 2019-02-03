package ru.mironow.statistics;

import ru.mironow.exes.DificultExe;

import java.util.Map;

/**
 * Сущность статистики для хранения статистики по решенным задачам
 *
 * Created By alex on date 30.01.19 20:34
 */
public class Statistic {
    private String userName;
    private Integer numberLastExe;
    private Map<Integer, DificultExe> dataStatistic;

    /**
     * Получение имени пользователя
     * @return получение имени пользователя
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Установка имени пользователя
     * @param userName имя пользователя
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * Получение номера последней решенной задачи
     * @return номер последней решенной задачи
     */
    public Integer getNumberLastExe() {
        return numberLastExe;
    }

    /**
     * Установить номер последней решенной задачи
     * @param numberLastExe номер последней решенной задачи
     */
    public void setNumberLastExe(Integer numberLastExe) {
        this.numberLastExe = numberLastExe;
    }

    /**
     * Получение данных состатистикой
     * @return данные со статистикой
     */
    public Map<Integer, DificultExe> getDataStatistic() {
        return dataStatistic;
    }

    /**
     * Установить данные со статистикой
     * @param dataStatistic данные со статистикой
     */
    public void setDataStatistic(Map<Integer, DificultExe> dataStatistic) {
        this.dataStatistic = dataStatistic;
    }
}
