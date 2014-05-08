package com.breezewaydevelopment.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Assets;

public class SimpleButton {

	private float x, y, width, height;
	private boolean isPressed = false;

	private TextureRegion buttonUp;
	private TextureRegion buttonDown;

	private Rectangle bounds;

	public SimpleButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;

		bounds = new Rectangle(x, y, width, height);
	}

	public boolean isClicked(int screenX, int screenY) {
		return bounds.contains(screenX, screenY);
	}

	public void draw(SpriteBatch batcher) {
		batcher.draw(isPressed ? buttonDown : buttonUp, x, y, width, height);
	}

	public boolean isTouchDown(int screenX, int screenY) {
		if (bounds.contains(screenX, screenY)) {
			isPressed = true;
			return true;
		}
		return false;
	}

	public boolean isTouchUp(int screenX, int screenY) {
		if (isPressed && bounds.contains(screenX, screenY)) {
			isPressed = false;
			Assets.flap.play();
			return true;
		}
		// Whenever a finger is released, we will cancel any presses.
		isPressed = false;
		return false;
	}

}
