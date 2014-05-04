package com.breezewaydevelopment.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.breezewaydevelopment.helpers.AssetLoader;

public class Bird {

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private Circle boundingCircle;

	private int width;
	private int height;
	private float rotation;
	private float originalY;

	private boolean isAlive;

	public Bird(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		originalY = y;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);

		boundingCircle = new Circle();

		isAlive = true;
	}

	public void update(float delta) {
		velocity.add(acceleration.cpy().scl(delta));
		if (velocity.y > 200) { // Don't fall too fast
			velocity.y = 200;
		}
		
		if (position.y < -13) { // Ceiling
			position.y = -13;
			velocity.y = 0;
		}
		position.add(velocity.cpy().scl(delta));

		boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

		// Rotate counterclockwise (we're going up)
		if (velocity.y < 0) {
			rotation -= 600 * delta;
			if (rotation < -20) {
				rotation = -20;
			}
		} else if (velocity.y > 110 || !isAlive) { // Rotate clockwise (falling down)
			rotation += 480 * delta;
			if (rotation > 90) {
				rotation = 90;
			}

		}
	}
	
	public void updateReady(float runtime) {
		position.y = 2 * (float) Math.sin(7 * runtime) + originalY;
	}

	public void die() {
		isAlive = false;
		AssetLoader.dead.play();
	}

	public void stop(boolean decel) {
		velocity.y = 0;
		if (decel) {
			acceleration.y = 0;
		}
	}

	public boolean shouldFlap() {
		return isAlive && velocity.y < 70;
	}

	public void onClick() {
		if (isAlive) {
			AssetLoader.flap.play();
			velocity.y = -140;
		}
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void onRestart(int y) {
		rotation = 0;
		position.y = y;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = 460;
		isAlive = true;
	}

}
