package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.pool.BulletPool;

public class Ship extends Sprite {

    protected BulletPool bulletPool;                                              //пул пуль
    protected Vector2 speed = new Vector2();                                      //буфер под скорость
    protected TextureRegion bulletRegion;                                         //текстура пули
    protected float reloadInterval;
    protected float reloadTimer;
    protected Sound shotSound;                                                    //звук выстрела
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    public Ship( ) {
        super();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
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
    public void dispose(){
        shotSound.dispose();
    }
}
