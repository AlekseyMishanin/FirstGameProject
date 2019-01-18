package ru.mishanin.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.MyFirstGame;
import ru.mishanin.game.base.Base2DScreen;

/**
 * Класс основного экрана игры
 * @author Mishanin Aleksey
 * */
public class GameScreen extends Base2DScreen {

    private MyFirstGame game;   //ссылка на объект типа MyFirstGame
    private Vector2 pos;        //координаты левого нижнего угла игрового объекта
    private Vector2 speed;      //скорость перемещения игрового объекта
    private Texture img;        //текстура игрового объекта
    private Texture fon;        //текстура фона

    /**
     * Конструктор
     * @param game - ссылка на объект типа MyFirstGame
     * */
    public GameScreen(MyFirstGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        img = new Texture("badlogic.jpg");
        fon = new Texture("gameFon.jpg");

        pos = new Vector2(0f,0f);
        speed = new Vector2(0f,0f);

        rectangle.setSize(img.getWidth(),img.getHeight());  //сохраняем размеры игрового объекта

        game.setBackgroundSprite(fon);                      //загружаем текстуру для нового фона
        game.getBackgroundSprite().setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBackgroundSprite().setPosition(0f,0f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.getBatch().begin();
        game.getBackgroundSprite().draw(game.getBatch());
        game.getBatch().draw(img, pos.x, pos.y);
        game.getBatch().end();
        /* В родительском классе Base2DScreen реализована булевая переменная для подтверждения факта нажатия пальцем/мышкой
        по экрану или клавишы направления. Булевая переменная и нижесодержащаяся проверка введены, чтобы минимизировать
        кол-во холостых математических операций с векторами.
        * */
        if(isFlagNewTouch()){
            speed.set(endpoint);    //присваиваем вектору скорости координаты новой конечной точки
            speed.sub(pos);         //определяем новое значение скорости
            speed.nor();            //нормируем вектор скорости, чтобы избежать мгновенного перемещения игрового объекта в конечную точку
            setFlagNewTouch(false); //обновляем булевую переменнюу родительского класса
        }
        //если округленные координаты х/у игрового объекта равны х/у конечной точки, то пропускаем данный шаг
        if(Math.round(pos.x)==Math.round(endpoint.x)&&Math.round(pos.y)==Math.round(endpoint.y)) {}
        else{
            //иначе прибавляем к вектору положения игрового объекта вектор скорости
            pos.add(speed);
        }
    }

    @Override
    public void dispose() {
        img.dispose();
        fon.dispose();
        super.dispose();
    }
}
