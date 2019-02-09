package ru.mishanin.game.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import ru.mishanin.game.base.SpritePool;
import ru.mishanin.game.sprite.game.Enemy;
import ru.mishanin.game.sprite.game.MainShip;

public class EnemyPool extends SpritePool<Enemy> {

    private Sound shotSound;                                                    //звук выстрела
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, MainShip mainShip) {
        this.shotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.wav"));
        this.bulletPool=bulletPool;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shotSound, bulletPool, explosionPool, mainShip);
    }

    @Override
    public void dispose() {
        super.dispose();
        shotSound.dispose();
    }

    @Override
    public Enemy obtain() {
        return super.obtain();

    }
}
