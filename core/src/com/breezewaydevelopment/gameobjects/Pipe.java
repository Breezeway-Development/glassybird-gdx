package com.breezewaydevelopment.gameobjects;

import java.security.SecureRandom;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Constants;

public class Pipe extends Scrollable {

	public static int GAP = 60;
	public static int HOLE = 45;
	public static int CLEARANCE_BOTTOM = 10;
	public static int CLEARANCE_TOP = HOLE + 10;
	public static int WIDTH = 23;
	public static float HOLE_DELTA_LOWER;
	public static float HOLE_DELTA_HIGHER;

	public static int RING_WIDTH = WIDTH + 2;
	public static int RING_HEIGHT = 16;

	private static int PREV_HOLE_Y;
	private static SecureRandom r;

	private Rectangle ringBottom, ringTop, barBottom, barTop;

	private boolean isScored = false;
	private int holeY;

	public Pipe(float x) {
		super(x, 0, WIDTH, 0);
		barBottom = new Rectangle(0, 0, width, height);
		barTop = new Rectangle(0, 0, width, height);
		ringBottom = new Rectangle(0, 0, RING_WIDTH, RING_HEIGHT);
		ringTop = new Rectangle(0, 0, RING_WIDTH, RING_HEIGHT);
		setup();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		barBottom.setX(position.x);
		barTop.setX(position.x);
		ringBottom.setX(position.x - 1);
		ringTop.setX(position.x - 1);
	}

	private void setup() {
		holeY = genHole();

		barBottom.set(position.x, position.y, width, holeY);
		ringBottom.set(position.x - 1, holeY - RING_HEIGHT, RING_WIDTH, RING_HEIGHT);

		barTop.set(position.x, holeY + HOLE, width, Constants.GAME_HEIGHT - holeY - HOLE);
		ringTop.set(position.x - 1, barTop.getY(), RING_WIDTH, RING_HEIGHT);
	}

	@Override
	public void reset(float newX) {
		super.reset(newX);
		isScored = false;
		setup();
	}

	@Override
	public float getTailX() {
		return super.getTailX() + GAP;
	}

	public Rectangle getBarBottom() {
		return barBottom;
	}

	public Rectangle getBarTop() {
		return barTop;
	}

	public Rectangle getRingBottom() {
		return ringBottom;
	}

	public Rectangle getRingTop() {
		return ringTop;
	}

	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getBoundingCircle(), barBottom) || Intersector.overlaps(bird.getBoundingCircle(), barTop) || Intersector.overlaps(bird.getBoundingCircle(), ringBottom) || Intersector.overlaps(bird.getBoundingCircle(), ringTop));
		}
		return false;
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}

	public int getHole() {
		return holeY;
	}

	public static void resetHole() {
		PREV_HOLE_Y = (int) Constants.MIDPOINT_Y;
	}

	/*
	 * Returns the y coordinate of the pipe hole from the top of the bottom pipe
	 * section
	 */

	private static int genHole() {
		if (r == null || PREV_HOLE_Y == 0) {
			r = new SecureRandom();
			resetHole();
		}

		int testHole = randHole();
		while (!canReach(PREV_HOLE_Y, testHole)) {
			testHole = randHole();
		}
		return PREV_HOLE_Y = testHole;
	}

	private static int randHole() {
		return Grass.GRASS_HEIGHT + CLEARANCE_BOTTOM + r.nextInt((int) Constants.GAME_HEIGHT - Grass.GRASS_HEIGHT - CLEARANCE_TOP - CLEARANCE_BOTTOM);
	}

	private static boolean canReach(int hole1, int hole2) {
		if (hole1 > hole2) {
			return hole1 - hole2 <= Pipe.HOLE_DELTA_LOWER;
		}
		return hole2 - hole1 <= Pipe.HOLE_DELTA_HIGHER;
	}

}
