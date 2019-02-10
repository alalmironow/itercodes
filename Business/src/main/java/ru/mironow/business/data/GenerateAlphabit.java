package ru.mironow.business.data;

import org.springframework.stereotype.Component;
import ru.mironow.exes.DificultExe;

import java.util.*;

@Component
public class GenerateAlphabit {

    private final ConvertUtils сonvertUtils;

    private final String data =
            "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
            "DFGIJLNQRSTUVWYZ" +
             "~!@#$%^&*(){}?>";

    private final Map<DificultExe, Integer> map;

    public GenerateAlphabit(ConvertUtils сonvertUtils) {
        this.сonvertUtils = сonvertUtils;

        map = new HashMap<>();
        map.put(DificultExe.EASY, 3);
        map.put(DificultExe.MEDIUM, 5);
        map.put(DificultExe.HARD, 6);
    }

    /**
     * Получение алфавита по сложности задачи
     * @param dificultExe сложность задачи
     * @return алфавит с закодированными в биты данными
     */
    public Map<String, ArrayList<Boolean>> getAlphabit(DificultExe dificultExe) {
        ArrayList<String> letters = new ArrayList<>(Arrays.asList(data.split("")));
        ArrayList<String> resultLetters = new ArrayList<>();

        for(String l : letters) {
            if(l.trim().length() != 0) {
                resultLetters.add(l);
            }
        }

        Map<String, ArrayList<Boolean>> mapAlphabit = new LinkedHashMap<>();
        Collections.shuffle(resultLetters);
        List<String> lettersSub = resultLetters.subList(0, (int)Math.pow(2, map.get(dificultExe)));

        for(int i = 0; i < lettersSub.size(); i++) {
            //System.out.println(lettersSub.get(i));
            //System.out.println(сonvertUtils.convertToBitArray(i, map.get(dificultExe)));
            mapAlphabit.put(lettersSub.get(i), сonvertUtils.convertToBitArray(i, map.get(dificultExe)));
        }

        return mapAlphabit;
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
