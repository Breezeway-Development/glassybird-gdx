package com.breezewaydevelopment.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.breezewaydevelopment.helpers.Assets;
import com.breezewaydevelopment.helpers.Constants;

public class Bird {

	public static int WIDTH = 17;
	public static int HEIGHT = 12;
	public static int ROT_DOWN = -350;
	public static int ROT_DOWN_MAX = -90;
	public static int ROT_UP = 600;
	public static int ROT_UP_MAX = 20;
	public static float ACCEL = 145;
	public static float DECEL = 480;
	public static float DECEL_MAX = 205;
	public static float START_X;

	public static float CIRC_OFFSET_X;
	public static float CIRC_OFFSET_Y;
	public static float CIRC_RADIUS = 6.5f;

	private int width;
	private int height;
	private float rotation;
	private float runtime;
	private boolean isAlive;

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private Circle bounds;

	public Bird() {
		this.width = WIDTH;
		this.height = HEIGHT;
		position = new Vector2(START_X, Constants.MIDPOINT_Y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, -1 * DECEL);
		bounds = new Circle();
		bounds.setRadius(CIRC_RADIUS);
		isAlive = true;
	}

	public void updateReady(float delta) {
		runtime += delta;
		position.y = Constants.MIDPOINT_Y + (float) (1.5f * Math.sin(7 * runtime));
		updateCircle();
	}

	public void update(float delta) {
		runtime += delta;
		velocity.add(acceleration.cpy().scl(delta));
		if (velocity.y < -1 * DECEL_MAX) {
			velocity.y = -1 * DECEL_MAX;
		}
		if (position.y > Constants.GAME_HEIGHT) { // Ceiling
			position.y = Constants.GAME_HEIGHT;
			velocity.y = 0;
		}
		position.add(velocity.cpy().scl(delta));
		updateCircle();
		rotate(delta);
	}

	public void updateGameover(float delta) {
		updateCircle();
		rotate(delta);
	}

	private void updateCircle() {
		bounds.setPosition(position.x + CIRC_OFFSET_X, position.y + CIRC_OFFSET_Y);
	}

	private void rotate(float delta) {
		if (velocity.y >= 0) {
			rotation += ROT_UP * delta;
			if (rotation > ROT_UP_MAX) {
				rotation = ROT_UP_MAX;
			}
		} else if (!isAlive || velocity.y <= -110) {
			rotation += ROT_DOWN * delta;
			if (rotation < ROT_DOWN_MAX) {
				rotation = ROT_DOWN_MAX;
			}
		}
	}

	public boolean shouldFlap() {
		return isAlive && velocity.y >= -110;
	}

	public void onTap() {
		if (isAlive) {
			Assets.flap.play();
			velocity.y = ACCEL;
			runtime = 0; // Always flap wings down on tap
		}
	}

	public void stop(boolean freeze) {
		isAlive = false;
		if (freeze) { // Hit the ground
			acceleration.y = 0;
		}
	}

	public void onRestart() {
		rotation = 0;
		runtime = 0;
		position.y = Constants.MIDPOINT_Y;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = -1 * DECEL;
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
		return bounds;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public float getRuntime() {
		return runtime;
	}

}
