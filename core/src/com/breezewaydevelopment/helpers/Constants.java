package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.Gdx;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.Pipe;
import com.breezewaydevelopment.gameobjects.Scrollable;

public final class Constants {

	public static float SCREEN_WIDTH, SCREEN_HEIGHT, GAME_WIDTH, GAME_HEIGHT,
			MIDPOINT_Y, MIDPOINT_X;

	public static void calc() {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();

		GAME_WIDTH = 320 * 0.75f;
		GAME_HEIGHT = SCREEN_HEIGHT * (GAME_WIDTH / SCREEN_WIDTH);
		MIDPOINT_Y = GAME_HEIGHT / 2;
		MIDPOINT_X = GAME_WIDTH / 2;

		Bird.START_X = GAME_WIDTH * 0.33f;
		Bird.CIRC_OFFSET_X = (float) Bird.WIDTH / 2;
		Bird.CIRC_OFFSET_Y = (float) Bird.HEIGHT / 2;

		float pipeTravelTime = Pipe.GAP / Scrollable.SCROLL_SPEED;
		Pipe.HOLE_DELTA_HIGHER = (pipeTravelTime * Bird.ACCEL) - Pipe.HOLE;
		Pipe.HOLE_DELTA_LOWER = (pipeTravelTime * Bird.DECEL_MAX * 0.8f) - Pipe.HOLE;
	}

	private Constants() {
		throw new AssertionError(Constants.class);
	}

}
