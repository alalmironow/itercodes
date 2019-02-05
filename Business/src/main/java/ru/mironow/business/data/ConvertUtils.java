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
    public ArrayList<Boolean> convertToBitArray(Integer convertNumber) {
        ArrayList<Boolean> bitArray = new ArrayList<>();
        Integer result = convertNumber;

        while(result > 1) {
            bitArray.add(result % 2 == 1);
            result /= 2;
        }
        Collections.reverse(bitArray);

        return bitArray;
    }
}
