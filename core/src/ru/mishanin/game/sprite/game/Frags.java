package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.NonNull;
import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.screen.GameScreen;
import ru.mishanin.game.utils.Regions;

/**
 * Класс инкапсулирует статистику по убитым врагам. Подсчет ведется отдельно по разным типам врагов. Статистика
 * выводится в левом верхнем углу. Для правильной интерпретации статистических данных помимо цыфр выводятся текстуры
 * всех типов вражеских кораблей.
 * @author Mishanin Aleksey
 * */
public class Frags extends Sprite {

    private final float lenSide = 0.05f;                        //длина стороны квадрата для отрисовки текстур
    private int countBig;                                       //кол-во убитых врагов типа LARGE
    private int countMedium;                                    //кол-во убитых врагов типа MEDIUM
    private int countSmall;                                     //кол-во убитых врагов типа SMALL
    private StringBuilder strFrags = new StringBuilder();       //строка для вывода кол-ва убитых врагов на экран
    private GameScreen gameScreen;                              //ссылка на объект GameScreen

    public Frags(GameScreen gameScreen, TextureAtlas atlas, RectBody worldBounds) {

        this.gameScreen = gameScreen;
        //вырезаем из массива текстур по одной первой текстуре
        TextureRegion[] regions = new TextureRegion[3];
        regions[0] = Regions.split(atlas.findRegion("enemy0"),1,2,2)[0];
        regions[1] = Regions.split(atlas.findRegion("enemy1"),1,2,2)[0];
        regions[2] = Regions.split(atlas.findRegion("enemy2"),1,2,2)[0];
        setRegions(regions);
        //устанавлием размеры текстуры
        setWidth(lenSide);
        setHeight(lenSide);
        //устанавливаем положение левой нижней точки текстуры
        setPos(worldBounds.getLeft()+0.005f+getHalfWidth(),worldBounds.getTop()-0.005f-getHalfHeight());
    }

    @Override
    public void draw(@NonNull SpriteBatch batch) {
        for (int i = 0; i < getRegions().length; i++) {
            //отрисовываем текстуру вражеского корабля
            batch.draw(
                    getRegions()[i],                     //выбираем регион для прорисовки
                    gameScreen.getWorldBounds().getLeft()+0.005f + i*lenSide, gameScreen.getWorldBounds().getTop()-0.005f-getHeight(),             //определяем левый нижний угол страйпа (смещаем)
                    getHalfWidth(),getHalfHeight(),     //точка вращения
                    getWidth(), getHeight(),            //определяем высоту и ширину объекта
                    getScale(), getScale(),             //масштаб по оси Х и У
                    getAngle()                          //угол вращения
            );

            strFrags.setLength(0); //обнуляем строку

            switch (i){
                case 0:
                    strFrags.append(countSmall);
                    break;
                case 1:
                    strFrags.append(countMedium);
                    break;
                case 2:
                    strFrags.append(countBig);
                    break;
            }

            //печатаем кол-во убитых врагов
            gameScreen.getFont().draw(gameScreen.getGame().getBatch(),
                    strFrags,
                    gameScreen.getWorldBounds().getLeft()+0.005f + i*lenSide,
                    gameScreen.getWorldBounds().getTop()-0.005f-getHeight());
        }
    }

    /**
     * Метод для подсчета кол-ва убитых врагов
     * @param enemy - убитый враг
     * */
    public void addFrag(Enemy enemy){
        switch (enemy.getTypeEnemy()){
            case LARGE:
                ++countBig;
                break;
            case MEDIUM:
                ++countMedium;
                break;
            case SMALL:
                ++countSmall;
                break;
        }
    }

    /**
     * Метод для очистки переменных
     * */
    public void clear(){
        countBig=countMedium=countSmall=0;
    }

    /**
     * Метод для вывода общего кол-ва убитых врагов
     * */
    public int getAllFrags(){
        return countBig+countMedium+countSmall;
    }
}
