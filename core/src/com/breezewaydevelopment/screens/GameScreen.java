package com.breezewaydevelopment.screens;

import com.breezewaydevelopment.gameworld.GameRenderer;
import com.breezewaydevelopment.gameworld.GameWorld;
import com.breezewaydevelopment.helpers.InputHelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;

	private float runtime = 0;

	public GameScreen() {
		System.out.println("GameScreen attached");
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);

		int midpointY = (int) (gameHeight / 2);

		world = new GameWorld(midpointY);
		renderer = new GameRenderer(world, (int) gameHeight, midpointY);

		Gdx.input.setInputProcessor(new InputHelper(world.getBird()));
	}

	@Override
	public void render(float delta) {
		runtime += delta;
		world.update(delta);
		renderer.render(runtime);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
