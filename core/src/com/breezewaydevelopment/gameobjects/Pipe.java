package com.breezewaydevelopment.gameobjects;

import java.security.SecureRandom;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Constants;

public class Pipe extends Scrollable {

	private Rectangle skullBottom, skullTop, barBottom, barTop;

	private boolean isScored = false;
	private int holeY;

	private static SecureRandom r;
	private static int PREV_HOLE_Y;

	public Pipe(float x) {
		super(x, 0, Constants.Scrollables.PIPE_WIDTH, 0);
		barBottom = new Rectangle(0, 0, width, height);
		barTop = new Rectangle(0, 0, width, height);
		skullBottom = new Rectangle(0, 0, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
		skullTop = new Rectangle(0, 0, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
		setup();
	}

	private void setup() {
		holeY = genHole();

		barBottom.set(position.x, position.y, width, holeY);
		skullBottom.set(position.x - 1, holeY - Constants.Scrollables.SKULL_HEIGHT, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);

		barTop.set(position.x, holeY + Constants.Scrollables.PIPE_HOLE, width, Constants.GAME_HEIGHT - holeY - Constants.Scrollables.PIPE_HOLE);
		skullTop.set(position.x - 1, barTop.getY(), Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		barBottom.setX(position.x);
		barTop.setX(position.x);
		skullBottom.setX(position.x - 1);
		skullTop.setX(position.x - 1);
	}

	@Override
	public void reset(float newX) {
		super.reset(newX);
		// Change the height to a random number
		isScored = false;
		setup();
	}

	@Override
	public float getTailX() {
		return super.getTailX() + Constants.Scrollables.PIPE_GAP;
	}

	public Rectangle getBarBottom() {
		return barBottom;
	}

	public Rectangle getBarTop() {
		return barTop;
	}

	public Rectangle getSkullBottom() {
		return skullBottom;
	}

	public Rectangle getSkullTop() {
		return skullTop;
	}

	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getBoundingCircle(), barBottom) || Intersector.overlaps(bird.getBoundingCircle(), barTop) || Intersector.overlaps(bird.getBoundingCircle(), skullBottom) || Intersector.overlaps(bird.getBoundingCircle(), skullTop));
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

	/*
	 * Y coordinate of the pipe hole (from the top of the bottom pipe section)
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

	// TODO: Don't let pipes get too tall
	private static int randHole() {
		return Constants.Scrollables.GRASS_HEIGHT + Constants.Scrollables.PIPE_CLEARANCE_BOTTOM + r.nextInt((int) Constants.GAME_HEIGHT - Constants.Scrollables.GRASS_HEIGHT - Constants.Scrollables.PIPE_CLEARANCE_TOP - Constants.Scrollables.PIPE_CLEARANCE_BOTTOM);
	}

	private static boolean canReach(int hole1, int hole2) {
		if (hole1 > hole2) {
			return hole1 - hole2 <= Constants.Scrollables.PIPE_HOLE_DELTA_LOWER;
		}
		return hole2 - hole1 <= Constants.Scrollables.PIPE_HOLE_DELTA_HIGHER;
	}

	public static void resetHole() {
		PREV_HOLE_Y = (int) Constants.MIDPOINT_Y;
	}

}
