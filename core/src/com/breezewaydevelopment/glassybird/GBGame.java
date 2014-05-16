package com.breezewaydevelopment.glassybird;

import com.badlogic.gdx.Game;
import com.breezewaydevelopment.helpers.Assets;
import com.breezewaydevelopment.screens.SplashScreen;

public class GBGame extends Game {

	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
	}

}