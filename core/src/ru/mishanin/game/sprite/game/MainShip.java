package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.pool.BulletPool;

public class MainShip extends Sprite {

    private static final Vector2 CONS_V = new Vector2(0.5f, 0f);          //константа скорости
    private Vector2 speed = new Vector2();                                      //буфер под скорость
    private boolean isPressedLeft;                                              //флаг нажатия клавиши левой стороны
    private boolean isPressedRight;                                             //флаг нажатия клавиши правой стороны
    private boolean isTouchLeft;                                                //флаг нажатия тача левой стороны
    private boolean isTouchRight;                                               //флаг нажатия тача правой стороны
    private boolean isTouchDragged;                                             //флаг нажатия тача и непрерывного движения из стороны в сторону
    private BulletPool bulletPool;                                              //пул пуль
    private TextureRegion bulletRegion;                                         //текстура пули
    private Sound shotSound;                                                    //звук выстрела

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletPool = bulletPool;
        setHeightProportion(0.15f);
        shotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.mp3"));
    }

    @Override
    public void resize(RectBody worlBounds) {
        super.resize(worlBounds);
        setBottom(worlBounds.getBottom()+0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if((!isPressedLeft&&!isTouchLeft&&(getX()+getHalfHeight())<getWorldBounds().getHalfWidth())||      //проверяем не выходит ли корабль за правую границу
                (!isPressedRight&&!isTouchRight&&(getX()-getHalfHeight())>(-1.0f)*getWorldBounds().getHalfWidth())||    //проверяем не выходит ли корабль за левую границу
                (isTouchDragged&&isInternal(getWorldBounds())) //проверяем не выходит ли корабль за границы во время события touchDragger
                    )
            getPos().mulAdd(speed,delta);
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft=true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight=true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft=false;
                if(isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight=false;
                if(isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        isTouchRight=isTouchLeft=isTouchDragged=false;
        stop();
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        if(touch.x>(getX()+getHalfHeight())){
            isTouchRight=true;
            moveRight();
        } else if(touch.x<(getX()-getHalfHeight())){
            isTouchLeft=true;
            moveLeft();
        }
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        isTouchDragged=true;
        if(touch.x>(getX()+getHalfHeight())){
            moveRight();
        } else if(touch.x<(getX()-getHalfHeight())){
            moveLeft();
        }
        return false;
    }

    private void moveRight(){

            speed.set(CONS_V);
    }

    private void moveLeft(){

            speed.set(CONS_V).rotate(180);
    }

    private void stop(){
        speed.setZero();
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,bulletRegion, getPos(), new Vector2(0f,0.5f), 0.01f,getWorldBounds(),1);
        shotSound.play();
    }

    public void dispose(){
    }

}
