package com.breezewaydevelopment.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.breezewaydevelopment.helpers.AssetLoader;

public class Bird {

	private int width;
	private int height;
	private float originalY;
	private float rotation;
	private boolean isAlive;

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private Circle boundingCircle;

	public Bird(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		this.originalY = y;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);
		boundingCircle = new Circle();
		isAlive = true;
	}

	public void update(float delta) {

		velocity.add(acceleration.cpy().scl(delta));

		if (velocity.y > 200) {
			velocity.y = 200;
		}

		// CEILING CHECK
		if (position.y < -13) {
			position.y = -13;
			velocity.y = 0;
		}

		position.add(velocity.cpy().scl(delta));

		// Set the circle's center to be (9, 6) with respect to the bird.
		// Set the circle's radius to be 6.5f;
		boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

		if (velocity.y < 0) {
			rotation -= 600 * delta; // Rotate counterclockwise

			if (rotation < -20) {
				rotation = -20;
			}
		} else if (!isAlive || velocity.y > 110) {
			rotation += 480 * delta; // Rotate clockwise
			if (rotation > 90) {
				rotation = 90;
			}
		}

	}

	public void updateReady(float runTime) {
		position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
	}

	public boolean shouldFlap() {
		return isAlive && velocity.y <= 70;
	}

	public void onClick() {
		if (isAlive) {
			AssetLoader.flap.play();
			velocity.y = -140;
		}
	}

	public void stop(boolean freeze) {
		velocity.y = 0;
		isAlive = false;
		if (freeze) { // Hit the ground
			acceleration.y = 0;
		}
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

}
