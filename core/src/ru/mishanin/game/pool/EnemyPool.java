package ru.mishanin.game.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import ru.mishanin.game.base.SpritePool;
import ru.mishanin.game.sprite.game.Enemy;

public class EnemyPool extends SpritePool<Enemy> {

    private Sound shotSound;                                                    //звук выстрела
    private BulletPool bulletPool;

    public EnemyPool(BulletPool bulletPool) {
        this.shotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.wav"));
        this.bulletPool=bulletPool;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shotSound, bulletPool);
    }

    @Override
    public void dispose() {
        super.dispose();
        shotSound.dispose();
    }

    @Override
    public Enemy obtain() {
        System.out.println(activObjects.size() + "    " + freeObjecks.size());
        return super.obtain();

    }
}
