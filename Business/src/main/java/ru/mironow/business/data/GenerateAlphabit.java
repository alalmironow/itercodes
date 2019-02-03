package ru.mironow.business.generatedata;

import org.springframework.stereotype.Component;
import ru.mironow.exes.DificultExe;

import java.util.*;

@Component
public class GenerateAlphabit {

    private final String data =
            "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
            "DFGIJLNQRSTUVWYZ" +
             "~!@#$%^&*(){}<>";
    private final Map<DificultExe, Integer> map;

    public GenerateAlphabit() {
        map = new HashMap<>();
        map.put(DificultExe.EASY, 3);
        map.put(DificultExe.MEDIUM, 5);
        map.put(DificultExe.HARD, 6);
    }

    /**
     * Получение алфавита по сложности задачи
     * @param dificultExe сложность задачи
     * @return алфавит
     */
    public List<String> getAlphabit(DificultExe dificultExe) {
        ArrayList<String> letters = new ArrayList<>(Arrays.asList(data.split("")));
        Collections.shuffle(letters);
        return letters.subList(0, (int)Math.pow(2, map.get(dificultExe)));
    }

    /**
     * Получить количество бит для генерации алфавита
     * @param dificultExe сложность
     * @return размер
     */
    public Integer getCountBit(DificultExe dificultExe) {
        return map.get(dificultExe);
    }
}
