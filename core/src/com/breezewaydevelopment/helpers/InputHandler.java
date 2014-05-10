package com.breezewaydevelopment.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameworld.GameWorld;
import com.breezewaydevelopment.ui.SimpleButton;

public class InputHandler implements InputProcessor {
	private Bird myBird;
	private GameWorld world;

	private List<SimpleButton> menuButtons;

	private SimpleButton playButton;

	// Don't forget inputhandler uses a y-down coord system for some reason ((0,0) is at the top left)

	public InputHandler(GameWorld world) {
		this.world = world;
		myBird = world.getBird();

		menuButtons = new ArrayList<SimpleButton>();
		playButton = new SimpleButton((Constants.GAME_WIDTH - Assets.playButtonUp.getRegionWidth()) / 2, Constants.MIDPOINT_Y - 50, Assets.playButtonUp, Assets.playButtonDown);
		//TODO: Constants for UI positioning
		menuButtons.add(playButton);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		x = scale(x);
		y = scale(Constants.SCREEN_HEIGHT - y);

		if (world.isMenu()) {
			playButton.isTouchDown(x, y);
		} else if (world.isReady()) {
			world.start();
			myBird.onClick();
		} else if (world.isRunning()) {
			myBird.onClick();
		}

		if (world.isGameOver() || world.isHighScore()) {
			world.restart();
		}

		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		x = scale(x);
		y = scale(Constants.SCREEN_HEIGHT - y);

		if (world.isMenu()) {
			if (playButton.isTouchUp(x, y)) {
				world.ready();
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE) {

			if (world.isMenu()) {
				world.ready();
			} else if (world.isReady()) {
				world.start();
			}

			myBird.onClick();

			if (world.isGameOver() || world.isHighScore()) {
				world.restart();
			}

		}

		return false;
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

	private int scale(float i) {
		return (int) (i * Constants.SCALE_FACTOR);
	}

	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}
}
