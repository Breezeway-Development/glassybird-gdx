package com.breezewaydevelopment.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.helpers.AssetLoader;

public class GameRenderer {

	private GameWorld world;
	private OrthographicCamera cam;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;

	private int midpointY;
	private int gameHeight;

	public GameRenderer(GameWorld world, int gameHeigh, int midpointY) {
		this.world = world;
		this.gameHeight = gameHeight;
		this.midpointY = midpointY;

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, 204);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
	}

	public void render(float runtime) {
		Bird bird = world.getBird();

		// Black bg prevents flickering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Begin ShapeRenderer
		shapeRenderer.begin(ShapeType.Filled);
		// Draw Background color
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(0, 0, 136, midpointY + 66);
		// Draw Grass
		shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		shapeRenderer.rect(0, midpointY + 66, 136, 11);
		// Draw Dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, midpointY + 77, 136, 52);
		// End ShapeRenderer
		shapeRenderer.end();

		// Begin SpriteBatch
		batcher.begin();
		// Disable transparency is good for performance
		batcher.disableBlending();
		batcher.draw(AssetLoader.bg, 0, midpointY + 23, 136, 43);
		// The bird needs transparency, though.
		batcher.enableBlending();
		// Draw bird at its coordinates. Retrieve the Animation object from AssetLoader
		// Pass in the runTime variable to get the current frame.
		batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runtime), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());
		// End SpriteBatch
		batcher.end();
	}

}
