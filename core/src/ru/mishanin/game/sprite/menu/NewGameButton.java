package ru.mishanin.game.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.screen.GameScreen;

public class NewGameButton extends ScaledButton {

    private GameScreen gameScreen;

    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        setSize(0.25f, 0.08f);
        this.gameScreen = gameScreen;
    }

    @Override
    public void action() {
        gameScreen.active();
    }

    @Override
    public void resize(RectBody worlBounds) {
        super.resize(worlBounds);
        setPos(worlBounds.getX(),worlBounds.getY()-0.3f);
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
