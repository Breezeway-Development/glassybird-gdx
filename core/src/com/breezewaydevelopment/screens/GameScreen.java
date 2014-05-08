package com.breezewaydevelopment.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.breezewaydevelopment.helpers.InputHandler;
import com.breezewaydevelopment.gameworld.GameRenderer;
import com.breezewaydevelopment.gameworld.GameWorld;

public class GameScreen implements Screen {

	private float runTime = 0;
	
	private GameWorld world;
	private GameRenderer renderer;

	public GameScreen() {
		world = new GameWorld();
		Gdx.input.setInputProcessor(new InputHandler(world));
		renderer = new GameRenderer(world);
		world.setRenderer(renderer);
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
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
