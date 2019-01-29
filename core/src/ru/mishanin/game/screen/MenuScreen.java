package ru.mishanin.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.NonNull;
import ru.mishanin.game.MyFirstGame;
import ru.mishanin.game.base.Base2DScreen;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.sprite.Star;
import ru.mishanin.game.sprite.menu.ExitButton;
import ru.mishanin.game.sprite.menu.PlayButton;

/**
 * Класс главного меню игры
 * @author Mishanin Aleksey
 * */
public class MenuScreen extends Base2DScreen {

    private Texture bg;                 //текстура фона
    private Music spaceMusic;           //фоновая музыка
    private Star[] star;                //массив звезд
    private PlayButton playButton;      //кнопка play
    private ExitButton exitButton;      //кнопка exit
    private TextureAtlas atlas;         //атлас текстур

    /**
     * Конструктор
     * @param game - ссылка на объект типа MyFirstGame
     * */

    public MenuScreen(@NonNull MyFirstGame game) {
        super(game);
        bg = new Texture("textures/menuFon.jpg");
        game.getBackground().setRegion(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        star = new Star[256];
        for (int i =0;i< star.length; i++) {
            star[i] = new Star(atlas);
        }
        playButton = new PlayButton(atlas, this);
        exitButton = new ExitButton(atlas);
    }

    @Override
    public void show() {
        super.show();

        //загрузка фоновой музыки
        spaceMusic = Gdx.audio.newMusic(Gdx.files.internal("music/space.mp3"));
        //устанавливаем бесконечное повторение музыки
        spaceMusic.setLooping(true);
        //воспроизводим музыку
        spaceMusic.play();

    }

    public void update(float delta){
        for (Star s: star) {
            s.update(delta);
        }
    }
    public void draw(){
        game.getBatch().begin();
        game.getBackground().draw(game.getBatch());
        playButton.draw(game.getBatch());
        exitButton.draw(game.getBatch());
        for (Star s: star) {
            s.draw(game.getBatch());
        }
        game.getBatch().end();
    }
    @Override
    public void resize(RectBody worldBounds) {
        game.getBackground().resize(worldBounds);
        playButton.resize(worldBounds);
        exitButton.resize(worldBounds);
        for (Star s: star) {
            s.resize(worldBounds);
        }
        super.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        playButton.touchDown(touch, pointer);
        exitButton.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        playButton.touchUp(touch, pointer);
        exitButton.touchUp(touch, pointer);
        return false;
    }

    @Override
    public void dispose() {
        bg.dispose();
        spaceMusic.dispose();
        atlas.dispose();
        super.dispose();
    }
}
