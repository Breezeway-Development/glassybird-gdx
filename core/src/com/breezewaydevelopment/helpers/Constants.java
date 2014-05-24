package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.Gdx;

public final class Constants {

	/*
	 * Most of this class is temporary. Once the dust clears we can start adding
	 * variables back into classes.
	 */

	public static float SCREEN_WIDTH, SCREEN_HEIGHT, GAME_WIDTH, GAME_HEIGHT,
			MIDPOINT_Y, MIDPOINT_X, SCALE_FACTOR;

	public static class Bird {
		public static float START_X, ACCEL, DECEL;
		public static int WIDTH, HEIGHT;
	}

	public static class Scrollables {

		public static int PIPE_GAP;
		public static int PIPE_HOLE;
		public static int PIPE_CLEARANCE_TOP;
		public static int PIPE_CLEARANCE_BOTTOM;
		public static int PIPE_WIDTH;
		public static int SKULL_WIDTH;
		public static int SKULL_HEIGHT;
		public static int GRASS_HEIGHT;

		public static float PIPE_HOLE_DELTA_HIGHER, PIPE_HOLE_DELTA_LOWER,
				SCROLL_SPEED;
	}

	public static void calc() {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		GAME_WIDTH = 320 * 0.75f;
		SCALE_FACTOR = GAME_WIDTH / SCREEN_WIDTH;

		GAME_HEIGHT = SCREEN_HEIGHT * SCALE_FACTOR;
		MIDPOINT_Y = GAME_HEIGHT / 2;
		MIDPOINT_X = GAME_WIDTH / 2;

		Scrollables.SCROLL_SPEED = 57;
		Scrollables.GRASS_HEIGHT = 11;
		Scrollables.PIPE_GAP = 55;
		Scrollables.PIPE_HOLE = 45;
		Scrollables.PIPE_WIDTH = 25;
		Scrollables.PIPE_CLEARANCE_TOP = Scrollables.PIPE_HOLE + 5;
		Scrollables.PIPE_CLEARANCE_BOTTOM = 13;
		Scrollables.SKULL_WIDTH = 27;
		Scrollables.SKULL_HEIGHT = 20;
		// TOOD: Resize pipe graphics

		Bird.START_X = GAME_WIDTH * (2 / 7f);
		Bird.WIDTH = 17;
		Bird.HEIGHT = 12;
		Bird.ACCEL = 140;
		Bird.DECEL = 200;

		float pipeTravelTime = Scrollables.PIPE_GAP / Scrollables.SCROLL_SPEED;
		Scrollables.PIPE_HOLE_DELTA_HIGHER = (pipeTravelTime * Bird.ACCEL) - Scrollables.PIPE_HOLE;
		Scrollables.PIPE_HOLE_DELTA_LOWER = (pipeTravelTime * Bird.DECEL * 0.8f) - Scrollables.PIPE_HOLE;
	}

	private Constants() {
		throw new AssertionError(Constants.class);
	}

}
