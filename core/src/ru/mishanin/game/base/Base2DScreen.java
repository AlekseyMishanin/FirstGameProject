package ru.mishanin.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.MyFirstGame;
import ru.mishanin.game.math.MatrixUtils;
import ru.mishanin.game.math.RectBody;

/**
 * Базовый класс экрана
 * @author Mishanin Aleksey
 * */
public class Base2DScreen implements Screen, InputProcessor {

    protected RectBody screenBounds;  //границы области рисования в пикселях
    protected RectBody worldBounds;   //границы проекции мировых координат
    protected RectBody gldBounds;     //дефолтные границы gl

    protected Matrix4 worldToGL;
    protected Matrix3 screenToWorlds;

    private Vector2 touch;

    protected MyFirstGame game;   //ссылка на объект типа MyFirstGame

    /**
     * Конструктор по умолчанию для инициализации объектов
     * */
    public Base2DScreen() {
        screenBounds = new RectBody();
        worldBounds = new RectBody();
        gldBounds = new RectBody(0f,0f,1f,1f);
        worldToGL = new Matrix4();
        screenToWorlds = new Matrix3();
        touch = new Vector2();
    }

    public Base2DScreen(MyFirstGame game) {
        this();
        this.game = game;
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
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width/(float)height;

        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f*aspect);

        MatrixUtils.calcTransformMatrix(worldToGL, worldBounds, gldBounds);
        game.getBatch().setProjectionMatrix(worldToGL);
        MatrixUtils.calcTransformMatrix(screenToWorlds, screenBounds, worldBounds);
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
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,screenBounds.getHeight()-screenY).mul(screenToWorlds);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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


}
