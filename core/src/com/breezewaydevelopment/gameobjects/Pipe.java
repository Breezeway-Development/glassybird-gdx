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

	private void setRectangles() { // TODO: Fix pipe rectangles
		barUp.set(position.x, position.y, width, height);		
		barDown = new Rectangle(position.x, position.y + height + Constants.Scrollables.PIPE_GAP_Y, width, Constants.Scrollables.Y_POSITION - (position.y + height + Constants.Scrollables.PIPE_GAP_Y));
		skullUp.set(position.x - 1, position.y + height - Constants.Scrollables.SKULL_HEIGHT,Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
		skullDown.set(position.x - 1, barDown.y, Constants.Scrollables.SKULL_WIDTH, Constants.Scrollables.SKULL_HEIGHT);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		// x, y of top left corner, width, height of rectangle
		
		barUp.setX(position.x);
		barDown.setX(position.x);
		
		skullUp.setX(position.x - 1);
		skullDown.setX(position.x - 1);
		
//		barUp.set(position.x, position.y, width, height);
//		barDown.set(position.x, position.y + height + VERTICAL_GAP, width, groundY - (position.y + height + VERTICAL_GAP));
//
//		// Our skull width is 24. The bar is only 22 pixels wide. So the skull
//		// must be shifted by 1 pixel to the left (so that the skull is centered
//		// with respect to its bar).
//
//		// This shift is equivalent to: (SKULL_WIDTH - width) / 2
//		skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
//		skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y, SKULL_WIDTH, SKULL_HEIGHT);
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
