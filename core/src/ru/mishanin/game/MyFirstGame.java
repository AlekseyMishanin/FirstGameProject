package ru.mishanin.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import ru.mishanin.game.screen.MenuScreen;
import ru.mishanin.game.sprite.Background;

/**
 * Класс представляет реализацию ApplicationListener и создает ряд объектов общих для всех экранов
 * @author Mishanin Aleksey
 * */
@Getter
public class MyFirstGame extends Game {

	private SpriteBatch batch;			//для отображения объектов на экранах
	private BitmapFont font;			//для отображения текста на экранах
	private Background background;      //фоновый рисуно

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();		// libGDX по умолчанию шрифт Arial
		background = new Background();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}
}
