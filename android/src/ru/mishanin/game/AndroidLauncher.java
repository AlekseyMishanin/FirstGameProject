package ru.mishanin.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import ru.mishanin.game.MyFirstGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		/*Чтобы сохранить заряд батарении отключаем акселерометр и компас*/
		config.useAccelerometer = false; 	//отключаем акселерометр
		config.useCompass = false;			//отключаем компас
		initialize(new MyFirstGame(), config);
	}
}
