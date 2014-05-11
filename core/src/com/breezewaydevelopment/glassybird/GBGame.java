package com.breezewaydevelopment.glassybird;

import com.badlogic.gdx.Game;
import com.breezewaydevelopment.helpers.Util;
import com.breezewaydevelopment.screens.InitScreen;

public class GBGame extends Game {

	@Override
	public void create() {
		setScreen(new InitScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		Util.exit();
	}

}