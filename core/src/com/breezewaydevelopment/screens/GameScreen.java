package com.breezewaydevelopment.screens;

import com.breezewaydevelopment.gameworld.GameRenderer;
import com.breezewaydevelopment.gameworld.GameWorld;
import com.breezewaydevelopment.helpers.InputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	private float runtime = 0;

	private GameWorld world;
	private GameRenderer renderer;

	public GameScreen() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);

		int midpointY = (int) (gameHeight / 2);
		
		world = new GameWorld(midpointY);
		Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
		renderer = new GameRenderer(world, (int) gameHeight, midpointY);
	}

	@Override
	public void render(float delta) {
		runtime += delta;
		world.update(delta);
		renderer.render(delta, runtime);
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
