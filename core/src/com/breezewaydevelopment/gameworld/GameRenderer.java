package com.breezewaydevelopment.gameworld;

import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.Grass;
import com.breezewaydevelopment.gameobjects.Pipe;
import com.breezewaydevelopment.gameobjects.ScrollHandler;
import com.breezewaydevelopment.helpers.AssetLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameRenderer {

	private GameWorld world;
	private OrthographicCamera cam;

	private int midpointY;
	private int gameHeight;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, secondGrass, thirdGrass, fourthGrass;
	private Pipe[] pipes;

	// Game Assets
	private TextureRegion bg, grass;
	private Animation birdAnimation;
	private TextureRegion birdMid, birdDown, birdUp;
	private TextureRegion skullUp, skullDown, bar;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;

	public GameRenderer(GameWorld world, int gameHeight, int midpointY) {
		this.world = world;
		this.gameHeight = gameHeight;
		this.midpointY = midpointY;

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 320, 180);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initGameObjects();
		initAssets();
	}

	private void initGameObjects() {
		bird = world.getBird();
		scroller = world.getScroller();
		pipes = scroller.getPipes();
		frontGrass = scroller.getFrontGrass();
		secondGrass = scroller.getSecondGrass();
		thirdGrass = scroller.getThirdGrass();
		fourthGrass = scroller.getFourthGrass();
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
		shapeRenderer.rect(0, 0, 320, 180);

		// Draw Dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, midpointY + 77, 320, 52);

		shapeRenderer.end();

		batcher.begin();
		batcher.disableBlending();
		batcher.draw(bg, 0, midpointY + 23, 320, 43);

		drawGrass();
		drawPipes();
		batcher.enableBlending();
		drawSkulls();
		drawBird(runtime);

		if (world.isReady()) {
         // Draw shadow first
         AssetLoader.shadow.draw(batcher, "Tap me", (136 / 2) - (42), 60);
         // Draw text
         AssetLoader.font
                 .draw(batcher, "Tap me", (136 / 2) - (42 - 1), 59);
     } else {

         if (world.isGameOver() || world.isHighScore()) {

             if (world.isGameOver()) {
                 AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
                 AssetLoader.font.draw(batcher, "Game Over", 24, 55);

                 AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
                 AssetLoader.font.draw(batcher, "High Score:", 22, 105);

                 String highScore = AssetLoader.getHighScore() + "";

                 // Draw shadow first
                 AssetLoader.shadow.draw(batcher, highScore, (136 / 2)
                         - (3 * highScore.length()), 128);
                 // Draw text
                 AssetLoader.font.draw(batcher, highScore, (136 / 2)
                         - (3 * highScore.length() - 1), 127);
             } else {
                 AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
                 AssetLoader.font.draw(batcher, "High Score!", 18, 55);
             }

             AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
             AssetLoader.font.draw(batcher, "Try again?", 24, 75);

             // Convert integer into String
             String score = world.getScore() + "";

             // Draw shadow first
             AssetLoader.shadow.draw(batcher, score,
                     (136 / 2) - (3 * score.length()), 12);
             // Draw text
             AssetLoader.font.draw(batcher, score,
                     (136 / 2) - (3 * score.length() - 1), 11);

         }

			String score = Integer.toString(world.getScore());
			// Draw shadow first
			AssetLoader.shadow.draw(batcher, score, (136 / 2) - (3 * score.length()), 12);
			// Draw text
			AssetLoader.font.draw(batcher, score, (136 / 2) - (3 * score.length() - 1), 11);
		}

		batcher.end();
	}

	private void drawGrass() {
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, secondGrass.getX(), secondGrass.getY(), secondGrass.getWidth(), secondGrass.getHeight());
		batcher.draw(grass, thirdGrass.getX(), thirdGrass.getY(), thirdGrass.getWidth(), thirdGrass.getHeight());
		batcher.draw(grass, fourthGrass.getX(), fourthGrass.getY(), fourthGrass.getWidth(), fourthGrass.getHeight());
	}

	private void drawPipes() {
		for (Pipe p : pipes) {
			batcher.draw(bar, p.getX(), p.getY(), p.getWidth(), p.getHeight());
			batcher.draw(bar, p.getX(), p.getY() + p.getHeight() + 45, p.getWidth(), midpointY + 66 - (p.getHeight() + 45));
		}
	}

	private void drawSkulls() {
		for (Pipe p : pipes) {
			batcher.draw(skullUp, p.getX() - 1, p.getY() + p.getHeight() - 14, 24, 14);
			batcher.draw(skullDown, p.getX() - 1, p.getY() + p.getHeight() + 45, 24, 14);
		}
	}

	private void drawBird(float runtime) {
		float birdX = bird.getX(), birdY = bird.getY(), birdW = bird.getWidth(), birdH = bird.getHeight();
		batcher.draw(bird.shouldFlap() ? birdAnimation.getKeyFrame(runtime)
				: birdMid, // Only flap if not falling
				birdX, birdY, birdW / 2.0f, birdH / 2.0f, birdW, birdH, 1, 1, bird.getRotation());
	}

}
