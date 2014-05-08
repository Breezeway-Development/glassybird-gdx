package com.breezewaydevelopment.glassybird;

import com.badlogic.gdx.Game;
import com.breezewaydevelopment.helpers.Util;
import com.breezewaydevelopment.screens.SplashScreen;

public class GBGame extends Game {
	
	//TODO: document lots of arbitrary math

	@Override
	public void create() {
		Util.init();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		Util.exit();
	}

}