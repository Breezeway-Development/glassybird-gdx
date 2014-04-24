package com.breezewaydevelopment.helpers;

import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameworld.GameWorld;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class InputHandler implements InputProcessor {

	private GameWorld world;
	private Bird bird;

	public InputHandler(GameWorld world) {
		this.world = world;
		this.bird = world.getBird();
	}

	private void handleTouch() {
		if (world.isReady()) {
			world.start();
		}

		bird.onClick();

		if (world.isGameOver() || world.isHighScore()) {
			world.restart();
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		handleTouch();
		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.DPAD_CENTER || keycode == Keys.SPACE) {
			handleTouch();
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
