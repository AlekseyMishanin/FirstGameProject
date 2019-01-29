package ru.mishanin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;

public class Background extends Sprite {

    public Background( TextureRegion region) {
        super(region);
    }

    public Background() {}

    @Override
    public void resize(RectBody worldBounds) {
        super.resize(worldBounds);
        set(worldBounds);
        setHeightProportion(worldBounds.getHeight());
    }
}
