package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.base.Sprite;

public class Explosion extends Sprite {

    private float animateInterval = 0.017f;
    private float animateTimer;
    private Sound eplosionSound;

    public Explosion(TextureRegion region, int rows, int cols, int frames, Sound sound) {
        super(region, rows, cols, frames);
        eplosionSound=sound;
    }

    public void set(float height, Vector2 pos){
        getPos().set(pos);
        setHeightProportion(height);
        eplosionSound.play(0.5f);
    }

    @Override
    public void update(float delta) {
        animateTimer+=delta;
        if(animateTimer>=animateInterval){
            animateTimer=0f;
            setFrame(getFrame()+1);
            if(getFrame()==getRegions().length){
                setDestroyed(true);
            }
        }
    }

    @Override
    public void setDestroyed(boolean isDestroyed) {
        super.setDestroyed(isDestroyed);
        if(isDestroyed==true){setFrame(0);}
    }

    public void dispose(){
    }
}
