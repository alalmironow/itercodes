package ru.mironow.business.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Компонент утилит для преобразования данных
 */
@Component
public class ConvertUtils {

    /**
     * Преобразовать число в контейнер битов
     * @param convertNumber конвертируемое число
     * @return конвертируемое число
     */
    public ArrayList<Boolean> convertToBitArray(Integer convertNumber, Integer countBits) {
        ArrayList<Boolean> bitArray = new ArrayList<>();
        for(int i = 0; i < countBits; i++) {
            bitArray.add(false);
        }
        Integer result = convertNumber;
        int i = 0;
        while(result > 1) {
            bitArray.set(i++, result % 2 == 1);
            result /= 2;
        }
        bitArray.set(i, result == 1);
        Collections.reverse(bitArray);
        return bitArray;
    }
}
