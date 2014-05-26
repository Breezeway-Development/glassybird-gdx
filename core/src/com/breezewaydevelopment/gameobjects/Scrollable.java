package com.breezewaydevelopment.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {

	public static float SCROLL_SPEED = 54;

	// Protected is similar to private, but allows inheritance by subclasses
	protected Vector2 position;
	protected Vector2 velocity;

	protected int width;
	protected int height;

	protected boolean isScrolledLeft;

	public Scrollable(float x, float y, int width, int height) {
		position = new Vector2(x, y);
		velocity = new Vector2(-1 * SCROLL_SPEED, 0);
		this.width = width;
		this.height = height;
		isScrolledLeft = false;
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
		if (position.x + width < 0) { // No longer visible
			isScrolledLeft = true;
		}
	}

	public void reset(float newX) {
		position.x = newX;
		isScrolledLeft = false;
	}

	public void stop() {
		velocity.x = 0;
	}

	// Getters for instance variables
	public boolean isScrolledLeft() {
		return isScrolledLeft;
	}

	public float getTailX() {
		return position.x + width;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void onRestart(float x) {
		reset(x);
		velocity.x = -1 * SCROLL_SPEED;
	}

}
