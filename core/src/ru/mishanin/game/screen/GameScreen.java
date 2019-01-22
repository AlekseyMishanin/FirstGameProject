package ru.mishanin.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.mishanin.game.MyFirstGame;
import ru.mishanin.game.base.Base2DScreen;

/**
 * Класс основного экрана игры
 * @author Mishanin Aleksey
 * */
public class GameScreen extends Base2DScreen {

    private Vector2 pos;        //координаты левого нижнего угла игрового объекта
    private Vector2 speed;      //скорость перемещения игрового объекта
    private Vector2 buf;        //временный буфер
    private Texture img;        //текстура игрового объекта
    private Texture fon;        //текстура фона
    private Vector2 endpoint;         //координаты конечной точки
    private float step = 0.02f;            //шаг движения при нажатии клавишы направления
    private static float V_SPEED = 0.0005f;     //скорость
    private boolean bl1;                        //флаг для оптимизации работы метода render

    /**
     * Конструктор
     * @param game - ссылка на объект типа MyFirstGame
     * */
    public GameScreen(MyFirstGame game) {

        super(game);
        this.endpoint = new Vector2();
        img = new Texture("badlogic.jpg");
        fon = new Texture("gameFon.jpg");

        pos = new Vector2(-0.5f,-0.5f);
        speed = new Vector2(0.001f,0.001f);
        buf = new Vector2(0f,0f);

        bl1=false;
    }

    @Override
    public void show() {
        super.show();

        game.setBackgroundSprite(fon);                      //загружаем текстуру для нового фона
        game.getBackgroundSprite().setSize(1f, 1f);
        game.getBackgroundSprite().setPosition(-0.5f,-0.5f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.getBatch().begin();
        game.getBackgroundSprite().draw(game.getBatch());
        game.getBatch().draw(img, pos.x, pos.y, 0.5f,0.5f);
        game.getBatch().end();
        if(!bl1){       //проверка, чтобы избежать холостых расчетов и лишнего нагрузки на ЦП
            buf.set(endpoint);
            if(buf.sub(pos).len2()<(V_SPEED)){
                pos.set(endpoint);
                bl1=true;
            }
            else{
                pos.add(speed);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        System.out.println(width+" "+height);
        System.out.println(Gdx.graphics.getWidth()+" "+Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        img.dispose();
        fon.dispose();
        super.dispose();
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                //если координата х конечной точки равна -0.5 - завершаем обработку
                if(endpoint.x==-0.5) {break;}
                //перемещаем игровой объекта на шаг влево
                else if(endpoint.x<0) {endpoint.x-=((0.5f+endpoint.x)< step ? (0.5f+endpoint.x) : step);}
                else if(endpoint.x>=0) {endpoint.x-=step;}
                updatePosEndVector2();
                break;
            case Input.Keys.RIGHT:
                //если координата (х+ширина игрового объета) конечной точки равна ширине игрового экрана в мировых координатах - завершаем обработку
                if(endpoint.x==0.5f) break;
                //перемещаем игровой объекта на шаг вправо
                else if(endpoint.x<0) {endpoint.x+=((endpoint.x+step)<0.5f ? step:0.5f-endpoint.x);}
                else if(endpoint.x>=0) {endpoint.x=0.0f;}
                updatePosEndVector2();
                break;
            case Input.Keys.UP:
                //если координата (у+высота игрового объета) конечной точки равна высоте игрового экрана в мировых координатах - завершаем обработку
                if(endpoint.y==0.5f) break;
                //перемещаем игровой объекта на шаг вверх
                else if(endpoint.y<0) {endpoint.y+=((endpoint.y+step)<0.5f ? step:0.5f-endpoint.y);}
                else if(endpoint.y>=0) {endpoint.y=0.0f;}
                updatePosEndVector2();
                break;
            case Input.Keys.DOWN:
                //если координата у конечной точки равна -0.5 - завершаем обработку
                if(endpoint.y==-0.5) break;
                //перемещаем игровой объекта на шаг вниз
                else if(endpoint.y<0) {endpoint.y-=((0.5f+endpoint.y)< step ? (0.5f+endpoint.y) : step);}
                else if(endpoint.y>=0) {endpoint.y-=step;}
                updatePosEndVector2();
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        endpoint.set(screenX,screenBounds.getHeight()-screenY).mul(screenToWorlds);
        updatePosEndVector2();
        return false;
    }

    private void updatePosEndVector2(){
        speed.set(endpoint).sub(pos).setLength2(speed.len2()*V_SPEED);        //присваиваем новое значение скорости
        bl1=false;
    }
}
