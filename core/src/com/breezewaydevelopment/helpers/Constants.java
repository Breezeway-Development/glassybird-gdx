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
		public static float START_X, START_Y, ACCEL, DECEL;
		public static int WIDTH, HEIGHT;
	}

	public static class Scrollables {

		public static int PIPE_GAP;
		public static int PIPE_HOLE;
		public static int PIPE_CLEARANCE;
		public static int PIPE_WIDTH;
		public static int SKULL_WIDTH;
		public static int SKULL_HEIGHT;

		public static int GRASS_WIDTH, GRASS_HEIGHT;

		public static float PIPE_HOLE_DELTA_HIGHER, PIPE_HOLE_DELTA_LOWER,
				SCROLL_SPEED;
	}

	public static void calc() {
		SCREEN_WIDTH = Gdx.graphics.getWidth(); // 272
		SCREEN_HEIGHT = Gdx.graphics.getHeight(); // 480
		GAME_WIDTH = 136;
		SCALE_FACTOR = GAME_WIDTH / SCREEN_WIDTH; // 2

		GAME_HEIGHT = SCREEN_HEIGHT * SCALE_FACTOR; // 240
		MIDPOINT_Y = GAME_HEIGHT / 2; // 120
		MIDPOINT_X = GAME_WIDTH / 2;

		Scrollables.SCROLL_SPEED = 59;

		Scrollables.GRASS_WIDTH = (int) GAME_WIDTH;
		Scrollables.GRASS_HEIGHT = 11;
		Scrollables.PIPE_GAP = 55;
		Scrollables.PIPE_HOLE = 45;
		Scrollables.PIPE_WIDTH = 22;
		Scrollables.PIPE_CLEARANCE = 15;
		Scrollables.SKULL_WIDTH = 24;
		Scrollables.SKULL_HEIGHT = 11;

		// TODO: Don't let pipes get too tall

		Bird.START_X = GAME_WIDTH * (2 / 7f);
		Bird.START_Y = MIDPOINT_Y;
		Bird.WIDTH = 17;
		Bird.HEIGHT = 12;
		Bird.ACCEL = 140;
		Bird.DECEL = 200;

		//		float pipeDist = Scrollables.PIPE_GAP + Scrollables.PIPE_WIDTH;
		//		float pipeTravelTime = pipeDist / Math.abs(Scrollables.SCROLL_SPEED);
		//		
		//		Vector2 bird = new Vector2(Scrollables.SCROLL_SPEED, Bird.ACCEL);
		//		bird.scl(pipeTravelTime);
		//		float maxBirdTravel = bird.len();
		//		
		//		Scrollables.PIPE_MAX_HOLE_DELTA = (float) Math.sqrt(sqr(maxBirdTravel) - sqr(pipeDist)); // Pythagorean theorem
		//		
		//		System.out.println(Scrollables.PIPE_MAX_HOLE_DELTA);

		float pipeTravelTime = (Scrollables.PIPE_GAP + Scrollables.PIPE_WIDTH) / Scrollables.SCROLL_SPEED;
		Scrollables.PIPE_HOLE_DELTA_HIGHER = (Bird.ACCEL / pipeTravelTime) - Scrollables.PIPE_HOLE;
		Scrollables.PIPE_HOLE_DELTA_LOWER = (Bird.DECEL / pipeTravelTime) - Scrollables.PIPE_HOLE;
		System.out.printf("Higher %f Lower %f\n", Scrollables.PIPE_HOLE_DELTA_HIGHER, Scrollables.PIPE_HOLE_DELTA_LOWER);
	}

	private Constants() {
		throw new AssertionError(Constants.class);
	}

}
