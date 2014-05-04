package com.breezewaydevelopment.gameworld;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.breezewaydevelopment.helpers.AssetLoader;
import com.breezewaydevelopment.helpers.InputHandler;
import com.breezewaydevelopment.tweenaccessors.Value;
import com.breezewaydevelopment.tweenaccessors.ValueAccessor;
import com.breezewaydevelopment.ui.SimpleButton;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.Grass;
import com.breezewaydevelopment.gameobjects.Pipe;
import com.breezewaydevelopment.gameobjects.ScrollHandler;

public class GameRenderer {

	private int midpointY;

	private GameWorld myWorld;

	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe[] pipes;

	// Game Assets
	private TextureRegion bg, grass, birdMid, skullUp, skullDown, bar, ready,
			logo, gameOver, highScore, scoreboard, star, noStar, retry;
	private Animation birdAnimation;

	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();

	// Buttons
	private List<SimpleButton> menuButtons;
	private Color transitionColor;

	public GameRenderer(GameWorld world, int gameHeight, int midpointY) {
		myWorld = world;

		this.midpointY = midpointY;
		this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initGameObjects();
		initAssets();

		transitionColor = new Color();
		initTransition(255, 255, 255, .5f);
	}

	private void initGameObjects() {
		bird = myWorld.getBird();
		scroller = myWorld.getScroller();
		pipes = scroller.getPipes();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
	}

	private void initAssets() {
		bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		birdAnimation = AssetLoader.birdAnimation;
		birdMid = AssetLoader.bird;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		bar = AssetLoader.bar;
		ready = AssetLoader.ready;
		logo = AssetLoader.logo;
		gameOver = AssetLoader.gameOver;
		highScore = AssetLoader.highScore;
		scoreboard = AssetLoader.scoreboard;
		retry = AssetLoader.retry;
		star = AssetLoader.star;
		noStar = AssetLoader.noStar;
	}

	private void drawGrass() {
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawSkulls() {
		for (Pipe p : pipes) {
			batcher.draw(skullUp, p.getX() - 1, p.getY() + p.getHeight() - 14, 24, 14);
			batcher.draw(skullDown, p.getX() - 1, p.getY() + p.getHeight() + 45, 24, 14);
		}
	}

	private void drawPipes() {
		for (Pipe p : pipes) {
			batcher.draw(bar, p.getX(), p.getY(), p.getWidth(), p.getHeight());
			batcher.draw(bar, p.getX(), p.getY() + p.getHeight() + 45, p.getWidth(), midpointY + 66 - (p.getHeight() + 45));
		}
	}

	private void drawBirdCentered(float runTime) {
		batcher.draw(birdAnimation.getKeyFrame(runTime), 59, bird.getY() - 15, bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
	}

	private void drawBird(float runtime) {
		float birdW = bird.getWidth(), birdH = bird.getHeight();
		batcher.draw(bird.shouldFlap() ? birdAnimation.getKeyFrame(runtime)
				: birdMid, bird.getX(), bird.getY(), birdW / 2.0f, birdH / 2.0f, birdW, birdH, 1, 1, bird.getRotation());
	}

	private void drawMenuUI() {
		batcher.draw(logo, 136 / 2 - 56, midpointY - 50, logo.getRegionWidth() / 1.2f, logo.getRegionHeight() / 1.2f);
		for (SimpleButton button : menuButtons) {
			button.draw(batcher);
		}

	}

	private void drawScoreboard() {
		batcher.draw(scoreboard, 22, midpointY - 30, 97, 37);

		batcher.draw(noStar, 25, midpointY - 15, 10, 10);
		batcher.draw(noStar, 37, midpointY - 15, 10, 10);
		batcher.draw(noStar, 49, midpointY - 15, 10, 10);
		batcher.draw(noStar, 61, midpointY - 15, 10, 10);
		batcher.draw(noStar, 73, midpointY - 15, 10, 10);

		if (myWorld.getScore() > 2) {
			batcher.draw(star, 73, midpointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 17) {
			batcher.draw(star, 61, midpointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 50) {
			batcher.draw(star, 49, midpointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 80) {
			batcher.draw(star, 37, midpointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 120) {
			batcher.draw(star, 25, midpointY - 15, 10, 10);
		}

		int length = ("" + myWorld.getScore()).length();

		AssetLoader.whiteFont.draw(batcher, "" + myWorld.getScore(), 104 - (2 * length), midpointY - 20);

		int length2 = ("" + AssetLoader.getHighScore()).length();
		AssetLoader.whiteFont.draw(batcher, "" + AssetLoader.getHighScore(), 104 - (2.5f * length2), midpointY - 3);

	}

	private void drawRetry() {
		batcher.draw(retry, 36, midpointY + 10, 66, 14);
	}

	private void drawReady() {
		batcher.draw(ready, 36, midpointY - 50, 68, 14);
	}

	private void drawGameOver() {
		batcher.draw(gameOver, 24, midpointY - 50, 92, 14);
	}

	private void drawScore() {
		int length = ("" + myWorld.getScore()).length();
		AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), 68 - (3 * length), midpointY - 82);
		AssetLoader.greenFont.draw(batcher, "" + myWorld.getScore(), 68 - (3 * length), midpointY - 83);
	}

	private void drawHighScore() {
		batcher.draw(highScore, 22, midpointY - 50, 96, 14);
	}

	public void render(float delta, float runTime) {

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

		drawPipes();
		drawGrass();

		batcher.enableBlending();
		drawSkulls();

		if (myWorld.isRunning()) {
			drawBird(runTime);
			drawScore();
		} else if (myWorld.isReady()) {
			drawBird(runTime);
			drawReady();
		} else if (myWorld.isMenu()) {
			drawBirdCentered(runTime);
			drawMenuUI();
		} else if (myWorld.isGameOver()) {
			drawScoreboard();
			drawBird(runTime);
			drawGameOver();
			drawRetry();
		} else if (myWorld.isHighScore()) {
			drawScoreboard();
			drawBird(runTime);
			drawHighScore();
			drawRetry();
		}

		batcher.end();
		drawTransition(delta);

	}

	public void initTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g, transitionColor.b, alpha.getValue());
			shapeRenderer.rect(0, 0, 136, 300);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}

}
