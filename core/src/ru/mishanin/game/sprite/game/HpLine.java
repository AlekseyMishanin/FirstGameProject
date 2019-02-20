package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import ru.mishanin.game.screen.GameScreen;

/**
 * Класс инкапсулирует полоску HP главного героя.
 * @author Mishanin Aleksey
 * */
public class HpLine {

    private static final String HP = "%";

    private ShapeRenderer shapeRenderer = new ShapeRenderer();  //объект для отрисовки примитивных фигур(прямоугольник)
    private int hpBasic;                                        //базовое значение HP
    private int hpNow;                                          //текущее значение HP
    private float alpha;                                        //значимость 1 базового HP в приращении полоски HP
    private final float lineLen = 0.5f;                         //базовое значение длины полоски HP
    private StringBuilder strHp = new StringBuilder();          //текстовое значение HP
    private GameScreen gameScreen;                              //ссылка на объект GameScreen
    private Color startColor;                                   //цвет начала полоски HP
    private Color endColor;                                     //цвет конца полоски HP

    /**
     * Конструктор с параметрами.
     * @param gameScreen - ссылка на объект типа GameScreen
     * */
    public HpLine(GameScreen gameScreen) {
        //задаем матрицу проекции, которая будет использоваться для отрисовки примитивных фигур
        shapeRenderer.setProjectionMatrix(gameScreen.getWorldToGL());
        //устанавливаем флаг для автоматического сброса shapeType
        shapeRenderer.setAutoShapeType(true);
        hpBasic=hpNow=gameScreen.getMainShip().getHp();
        alpha = lineLen/(float) hpBasic;
        this.gameScreen = gameScreen;
        startColor = new Color().add(0.3f,0.0f,0.4f,0.5f);
        endColor = new Color().add(0.3f,0.0f,0.4f,0.9f);
    }

    public void draw(){

        strHp.setLength(0); //обнуляем строку
        hpNow = gameScreen.getMainShip().getHp();
        strHp.append(hpNow).append(HP);

        //отрисовываем текстовую строку
        gameScreen.getGame().getBatch().begin();
        gameScreen.getFont().draw(gameScreen.getGame().getBatch(), strHp, gameScreen.getWorldBounds().getX(), gameScreen.getWorldBounds().getTop()-0.02f, Align.center);
        gameScreen.getGame().getBatch().end();

        //настраиваем прозрачность объектов
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //рисуем полый прямоугольник (отрисовывается только рамка)
        shapeRenderer.rectLine(-0.25f,0.94f, -0.25f+lineLen, 0.94f, 0.07f, startColor, endColor);
        //рисуем прямоугольник с заливкой
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rectLine(-0.25f,0.94f,-0.25f+hpNow*alpha, 0.94f, 0.07f, startColor, endColor);
        shapeRenderer.end();
        //убираем прозрачность объектов
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
