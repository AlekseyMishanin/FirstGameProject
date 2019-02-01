package ru.mishanin.game.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.mishanin.game.math.RectBody;

/**
 * Класс кнопки Exit
 * @author Mishanin Aleksey
 * */
public class ExitButton extends ScaledButton {

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setSize(0.1f,0.1f);
    }

    @Override
    public void action() {
        //некрасиво выходим из игры
        //System.exit(0);
        Gdx.app.exit();
    }

    @Override
    public void resize(RectBody worlBounds) {
        super.resize(worlBounds);
        setPos(worlBounds.getX()+0.35f,worlBounds.getY()+0.35f);
    }
}
