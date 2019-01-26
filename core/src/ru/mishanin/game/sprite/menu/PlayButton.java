package ru.mishanin.game.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.screen.GameScreen;
import ru.mishanin.game.screen.MenuScreen;

/**
 * Класс кнопки Play
 * @author Mishanin Aleksey
 * */
public class PlayButton extends  ScaledButton {

    private MenuScreen menu; //ссылка на главное меню игры

    /**
     * Конструктор с параметрами
     * @param atlas атлас с текстурами
     * @param menu ссылка на гл.меню игры
     * */
    public PlayButton(TextureAtlas atlas, MenuScreen menu) {
        super(atlas.findRegion("btPlay"));
        setSize(0.5f,0.5f);
        this.menu=menu;
    }

    @Override
    public void action() {
        //создаем новый экран игры
        menu.getGame().setScreen(new GameScreen(menu.getGame()));
        //освобождаем ресурсы экрана гл.меню
        menu.dispose();
    }

    @Override
    public void resize(RectBody worlBounds) {
        super.resize(worlBounds);
        setPos(0f,0f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}
