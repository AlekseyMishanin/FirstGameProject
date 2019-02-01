package ru.mishanin.game.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mishanin.game.base.SpritePool;
import ru.mishanin.game.sprite.game.Explosion;

public class ExplosionPool extends SpritePool<Explosion> {

    private TextureRegion region;
    private Sound eplosionSound;

    public ExplosionPool(TextureAtlas atlas){
        region = atlas.findRegion("explosion");
        eplosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/boom.wav"));
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(region,9,9,74,eplosionSound);
    }

    public void dispose(){
        eplosionSound.dispose();
        super.dispose();
    }
}
