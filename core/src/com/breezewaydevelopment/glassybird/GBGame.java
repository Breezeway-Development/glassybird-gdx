package com.breezewaydevelopment.glassybird;

import com.breezewaydevelopment.helpers.AssetLoader;
import com.breezewaydevelopment.screens.SplashScreen;
import com.badlogic.gdx.Game;

public class GBGame extends Game {

	//TODO: document lots of arbitrary math

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
