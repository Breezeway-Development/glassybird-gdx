package com.breezewaydevelopment.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Constants;
import com.breezewaydevelopment.helpers.Util;

public class Pipe extends Scrollable {

	private Rectangle skullUp, skullDown, barUp, barDown;

	private boolean isScored = false;

	public Pipe(float x) {
		super(x, Constants.Scrollables.Y_POSITION, Constants.Scrollables.PIPE_WIDTH, Util.genPipeHeight());
		barUp = new Rectangle();
		barDown = new Rectangle();
		skullUp = new Rectangle();
		skullDown = new Rectangle();
		setRectangles();
	}

	private void setRectangles() { // TODO: Pipe rectangles?
		// Do these make sense?
		barUp.set(position.x, position.y, width, height);
		barDown = new Rectangle(position.x, position.y + height + Constants.Scrollables.PIPE_GAP_Y, width, Constants.Scrollables.Y_POSITION - (position.y + height + Constants.Scrollables.PIPE_GAP_Y));
		skullUp.set(position.x - 1, position.y + height - Constants.Scrollables.SKULL_HEIGHT, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
		skullDown.set(position.x - 1, barDown.y, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		barUp.setX(position.x);
		barDown.setX(position.x);
		skullUp.setX(position.x - 1);
		skullDown.setX(position.x - 1);
	}

	@Override
	public void reset(float newX) {
		super.reset(newX);
		// Change the height to a random number
		height = Util.genPipeHeight();
		isScored = false;
		setRectangles();
	}

	@Override
	public float getTailX() {
		return super.getTailX() + Constants.Scrollables.PIPE_GAP_X;
	}

	public Rectangle getSkullUp() {
		return skullUp;
	}

	public Rectangle getSkullDown() {
		return skullDown;
	}

	public Rectangle getBarUp() {
		return barUp;
	}

	public Rectangle getBarDown() {
		return barDown;
	}

	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getBoundingCircle(), barUp) || Intersector.overlaps(bird.getBoundingCircle(), barDown) || Intersector.overlaps(bird.getBoundingCircle(), skullUp) || Intersector.overlaps(bird.getBoundingCircle(), skullDown));
		}
		return false;
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}
}
