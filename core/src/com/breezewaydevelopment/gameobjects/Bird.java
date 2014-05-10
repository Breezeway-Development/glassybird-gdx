package com.breezewaydevelopment.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.breezewaydevelopment.helpers.Assets;
import com.breezewaydevelopment.helpers.Constants;

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

	public Bird() {
		this.width = Constants.Bird.WIDTH;
		this.height = Constants.Bird.HEIGHT;
		originalY = Constants.Bird.START_Y;
		position = new Vector2(Constants.Bird.START_X, originalY);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, -460);
		boundingCircle = new Circle(position.x, position.y, 6.5f);
		isAlive = true;
	}

	public void update(float delta) {

		velocity.add(acceleration.cpy().scl(delta));
		if (velocity.y < -200) {
			velocity.y = -200;
		}

		if (position.y > Constants.GAME_HEIGHT) {
			System.out.println("Ceiling");
			position.y = Constants.GAME_HEIGHT;
			velocity.y = 0;
		}

		position.add(velocity.cpy().scl(delta));

		boundingCircle.setPosition(position.x + 9, position.y + 6);

		// Clockwise rotation in degrees
		if (velocity.y >= 0) { // Going up
			rotation += 600 * delta;
			if (rotation > 20) {
				rotation = 20;
			}
		} else if (!isAlive || velocity.y <= -110) { // Going down
			// TODO: More responsive clockwise rotation
			rotation -= 480 * delta;
			if (rotation < -90) {
				rotation = -90;
			}
		}

	}

	public void updateReady(float runTime) {
		position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
		position.x = Constants.MIDPOINT_X - width;
	}

	public boolean shouldFlap() {
		return isAlive && velocity.y >= -70;
	}

	public void onClick() {
		if (isAlive) {
			Assets.flap.play();
			velocity.y = 140;
		}
	}

	public void stop(boolean freeze) {
		velocity.y = 0;
		isAlive = false;
		if (freeze) { // Hit the ground
			acceleration.y = 0;
		}
	}

	public void onRestart() {
		rotation = 0;
		position.y = Constants.Bird.START_Y;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = -460;
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
