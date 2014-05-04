package com.breezewaydevelopment.glassybird;

import com.badlogic.gdx.Game;
import com.breezewaydevelopment.helpers.AssetLoader;
import com.breezewaydevelopment.screens.SplashScreen;

public class GBGame extends Game {

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