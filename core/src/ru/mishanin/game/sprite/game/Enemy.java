package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.pool.BulletPool;

public class Enemy extends Ship {

    private Vector2 speed0 = new Vector2();

    public Enemy(Sound shotSound, BulletPool bulletPool) {
        super();
        this.speed.set(speed0);
        this.shotSound=shotSound;
        this.bulletPool=bulletPool;
        this.bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        getPos().mulAdd(this.speed,delta);
        this.reloadTimer += delta;
        if(reloadTimer>=reloadInterval){
            reloadTimer=0f;
            shoot();
        }
        if(getTop()<getWorldBounds().getBottom()) {setDestroyed(true);}  //если верхняя граница объекта вышла за нижнюю границу мировых координат - помечаем объект на удаление
        super.update(delta);
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletSpeed,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp,
            RectBody worlBounds
    ){
        this.regions = regions;
        speed0.set(v0);
        this.bulletRegion=bulletRegion;
        this.bulletHeight=bulletHeight;
        this.bulletV.set(0,bulletSpeed);
        this.damage=bulletDamage;
        this.reloadInterval=reloadInterval;
        setHeightProportion(height);
        this.hp=hp;
        reloadTimer=reloadInterval;
        this.speed.set(v0);
        setWorldBounds(worlBounds);
    }
}
