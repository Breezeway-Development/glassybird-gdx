package com.breezewaydevelopment.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Constants;

public class Pipe extends Scrollable {

	private Rectangle skullBottom, skullTop, barBottom, barTop;

	private boolean isScored = false;
	private int holeY;
	private static Random r;

	public Pipe(float x) {
		super(x, 0, Constants.Scrollables.PIPE_WIDTH, Constants.Scrollables.PIPE_HEIGHT);
		holeY = genHole();
		barBottom = new Rectangle(0, 0, width, height);
		barTop = new Rectangle(0, 0, width, height);
		skullBottom = new Rectangle(0, 0, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
		skullTop = new Rectangle(0, 0, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
		setRectangles();
	}

	private void setRectangles() {
		barBottom.set(position.x, position.y, width, holeY);
		skullBottom.set(position.x - 1, holeY - Constants.Scrollables.SKULL_HEIGHT, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
		
		barTop.set(position.x, holeY + Constants.Scrollables.PIPE_HOLE, width, Constants.GAME_HEIGHT - holeY - Constants.Scrollables.PIPE_HOLE);
		skullTop.set(position.x - 1, barTop.getY(), Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);

		System.out.println("Pipe " + hashCode() + "\tat " + (int) getX() + "," + (int) getY() + "  " + getWidth() + "x" + getHeight() + "\tgap " + holeY);
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
		holeY = genHole();
		isScored = false;
		setRectangles();
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
	 * Y coordinate of the pipe gap (from the top of the bottom pipe
	 */

	private static int genHole() {
		if (r == null) {
			r = new Random();
		}
		return Constants.Scrollables.GRASS_HEIGHT + Constants.Scrollables.PIPE_CLEARANCE + r.nextInt((int) Constants.GAME_HEIGHT - Constants.Scrollables.GRASS_HEIGHT - 2 * Constants.Scrollables.PIPE_CLEARANCE);
	}
}
