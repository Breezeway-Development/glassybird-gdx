package com.breezewaydevelopment.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.helpers.AssetLoader;

public class GameRenderer {

	private GameWorld world;
	private OrthographicCamera cam;
	
	// Game Objects
	private Bird bird;

	// Game Assets
	private TextureRegion bg, grass;
	private Animation birdAnimation;
	private TextureRegion birdMid, birdDown, birdUp;
	private TextureRegion skullUp, skullDown, bar;


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
		
		initGameObjects();
		initAssets();
	}
	
	private void initGameObjects() {
        bird = world.getBird();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        birdDown = AssetLoader.birdDown;
        birdUp = AssetLoader.birdUp;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
    }

	public void render(float runtime) {

		// Black bg prevents flickering
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();
        batcher.draw(bg, 0, midpointY + 23, 136, 43);

        batcher.enableBlending();
        
        float birdX = bird.getX(), birdY = bird.getY(), birdW = bird.getWidth(), birdH = bird.getHeight();
        if (!bird.shouldFlap()) {
            batcher.draw(birdMid, birdX, birdY, birdW / 2.0f, birdH / 2.0f, birdW, birdH, 1, 1, bird.getRotation());
        } else {
            batcher.draw(birdAnimation.getKeyFrame(runtime), birdX, birdY, birdW / 2.0f, birdH / 2.0f, birdW, birdH, 1, 1, bird.getRotation());
        }

        batcher.end();
	}

}
