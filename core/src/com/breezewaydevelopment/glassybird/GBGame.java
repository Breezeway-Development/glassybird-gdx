package com.breezewaydevelopment.glassybird;

import com.breezewaydevelopment.helpers.AssetLoader;
import com.breezewaydevelopment.screens.GameScreen;

import com.badlogic.gdx.Game;

public class GBGame extends Game {

	@Override
	public void create() {
		System.out.println("GBGame - create");
		
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
