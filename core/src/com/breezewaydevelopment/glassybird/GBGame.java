package com.breezewaydevelopment.glassybird;

import com.breezewaydevelopment.screens.GameScreen;

import com.badlogic.gdx.Game;

public class GBGame extends Game {

	@Override
	public void create() {
		System.out.println("GBGame - create");
		setScreen(new GameScreen());
	}
}
