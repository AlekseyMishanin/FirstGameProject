package ru.mishanin.game.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;

/**
 * Базовый класс кнопки
 * */
public abstract class ScaledButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f; //константа для скалирования кнопки

    private int pointer;                            //номер пальца
    private boolean isPressed;                      //флаг нажатия

    public ScaledButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {

        if(isPressed||!isMe(touch)){
            return false;
        } else {
            this.pointer=pointer;
            setScale(PRESS_SCALE);
            isPressed=true;
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(this.pointer!=pointer||!isPressed){
            return false;
        }
        if (isMe(touch)){
            action();
        }
        this.isPressed=false;
        setScale(1f);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void resize(RectBody worlBounds) {
        super.resize(worlBounds);
    }

    abstract public void action();
}
