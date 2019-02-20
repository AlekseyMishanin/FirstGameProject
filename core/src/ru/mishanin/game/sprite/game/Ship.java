package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.pool.BulletPool;
import ru.mishanin.game.pool.ExplosionPool;

@Getter
public class Ship extends Sprite {

    protected BulletPool bulletPool;                                              //пул пуль
    protected Vector2 speed = new Vector2();                                      //буфер под скорость
    protected TextureRegion bulletRegion;                                         //текстура пули
    protected float reloadInterval;
    protected float reloadTimer;

    private float damageInterval = 0.1f;
    private float damageTimer = damageInterval;

    protected Sound shotSound;                                                    //звук выстрела
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected int hp;
    protected ExplosionPool explosionPool;

    public Ship( ) {
        super();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageTimer+=delta;
        if(damageTimer>=damageInterval){
            setFrame(0);
        }
    }

    public void damage(int damage){
        setFrame(1);
        damageTimer = 0f;
        hp -= damage;
        if(hp<=0 && !isDestroyed()){
            hp=0;
            setDestroyed(true);
            setFrame(0);
        }
    }

    @Override
    public void resize(RectBody worlBounds) {
        super.resize(worlBounds);
    }

    public void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,bulletRegion, getPos(), bulletV, bulletHeight,getWorldBounds(),damage);
        shotSound.play();
    }

    public void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(),getPos());
    }

    public void dispose(){
        shotSound.dispose();
    }
}
