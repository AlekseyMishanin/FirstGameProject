package ru.mishanin.game.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.NonNull;
import ru.mishanin.game.base.Sprite;
import ru.mishanin.game.math.RectBody;

@Getter
public class Bullet extends Sprite {

    private RectBody worldBounds;           //мировые координаты
    private Vector2 speed = new Vector2();  //вектор скорости
    private int damage;                     //размер урона пули
    private Object owner;                   //владелец пули

    public Bullet() {
        //super(new TextureRegion());
    }

    public void set(
            @NonNull Object owner,           //владелец пули
            @NonNull TextureRegion region,   //регион
            @NonNull Vector2 pos,            //вектор позиции
            @NonNull Vector2 speed,          //вектор скорости
            @NonNull float height,           //размер пули
            @NonNull RectBody worldBounds,   //мировые координаты
            @NonNull int damage              //размер урона пули
    ){
        this.owner=owner;
        setRegion(region);
        getPos().set(pos);
        this.speed=speed;
        setHeightProportion(height);
        this.worldBounds=worldBounds;
        this.damage=damage;
    }

    @Override
    public void update(float delta) {
        getPos().mulAdd(speed,delta);
        if(isOutside(worldBounds)) {setDestroyed(true);}
        super.update(delta);
    }
}
