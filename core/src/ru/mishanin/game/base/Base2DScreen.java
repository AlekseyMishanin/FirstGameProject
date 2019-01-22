package ru.mishanin.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Базовый класс экрана
 * @author Mishanin Aleksey
 * */
public class Base2DScreen implements Screen, InputProcessor {

    protected Vector2 endpoint;         //координаты конечной точки
    protected Rectangle rectangle;      //размер игрового объекта
    private boolean flagNewTouch;       //флаг подтверждающий наступление события движения игрового объекта
    protected int step = 10;            //шаг движения при нажатии клавишы направления

    /**
     * Конструктор по умолчанию для инициализации объектов
     * */
    public Base2DScreen() {
        this.endpoint = new Vector2();
        this.flagNewTouch = false;
        this.rectangle = new Rectangle();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);  //сообщаем фреймворку о том, что мы сами будем обрабатывать события
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                //если координата х конечной точки равна 0 - завершаем обработку
                if(endpoint.x==0) break;
                //перемещаем игровой объект на шаг влево
                endpoint.x-=(endpoint.x < step ? endpoint.x : step);
                //сообщаем о том, что произошло событие движения игрового объекта
                this.flagNewTouch = true;
                break;
            case Input.Keys.RIGHT:
                //если координата (х+ширина игрового объета) конечной точки равна ширине игрового экрана - завершаем обработку
                if((endpoint.x+rectangle.getWidth())==Gdx.graphics.getWidth()) break;
                //перемещаем игровой объекта на шаг вправо
                endpoint.x+=((endpoint.x+rectangle.getWidth()+step) > Gdx.graphics.getWidth() ? Gdx.graphics.getWidth()-endpoint.x-rectangle.getWidth() : step);
                //сообщаем о том, что произошло событие движения игрового объекта
                this.flagNewTouch = true;
                break;
            case Input.Keys.UP:
                //если координата (у+высота игрового объета) конечной точки равна высоте игрового экрана - завершаем обработку
                if((endpoint.y+rectangle.getHeight())==Gdx.graphics.getHeight()) break;
                //перемещаем игровой объекта на шаг вверх
                endpoint.y+=((endpoint.y+rectangle.getHeight()+step) > Gdx.graphics.getHeight() ? Gdx.graphics.getHeight()-endpoint.y-rectangle.getHeight() : step);
                //сообщаем о том, что произошло событие движения игрового объекта
                this.flagNewTouch = true;
                break;
            case Input.Keys.DOWN:
                //если координата у конечной точки равна 0 - завершаем обработку
                if(endpoint.y==0) break;
                //перемещаем игровой объекта на шаг вниз
                endpoint.y-=(endpoint.y < step ? endpoint.y : step);
                //сообщаем о том, что произошло событие движения игрового объекта
                this.flagNewTouch = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        endpoint.x=screenX;                             //присваиваем конеыной точке новое значение х
        endpoint.y=Gdx.graphics.getHeight()-screenY;    //присваиваем конеыной точке новое значение у
        flagNewTouch = true;                            //сообщаем о том, что произошло событие движения игрового объекта
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isFlagNewTouch() {
        return flagNewTouch;
    }

    public void setFlagNewTouch(boolean flagNewTouch) {
        this.flagNewTouch = flagNewTouch;
    }
}
