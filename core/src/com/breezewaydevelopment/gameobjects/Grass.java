package com.breezewaydevelopment.gameobjects;

import com.breezewaydevelopment.helpers.Constants;

public class Grass extends Scrollable {

	public static int GRASS_HEIGHT = 9;

	public Grass(float x) {
		super(x, 0, (int) Constants.GAME_WIDTH, Grass.GRASS_HEIGHT);
	}

}
