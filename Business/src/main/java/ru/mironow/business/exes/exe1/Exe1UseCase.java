package ru.mironow.business.exes.exe1;

import ru.mironow.exes.DificultExe;

public interface Exe1UseCase {

    /**
     * Установка основной бизнес-логики
     */
    void setLogic();

    /**
     * Генерация задания
     */
    void addExe(DificultExe dificultExe);
}
