package ru.mishanin.game.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;

/**
 * Класс GameOver
 * @author - Mishanin Aleksey
 * */
public class GameOver extends Sprite {

    private final float scaleConst = 0.05f; //константа для масштабирования текстуры по оси Х и У за равные промежутки времени
    private int direction = 1;              //направление масштабирования
    private int position = 0;               //шаг итерации маштабирования
    private float animatedInterval = 0.1f;  //интервал проигрывания анимации
    private float animatedTimer = 0;        //таймер

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setSize(0.8f,0.4f);
    }

    @Override
    public void resize(RectBody worlBounds) {
        super.resize(worlBounds);
        setPos(worlBounds.getX(),worlBounds.getY()+0.2f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        animatedTimer+=delta;
        if(animatedTimer>=animatedInterval){
            animated();                         //проигрываем анимацию
            animatedTimer = 0f;                 //сбрасываем таймер
        }
    }

    /**
     * Метод анимирует текстуру уменьшая за равные промежутки времени масштаб по оси х и у, а после заданного кол-ва
     * итераций (определяется локальной переменной end) восстанавливая масштраб по оси х и у.
     * */
    private void animated(){

        final int end = 5;
        /*Проверяем направление движения: если 1 -> уменьшаем масштаб, если -1 -> увеличиваем масштаб*/
        switch (direction){
            case 1:
                if(position < end){ //проверяем шаг итерации
                    ++position;     //изменяем значение шага
                    setScale(getScale() >= 0.5f ? getScale() - scaleConst : getScale()); //уменьшаем масштаб на scaleConst, но не менее 0.5f
                    break;
                }
                if (position == end) {direction = -1;}  //если шаг итерации достиг установленного лимита разворачиваем направление движения
                break;
            case -1:
                if (position > 0) {     //проверяем шаг итерации
                    --position;         //изменяем значение шага
                    setScale(getScale() <= 1f ? getScale() + scaleConst : getScale()); //увеличиваем масштаб на scaleConst, но не более 1.0f
                    break;
                }
                if (position == 0) {direction = 1;}  //если шаг итерации достиг установленного лимита разворачиваем направление движения
                break;
        }
    }
}
