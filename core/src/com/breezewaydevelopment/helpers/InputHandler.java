package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.InputProcessor;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameworld.GameWorld;
import com.breezewaydevelopment.gameworld.GameWorld.GameState;

public class InputHandler implements InputProcessor {
	private Bird myBird;
	private GameWorld world;

	// Don't forget inputhandler uses a y-down coord system for some reason ((0,0) is at the top left)

	public InputHandler(GameWorld world) {
		this.world = world;
		myBird = world.getBird();
	}

	public boolean handleTap() {
		switch (world.getState()) {
			case READY:
				world.setState(GameState.RUNNING);
				myBird.onClick();
				break;
			case RUNNING:
				myBird.onClick();
				break;
			case GAMEOVER:
				world.restart();
				break;
			default:
				return false;
		}
		return true;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return handleTap();
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return handleTap();
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

}
