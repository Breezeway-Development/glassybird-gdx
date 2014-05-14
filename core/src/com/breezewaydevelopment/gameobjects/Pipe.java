package com.breezewaydevelopment.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Constants;

public class Pipe extends Scrollable {

	private Rectangle skullBottom, skullTop, barBottom, barTop;

	private boolean isScored = false;
	private int gap;
	private static Random r;

	public Pipe(float x) {
		super(x, Constants.Scrollables.Y_POSITION, Constants.Scrollables.PIPE_WIDTH, Constants.Scrollables.PIPE_HEIGHT);
		gap = getPipeGap();
		barBottom = new Rectangle();
		barTop = new Rectangle();
		skullBottom = new Rectangle();
		skullTop = new Rectangle();
		setRectangles();
	}

	private void setRectangles() {
		// TODO: Constants for skull placement
		// TODO: FIX PIPES
		barBottom.set(position.x, position.y, width, height);
		skullBottom.set(position.x - 1, gap - Constants.Scrollables.SKULL_HEIGHT, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);

		barTop.set(position.x, gap + Constants.Scrollables.PIPE_GAP_Y, width, Constants.GAME_HEIGHT - (gap + Constants.Scrollables.PIPE_GAP_Y + Constants.Scrollables.SKULL_HEIGHT));
		skullTop.set(position.x - 1, barTop.y, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);

		System.out.println("Pipe " + hashCode() + "\tat " + (int)getX() + "," + (int)getY() + "  " + getWidth() + "x" + getHeight() + "\tgap " + gap);
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
		gap = getPipeGap();
		isScored = false;
		setRectangles();
	}

	@Override
	public float getTailX() {
		return super.getTailX() + Constants.Scrollables.PIPE_GAP_X;
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

	public int getGap() {
		return gap;
	}

	/*
	 * Y coordinate of the pipe gap (from the top of the bottom pipe
	 */

	public int getPipeGap() {
		if (r == null) {
			r = new Random();
		}
		return Constants.Scrollables.PIPE_OFFSET + r.nextInt((int) Constants.GAME_HEIGHT - Constants.Scrollables.PIPE_OFFSET);
	}
}
