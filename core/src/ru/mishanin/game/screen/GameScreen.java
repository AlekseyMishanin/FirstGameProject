package ru.mishanin.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import lombok.Getter;
import lombok.NonNull;
import ru.mishanin.game.MyFirstGame;
import ru.mishanin.game.base.Base2DScreen;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.pool.BulletPool;
import ru.mishanin.game.pool.EnemyPool;
import ru.mishanin.game.pool.ExplosionPool;
import ru.mishanin.game.sprite.Star;
import ru.mishanin.game.sprite.game.*;
import ru.mishanin.game.sprite.menu.GameOver;
import ru.mishanin.game.sprite.menu.NewGameButton;
import ru.mishanin.game.utils.EnemyEmitter;
import ru.mishanin.game.utils.Font;
import ru.mishanin.game.utils.FontScaled;

import java.util.List;

/**
 * Класс основного экрана игры
 * @author Mishanin Aleksey
 * */

@Getter
public class GameScreen extends Base2DScreen {

    @Getter
    private enum GameStatus {

        ACTIVE, DISABLE;

        private boolean flag;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }

    private static final String FRAGS = "Frags: ";
    private static final String LEVEL = "Level: ";

    private Texture fon;        //текстура фона

    private Music warMusic;            //фоновая музыка

    private TextureAtlas atlas;         //атлас текстур
    private Star[] star;                //массив звезд
    private MainShip mainShip;          //объект корабля игрока
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;
    private GameOver gameOver;
    private NewGameButton newGame;
    private GameStatus gameStatus = GameStatus.ACTIVE;
    private Font font;
    private FontScaled fontLevel;             //BitmapFont для отрисовки сообщения о начале нового уровня
    private StringBuilder strFrags = new StringBuilder();
    private StringBuilder strLevel = new StringBuilder();
    private HpLine hpLine;
    private Frags frags;

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
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyPool = new EnemyPool(bulletPool, explosionPool, mainShip);
        enemyEmitter = new EnemyEmitter(atlas,enemyPool,getWorldBounds());
        gameOver = new GameOver(atlas);
        newGame = new NewGameButton(atlas, this);
        this.font = new Font("font/font.fnt","font/font.png");
        this.font.setSize(0.02f);
        this.fontLevel = new FontScaled("font/font.fnt","font/font.png");
        hpLine = new HpLine(this);
        frags = new Frags(this, atlas, getWorldBounds());
    }

    @Override
    public void show() {
        super.show();

    }

    private void update(float delta){
        for (Star s: star) {
            s.update(delta);
        }
        fontLevel.update(delta);
        explosionPool.updateActiveSprite(delta);
        switch (gameStatus){
            case ACTIVE:
                //если корабль игрока уничтожен в пуле пуль/взрывов/врагов переводим все активные объекты в список неактивных
                if (mainShip.isDestroyed()) {
                    disable();
                }
                if (!mainShip.isDestroyed()) {
                    mainShip.update(delta);
                }
                bulletPool.updateActiveSprite(delta);
                enemyPool.updateActiveSprite(delta);
                enemyEmitter.generate(delta, frags.getAllFrags());
                break;
            case DISABLE:
                gameOver.update(delta);
                break;
        }
    }
    private void draw(){

        game.getBatch().begin();
        game.getBackground().draw(game.getBatch());
        for (Star s: star) {
            s.draw(game.getBatch());
        }
        explosionPool.drawActiveSprite(game.getBatch());
        switch (gameStatus){
            case ACTIVE:
                mainShip.draw(game.getBatch());
                bulletPool.drawActiveSprite(game.getBatch());
                enemyPool.drawActiveSprite(game.getBatch());
                break;
            case DISABLE:
                gameOver.draw(game.getBatch());
                newGame.draw(game.getBatch());
                break;
        }

        frags.draw(game.getBatch());
        printInfo();
        game.getBatch().end();

        hpLine.draw();
    }

    public void printInfo(){
        strLevel.setLength(0); //обнуляем строку
        font.draw(game.getBatch(), strLevel.append(LEVEL).append(enemyEmitter.getLevel()), getWorldBounds().getRight(), getWorldBounds().getTop(), Align.right);
        if(enemyEmitter.isLevelUp()){
            this.fontLevel.defaultSettings();
            enemyEmitter.setLevelUp(false);
        }
        if(this.fontLevel.isTimeForAnimate()) {
            fontLevel.drawUpScale(game.getBatch(),strLevel,getWorldBounds().getX(), getWorldBounds().getY(),Align.center);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void deleteAllDestroyed(){
        explosionPool.freeDestroyedActiveSprite();
        if(gameStatus==GameStatus.ACTIVE) {
            bulletPool.freeDestroyedActiveSprite();
            enemyPool.freeDestroyedActiveSprite();
        }
    }

    @Override
    public void resize(RectBody worldBounds) {
        game.getBackground().resize(worldBounds);
        for (Star s: star) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
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
        font.dispose();
        fontLevel.dispose();
        super.dispose();
    }

    private void checkCollisions(){
        if(gameStatus==GameStatus.ACTIVE) {
            List<Enemy> enemyList = enemyPool.getActivObjects();
            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed() || mainShip.isDestroyed()) {
                    continue;
                }      //если объект противника уничтожен - пропускаем
                float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth(); //минимальня дистацния между центрами объекта врага и объекта главного героя
                if (enemy.getPos().dst2(mainShip.getPos()) < minDist * minDist) {
                    enemy.setDestroyed(true);
                    mainShip.damage(enemy.getDamage());
                    return;
                }
            }
            List<Bullet> bulletList = bulletPool.getActivObjects();

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.setDestroyed(true);
                }
            }

            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }      //если объект противника уничтожен - пропускаем
                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.damage(mainShip.getDamage());
                        if(enemy.isDestroyed()){
                            frags.addFrag(enemy);
                        }
                        bullet.setDestroyed(true);
                    }
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        if(!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer);
        } else {
            newGame.touchUp(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        if(!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer);
        } else {
            newGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        super.touchDragged(touch, pointer);
        if(!mainShip.isDestroyed()) {
            mainShip.touchDragged(touch, pointer);
        }
        return false;
    }

    public void disable(){
        gameStatus = GameStatus.DISABLE;
        bulletPool.disable();
        //explosionPool.disable();
        enemyPool.disable();
    }

    public void active(){
        gameStatus = GameStatus.ACTIVE;
        frags.clear();
        enemyEmitter.setLevel(0);       //присвоен 0, чтобы при новой игре отрисовывалось сообщение с "Lelel: 1"
        mainShip.setDestroyed(false);
    }
}
