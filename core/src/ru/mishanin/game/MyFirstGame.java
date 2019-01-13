package ru.mishanin.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyFirstGame extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture img;
	private Texture fon;
	private Music spaceMusic;
	private Sprite backgroundSprite; // спрайт для заднего фона
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		fon = new Texture("fon.jpg");

		//загрузка фоновой музыки
		spaceMusic = Gdx.audio.newMusic(Gdx.files.internal("space.mp3"));
		//устанавливаем бесконечное повторение музыки
		spaceMusic.setLooping(true);
		//воспроизводим музыку
		spaceMusic.play();

		// установка текстуры в спрайт
		backgroundSprite = new Sprite(fon);
		// настройка размеров и позиции спрайта заднего фона
		backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		backgroundSprite.setPosition(0f,0f);
	}

	@Override
	public void render () {
		batch.begin();
		backgroundSprite.draw(batch);
		//batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		fon.dispose();
		spaceMusic.dispose();
	}
}
