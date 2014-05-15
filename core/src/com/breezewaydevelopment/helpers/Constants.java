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
		public static float START_X, START_Y;
		public static int WIDTH, HEIGHT;
	}

	public static class Scrollables {
		public static int SCROLL_SPEED;

		public static int PIPE_GAP;
		public static int PIPE_HOLE;
		public static int PIPE_CLEARANCE;
		public static int PIPE_WIDTH;
		public static int PIPE_HEIGHT;
		public static int SKULL_WIDTH;
		public static int SKULL_HEIGHT;

		public static int GRASS_WIDTH, GRASS_HEIGHT;
	}

	public static void calc() {
		SCREEN_WIDTH = Gdx.graphics.getWidth(); // 272
		SCREEN_HEIGHT = Gdx.graphics.getHeight(); // 480
		GAME_WIDTH = 136;
		SCALE_FACTOR = GAME_WIDTH / SCREEN_WIDTH; // 2

		GAME_HEIGHT = SCREEN_HEIGHT * SCALE_FACTOR; // 240
		MIDPOINT_Y = GAME_HEIGHT / 2; // 120
		MIDPOINT_X = GAME_WIDTH / 2;

		Scrollables.SCROLL_SPEED = -59;

		Scrollables.GRASS_WIDTH = (int) GAME_WIDTH;
		Scrollables.GRASS_HEIGHT = 11;
		Scrollables.PIPE_GAP = 49;
		Scrollables.PIPE_HOLE = 45;
		Scrollables.PIPE_WIDTH = 22;
		Scrollables.PIPE_CLEARANCE = 15; // TODO: Calculate this so you can play on a tall screen
		Scrollables.SKULL_WIDTH = 24;
		Scrollables.SKULL_HEIGHT = 11;

		Bird.START_X = GAME_WIDTH * (2 / 7f);
		Bird.START_Y = MIDPOINT_Y;
		Bird.WIDTH = 17;
		Bird.HEIGHT = 12;
	}

	private Constants() {
		throw new AssertionError(Constants.class);
	}

}