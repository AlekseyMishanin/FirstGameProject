package ru.mishanin.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import ru.mishanin.game.MyFirstGame;
import ru.mishanin.game.base.Base2DScreen;

/**
 * Класс главного меню игры
 * @author Mishanin Aleksey
 * */
public class MenuScreen extends Base2DScreen {

    private Texture fon;        //текстура фона
    private Music spaceMusic;   //фоновая музыка

    /**
     * Конструктор
     * @param game - ссылка на объект типа MyFirstGame
     * */

    public MenuScreen(MyFirstGame game) { super(game);}

    @Override
    public void show() {
        super.show();

        fon = new Texture("menuFon.jpg");

        //загрузка фоновой музыки
        spaceMusic = Gdx.audio.newMusic(Gdx.files.internal("space.mp3"));
        //устанавливаем бесконечное повторение музыки
        spaceMusic.setLooping(true);
        //воспроизводим музыку
        spaceMusic.play();

        game.setBackgroundSprite(fon);  //загружаем текстуру для нового фона
        // настройка размеров и позиции спрайта заднего фона
        game.getBackgroundSprite().setSize(1f, 1f);
        game.getBackgroundSprite().setPosition(-0.5f,-0.5f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.getBatch().begin();
        game.getBackgroundSprite().draw(game.getBatch());     //прорисовываем фон
        game.getFont().draw(game.getBatch(), "Click to start game!!! ", -0f, 0f);  //выводим текст на экран
        game.getBatch().end();
        //если пользователь кликнул мышкой или ткнул пальцем - закрываем экран меню и загрузаем основной экран игры
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            this.dispose();
        }
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        fon.dispose();
        spaceMusic.dispose();
        super.dispose();
    }
}
