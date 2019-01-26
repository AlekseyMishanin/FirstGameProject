package ru.mishanin.game.math;

import java.util.Random;

public class Rnd {

    private final static  Random random = new Random();

    /**
     * Метод генерирует случайной число типа float в заданном диапазоне
     * @param max - верхняя граница случайного числа
     * @param min - нижняя граница случайного числа
     * */
    public static float randomF(float max, float min){
        return random.nextFloat()*(max-min)+min;
    }
}
