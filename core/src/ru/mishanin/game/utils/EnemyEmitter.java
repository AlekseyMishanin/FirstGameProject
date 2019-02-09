package ru.mishanin.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import ru.mishanin.game.math.RectBody;
import ru.mishanin.game.math.Rnd;
import ru.mishanin.game.pool.EnemyPool;
import ru.mishanin.game.sprite.game.Enemy;

import java.util.Random;

public class EnemyEmitter {

    /**
     * Набор шаблонов для настройки объектов типа Enemy.
     * */
    @Getter
    private enum TypeEnemy{

        SMALL(0.1f,0.01f,-0.3f,1,3f,1, new Vector2(0,-0.2f)),
        MEDIUM(0.15f,0.015f,-0.3f,5,4f,10, new Vector2(0,-0.15f)),
        LARGE(0.2f,0.02f,-0.3f,10,5f,15, new Vector2(0,-0.1f));

        /**
         * Конструктор с параметрами
         * @param height - размер объекта
         * @param bulletHeight - размер пули
         * @param bulletVY - скорость пули
         * @param damage - значение урона
         * @param reloadinterval - интервал стрельбы
         * @param hp - кол-во жизней
         * @param speed - скорость объекта типа Enemy
         * */
        TypeEnemy(float height,float bulletHeight, float bulletVY, int damage, float reloadinterval, int hp, Vector2 speed){
            this.height=height;
            this.bulletHeight=bulletHeight;
            this.bulletVY=bulletVY;
            this.damage=damage;
            this.reloadinterval=reloadinterval;
            this.hp=hp;
            this.speed = speed;
        }
        private float height;
        private float bulletHeight;
        private float bulletVY;
        private int damage;
        private float reloadinterval;
        private int hp;
        private Vector2 speed;
        private TextureRegion[] regions;            //набор текстур объекта типа Enemy
        private static TextureRegion bulletRegion;  //текстура пули

        public void setRegions(TextureRegion[] regions) {
            this.regions = regions;
        }

        public static void setBulletRegion(TextureRegion bulletRegion) {
            TypeEnemy.bulletRegion = bulletRegion;
        }

        public static TextureRegion getBulletRegion() {
            return bulletRegion;
        }
    };

    private RectBody worldBounds;           //мировые координаты
    private Random random = new Random();   //переменная для генерирования случайного значения
    private float generateInterval = 4f;    //временной интервал между появлением объектов противника
    private float generateTimer;            //таймер для отсчета
    private EnemyPool enemyPool;            //пул объектов противников

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, RectBody worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion = atlas.findRegion("enemy0");
        TypeEnemy.SMALL.setRegions(Regions.split(textureRegion,1,2,2));     //получаем текстуры для объекта SMALL
        textureRegion = atlas.findRegion("enemy1");
        TypeEnemy.MEDIUM.setRegions(Regions.split(textureRegion,1,2,2));    //получаем текстуры для объекта MEDIUM
        textureRegion = atlas.findRegion("enemy2");
        TypeEnemy.LARGE.setRegions(Regions.split(textureRegion,1,2,2));     //получаем текстуры для объекта LARGE
        TypeEnemy.setBulletRegion(atlas.findRegion("bulletEnemy"));
        this.worldBounds=worldBounds;
    }

    public void generate(float delta){
        generateTimer+=delta;
        if(generateTimer>=generateInterval){
            generateTimer=0f;
            Enemy enemy = enemyPool.obtain();
            getRandomEnemy(enemy);              //рандомно настраиваем объект противника
            enemy.setPos(Rnd.randomF(worldBounds.getLeft()+enemy.getHalfWidth(), worldBounds.getRight()-enemy.getHalfWidth()),worldBounds.getTop());
        }
    }

    /**
     * Метод случайным образом генерирует настройки объекта противника. Чаще генерируются настройки для
     * SMALL объекта, реже для MEDIUM и LARGE объектов. Рандомно генерируем число в диапазоне от 0 до 13 включительно.
     * Если число в диапазоне от 0 до 7 настраиваем SMALL объект, если от 8 до 11 - MEDIUM, если от 12 до 13 -
     * LARGE.
     * @param enemy - объект противника, который нужно настроить
     * */
    private void getRandomEnemy(Enemy enemy){
        switch (random.nextInt(14)){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                enemy.set(TypeEnemy.SMALL.getRegions(),
                        TypeEnemy.SMALL.getSpeed(),
                        TypeEnemy.getBulletRegion(),
                        TypeEnemy.SMALL.getBulletHeight(),
                        TypeEnemy.SMALL.getBulletVY(),
                        TypeEnemy.SMALL.getDamage(),
                        TypeEnemy.SMALL.getReloadinterval(),
                        TypeEnemy.SMALL.getHeight(),
                        TypeEnemy.SMALL.getHp(),
                        worldBounds
                );
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                enemy.set(TypeEnemy.MEDIUM.getRegions(),
                        TypeEnemy.MEDIUM.getSpeed(),
                        TypeEnemy.getBulletRegion(),
                        TypeEnemy.MEDIUM.getBulletHeight(),
                        TypeEnemy.MEDIUM.getBulletVY(),
                        TypeEnemy.MEDIUM.getDamage(),
                        TypeEnemy.MEDIUM.getReloadinterval(),
                        TypeEnemy.MEDIUM.getHeight(),
                        TypeEnemy.MEDIUM.getHp(),
                        worldBounds
                );
                break;
            case 12:
            case 13:
                enemy.set(TypeEnemy.LARGE.getRegions(),
                        TypeEnemy.LARGE.getSpeed(),
                        TypeEnemy.getBulletRegion(),
                        TypeEnemy.LARGE.getBulletHeight(),
                        TypeEnemy.LARGE.getBulletVY(),
                        TypeEnemy.LARGE.getDamage(),
                        TypeEnemy.LARGE.getReloadinterval(),
                        TypeEnemy.LARGE.getHeight(),
                        TypeEnemy.LARGE.getHp(),
                        worldBounds
                );
                break;
            default: throw new NullPointerException("Problem in getRandomEnemy()");
        }
    }
}
