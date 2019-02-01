package ru.mishanin.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.utils.Regions;

@Getter
@Setter
public class Sprite extends RectBody {

    private float angle;          //угол поворота объекта
    private float scale = 1f;     //для маштабирования объекта
    private TextureRegion[] regions;  //массив регионов
    private int frame;            //номер текущего кадра
    private boolean isDestroyed;
    private RectBody worldBounds;

    public Sprite(@NonNull TextureRegion region) {
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(@NonNull TextureRegion region, int rows, int cols, int frames) {
        this.regions = Regions.split(region,rows,cols,frames);
    }

    public Sprite() {}

    public void setRegion(@NonNull TextureRegion region) {
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void draw(@NonNull SpriteBatch batch){
        batch.draw(
                regions[frame],                     //выбираем регион для прорисовки
                getLeft(), getBottom(),             //определяем левый нижний угол страйпа (смещаем)
                getHalfWidth(),getHalfHeight(),     //точка вращения
                getWidth(), getHeight(),            //определяем высоту и ширину объекта
                scale, scale,                       //масштаб по оси Х и У
                angle                               //угол вращения
        );
    }

    public void resize(RectBody worlBounds){
        this.worldBounds = worlBounds;
    }

    public void update(float delta){

    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = regions[frame].getRegionWidth()/(float)regions[frame].getRegionHeight();
        setWidth(height*aspect);
    }
}


