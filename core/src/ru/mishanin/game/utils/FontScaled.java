package ru.mishanin.game.utils;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import lombok.Getter;
import lombok.Setter;
import ru.mishanin.game.math.Rnd;

@Getter
@Setter
public class FontScaled extends Font{

    private final float scaleUp = 0.00001f;
    private float animatedInterval = 4f;        //интервал проигрывания анимации
    private float animatedTimer = 0;            //таймер

    public FontScaled(String fontFile, String imageFile) {
        super(fontFile, imageFile);
    }

    public void setRandomColor() {
        float r = Rnd.randomF(0f,1f);
        float g = Rnd.randomF(0f,1f);
        float b = Rnd.randomF(0f,1f);
        super.setColor(r, g, b, 0.7f);
    }

    public void update(float delta) {
        animatedTimer+=delta;
    }

    public boolean isTimeForAnimate(){
        return animatedTimer<=animatedInterval;
    }

    public void defaultSettings(){
        setRandomColor();                               //получаем случайный цвет для текста
        getData().setScale(scaleUp/getCapHeight());     //возвращаем исходный параметны шрифта
        setAnimatedTimer(0f);                           //сбрасываем таймер
    }

    public GlyphLayout drawUpScale(Batch batch, CharSequence str, float x, float y, int halign) {
        getData().setScale(getScaleX() + scaleUp);
        return super.draw(batch, str, x, y, 0f,  halign, false);
    }
}
