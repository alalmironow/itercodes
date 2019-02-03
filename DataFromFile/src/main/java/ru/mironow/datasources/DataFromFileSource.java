package ru.mironow.datasources;

import ru.mironow.data.DataFile;

import java.io.IOException;

/**
 * Интерфейс компонента доступа к данным
 *
 * Created By Root to date 19.01.2019
 */
public interface DataFromFileSource {

    /**
     * Получение данных
     * @return данные
     */
    DataFile getData();

    /**
     * Сохранение данных
     * @param dataFile данные
     */
    void saveData(DataFile dataFile) throws IOException;
} 