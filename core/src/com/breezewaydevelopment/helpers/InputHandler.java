package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameworld.GameWorld;
import com.breezewaydevelopment.gameworld.GameWorld.GameState;

public class InputHandler implements InputProcessor {

	private Bird bird;
	private GameWorld world;

	// Don't forget inputhandler uses a y-down coord system for some reason ((0,0) is at the top left)

	public InputHandler(GameWorld world) {
		this.world = world;
		bird = world.getBird();
	}

	private boolean handleTap() {
		world.onTap();
		switch (world.getState()) {
			case READY:
				world.setState(GameState.RUNNING);
				bird.onTap();
				break;
			case RUNNING:
				bird.onTap();
				break;
			case GAMEOVER:
				world.restart();
				break;
			default:
				return false;
		}
		return true;
	}

	private boolean handleRelease() {
		world.onRelease();
		return true;
	}

	private boolean isKey(int kc) {
		return kc == Keys.DPAD_CENTER || kc == Keys.ENTER || kc == Keys.SHIFT_LEFT || kc == Keys.SHIFT_RIGHT || kc == Keys.SPACE || kc == Keys.UP;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return handleTap();
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return handleRelease();
	}

	@Override
	public boolean keyDown(int keycode) {
		return isKey(keycode) ? handleTap() : false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return isKey(keycode) ? handleRelease() : false;
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
