package ru.mishanin.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.NonNull;

public class Regions {

    /**
     * Разбивает TextureRegion на фреймы
     * @param cols - кол-во столбцов
     * @param rows - кол-во строк
     * @param frames - кол-во фреймов
     * @param region - регион
     * @return массив регионов
     * */
    public static TextureRegion[] split(@NonNull TextureRegion region, int rows, int cols, int frames){
        TextureRegion[] regions = new TextureRegion[frames];
        int Width = region.getRegionWidth() / cols;
        int Height = region.getRegionHeight() / rows;
        int frame = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                regions[frame] = new TextureRegion(region, Width * j, Height * i, Width, Height);
                if(frame == frames - 1) return regions;
                frame++;
            }
        }
        return regions;
    }
}
