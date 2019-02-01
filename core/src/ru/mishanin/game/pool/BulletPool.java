package ru.mishanin.game.pool;

import ru.mishanin.game.base.SpritePool;
import ru.mishanin.game.sprite.game.Bullet;

public class BulletPool extends SpritePool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
