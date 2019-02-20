package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.NonNull;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.pool.BulletPool;
import ru.mishanin.game.pool.ExplosionPool;
import ru.mishanin.game.utils.EnemyEmitter;

@Getter
public class Enemy extends Ship {

    private enum State{DESCENT, FIGHT}

    private Vector2 speed0 = new Vector2();
    private State state;
    private Vector2 descentV = new Vector2(0f, -0.2f);
    private EnemyEmitter.TypeEnemy typeEnemy;
    private MainShip mainShip;

    public Enemy(@NonNull Sound shotSound, @NonNull BulletPool bulletPool, @NonNull ExplosionPool explosionPool, @NonNull MainShip mainShip) {
        super();
        this.speed.set(speed0);
        this.shotSound=shotSound;
        this.bulletPool=bulletPool;
        this.bulletV = new Vector2();
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        getPos().mulAdd(this.speed,delta);
        switch (state){
            case DESCENT:
                if(getTop()<=getWorldBounds().getTop()){
                    this.speed.set(speed0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                this.reloadTimer += delta;
                if(reloadTimer>=reloadInterval){
                    reloadTimer=0f;
                    shoot();
                }
                //если верхняя граница объекта вышла за нижнюю границу мировых координат - помечаем объект на удаление
                if(getBottom()<getWorldBounds().getBottom()) {
                    mainShip.damage(this.damage);
                    setDestroyed(true);
                }
        }
    }

    public void set(@NonNull EnemyEmitter.TypeEnemy typeEnemy, int level, @NonNull RectBody worlBounds){
                setRegions(typeEnemy.getRegions());
                speed0.set(typeEnemy.getSpeed());
                this.bulletRegion=typeEnemy.getBulletRegion();
                this.bulletHeight=typeEnemy.getBulletHeight();
                this.bulletV.set(0,typeEnemy.getBulletVY());
                this.damage=typeEnemy.getDamage()*level;
                this.reloadInterval=typeEnemy.getReloadinterval();
                setHeightProportion(typeEnemy.getHeight());
                this.hp=typeEnemy.getHp();
                reloadTimer=reloadInterval;
                this.speed.set(descentV);
                setWorldBounds(worlBounds);
                state = State.DESCENT;
                this.typeEnemy = typeEnemy;
    }
    /*
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
        setRegions(regions);
        speed0.set(v0);
        this.bulletRegion=bulletRegion;
        this.bulletHeight=bulletHeight;
        this.bulletV.set(0,bulletSpeed);
        this.damage=bulletDamage;
        this.reloadInterval=reloadInterval;
        setHeightProportion(height);
        this.hp=hp;
        reloadTimer=reloadInterval;
        this.speed.set(descentV);
        setWorldBounds(worlBounds);
        state = State.DESCENT;
    }*/

    @Override
    public void setDestroyed(boolean isDestroyed) {
        super.setDestroyed(isDestroyed);
        if(isDestroyed){boom();}
    }

    public boolean isBulletCollision(@NonNull RectBody bullet){
        return !(bullet.getRight() < getLeft()||
                bullet.getLeft() > getRight()||
                bullet.getBottom() > getTop()||
                bullet.getTop() < getY());
    }
}
