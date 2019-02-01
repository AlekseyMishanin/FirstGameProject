package ru.mishanin.game.math;

import com.badlogic.gdx.math.Vector2;
import lombok.NonNull;

/**
 * Класс прямоугольника, описывающего резмеры тела игрового объекта
 * @author Mishanin Aleksey
 * */
public class RectBody {

    private final Vector2 pos = new Vector2();
    private float halfHeight;
    private float halfWidth;

    public RectBody(){}

    public RectBody(@NonNull RectBody from){
        this(from.getX(), from.getY(), from.getHalfWidth(), from.getHalfHeight());
    }

    public RectBody(float x, float y, float halfWidth, float halfHeight){
        pos.set(x,y);
        this.halfWidth=halfWidth;
        this.halfHeight=halfHeight;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public void setPos(float x, float y){
        pos.set(x,y);
    }
    public Vector2 getPos() { return pos; }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getHeight() {
        return halfHeight*2f;
    }

    public void setHalfHeight(float halfHeight) {
        this.halfHeight = halfHeight;
    }

    public void setHeight(float height) {
        this.halfHeight = height/2f;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getWidth() {
        return halfWidth*2f;
    }

    public void setHalfWidth(float halfWidth) {
        this.halfWidth = halfWidth;
    }

    public void setWidth(float width) {
        this.halfWidth = width/2f;
    }

    public void setSize(float width, float height) {
        this.halfWidth = width/2f;
        this.halfHeight = height/2f;
    }

    public float getLeft(){return pos.x-halfWidth;}
    public float getRight(){return pos.x+halfWidth;}
    public float getTop(){return pos.y+halfHeight;}
    public float getBottom(){return pos.y-halfHeight;}

    public void set(RectBody from){
        pos.set(from.getX(),from.getY());
        this.halfWidth=from.halfWidth;
        this.halfHeight=from.halfHeight;
    }

    public float setLeft(float left){return pos.x = left + halfWidth;}
    public float setRight(float right){return pos.x = right - halfWidth;}
    public float setTop(float top){return pos.y = top - halfHeight;}
    public float setBottom(float bottom){return pos.y = bottom + halfHeight;}

    public boolean isMe(Vector2 touch){
        return touch.x>=getLeft() && touch.x<=getRight() && touch.y>=getBottom() && touch.y<=getTop();
    }

    public boolean isOutside(RectBody other){
        return getLeft()>other.getRight() || getRight()<other.getLeft() || getBottom()>other.getTop() || getTop()<other.getBottom();
    }

    /**
     * Метод проверяет нахождения границ прямоугольника внутри границ объекта other
     * @param other - обрамляющий объект
     * @return - true если объект находится в границах other, иначе - false
     * */
    public boolean isInternal(RectBody other){
        return getLeft()>other.getLeft() && getRight()<other.getRight() && getBottom()>other.getBottom() && getTop()<other.getTop();
    }

    @Override
    public String toString(){
        return "RectBody: pos " + pos + ", size(" + getWidth() + ", " + getHeight() + ")";
    }
}
