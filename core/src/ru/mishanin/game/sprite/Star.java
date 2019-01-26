package ru.mishanin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.math.Rnd;

public class Star extends Sprite {

    private Vector2 speed = new Vector2();
    private RectBody worldBounds;
    private final float min = 0.005f;
    private final float max = 0.02f;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.randomF(min,max));

        speed.set(Rnd.randomF(-0.005f, 0.005f),
                Rnd.randomF((float)(-0.5f + 0.02*getHeight()), (float)(-0.2f + 0.01*getHeight())));
    }

    @Override
    public void update(float delta) {
        getPos().mulAdd(speed,delta);
        chackAndHandleBounds();
    }

    private void chackAndHandleBounds(){
        if(getRight()<worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if(getLeft()>worldBounds.getRight()) setRight(worldBounds.getLeft());
        if(getTop()<worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if(getBottom()>worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
    @Override
    public void resize(RectBody worlBounds) {
        super.resize(worlBounds);
        this.worldBounds=worlBounds;
        float posX = Rnd.randomF(worldBounds.getLeft(),worldBounds.getRight());
        float posY = Rnd.randomF(worldBounds.getBottom(),worldBounds.getTop());
        setPos(posX,posY);
    }
}
