package ru.mishanin.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class SpritePool<T extends Sprite> {

    private List<T> activObjects = new ArrayList<T>();
    private List<T> freeObjecks = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain(){
        T obj;
        if(freeObjecks.isEmpty()) {
            obj = newObject();
        } else {
            obj = freeObjecks.remove(freeObjecks.size()-1);
        }
        activObjects.add(obj);
        return obj;
    }

    public void updateActiveSprite(float delta){
        for (T obj : activObjects) {
            obj.update(delta);
        }
    }

    public void drawActiveSprite(SpriteBatch batch){
        for (T obj : activObjects) {
            obj.draw(batch);
        }
    }

    public void freeDestroyedActiveSprite(){
        for (int i = 0; i < activObjects.size(); i++) {
            T obj = activObjects.get(i);
            if(obj.isDestroyed()){
                free(obj);
                i--;
            }
        }
    }

    public void free(T obj){
        activObjects.remove(obj);
        freeObjecks.add(obj);
        obj.setDestroyed(false);
    }

    public void dispose(){
        activObjects.clear();
        freeObjecks.clear();
    }
}
