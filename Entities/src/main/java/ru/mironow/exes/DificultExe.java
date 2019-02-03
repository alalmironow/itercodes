package ru.mironow.exes;

/**
 * Сложность задачи
 *
 * Created By alex on date 30.01.19 20:35
 */
public enum DificultExe {

    EASY,
    MEDIUM,
    HARD;

    public static DificultExe valueOfNumber(int i) {
       if(i == 0)
           return EASY;
        if(i == 1)
            return MEDIUM;
        return HARD;
    }
}
