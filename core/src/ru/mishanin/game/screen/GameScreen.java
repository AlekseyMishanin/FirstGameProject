package ru.mishanin.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.NonNull;
import ru.mishanin.game.MyFirstGame;
import ru.mishanin.game.base.Base2DScreen;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.pool.BulletPool;
import ru.mishanin.game.pool.EnemyPool;
import ru.mishanin.game.pool.ExplosionPool;
import ru.mishanin.game.sprite.Star;
import ru.mishanin.game.sprite.game.Explosion;
import ru.mishanin.game.sprite.game.MainShip;
import ru.mishanin.game.utils.EnemyEmitter;

/**
 * Класс основного экрана игры
 * @author Mishanin Aleksey
 * */
public class GameScreen extends Base2DScreen {

    private Texture fon;        //текстура фона

    private Music warMusic;            //фоновая музыка

    private TextureAtlas atlas;         //атлас текстур
    private Star[] star;                //массив звезд
    private MainShip mainShip;          //объект корабля игрока
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;

    /**
     * Конструктор
     * @param game - ссылка на объект типа MyFirstGame
     * */
    public GameScreen(@NonNull MyFirstGame game) {
        super(game);
        fon = new Texture("textures/gameFon.jpg");      //текстура фона

        warMusic = Gdx.audio.newMusic(Gdx.files.internal("music/war.mp3"));    //загружаем музыку фона
        warMusic.setLooping(true);      //бесконечное проигрывание музыки
        warMusic.play();    //включаем музыку


        game.getBackground().setRegion(new TextureRegion(fon));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        star = new Star[64];
        for (int i =0;i< star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool);
        enemyEmitter = new EnemyEmitter(atlas,enemyPool,getWorldBounds());
        mainShip = new MainShip(atlas, bulletPool);

    }

    @Override
    public void show() {
        super.show();

    }

    public void update(float delta){
        for (Star s: star) {
            s.update(delta);
        }
        bulletPool.updateActiveSprite(delta);
        explosionPool.updateActiveSprite(delta);
        enemyPool.updateActiveSprite(delta);
        mainShip.update(delta);
        enemyEmitter.generate(delta);
    }
    public void draw(){
        game.getBatch().begin();
        game.getBackground().draw(game.getBatch());
        for (Star s: star) {
            s.draw(game.getBatch());
        }
        bulletPool.drawActiveSprite(game.getBatch());
        explosionPool.drawActiveSprite(game.getBatch());
        enemyPool.drawActiveSprite(game.getBatch());
        mainShip.draw(game.getBatch());
        game.getBatch().end();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        deleteAllDestroyed();
        draw();
    }

    public void deleteAllDestroyed(){

        bulletPool.freeDestroyedActiveSprite();
        explosionPool.freeDestroyedActiveSprite();
        enemyPool.freeDestroyedActiveSprite();
    }

    @Override
    public void resize(RectBody worldBounds) {
        game.getBackground().resize(worldBounds);
        for (Star s: star) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        super.resize(worldBounds);
    }

    @Override
    public void dispose() {
        enemyPool.dispose();
        mainShip.dispose();
        warMusic.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        atlas.dispose();
        fon.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        mainShip.touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        mainShip.touchDown(touch, pointer);
        Explosion explosion = explosionPool.obtain();
        explosion.set(0.15f,touch);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        super.touchDragged(touch, pointer);
        mainShip.touchDragged(touch, pointer);
        return false;
    }
}
