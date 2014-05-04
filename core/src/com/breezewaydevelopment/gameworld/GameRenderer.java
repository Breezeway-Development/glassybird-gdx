package com.breezewaydevelopment.gameworld;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.Grass;
import com.breezewaydevelopment.gameobjects.Pipe;
import com.breezewaydevelopment.gameobjects.ScrollHandler;
import com.breezewaydevelopment.helpers.AssetLoader;
import com.breezewaydevelopment.helpers.InputHandler;
import com.breezewaydevelopment.tweenaccessors.Value;
import com.breezewaydevelopment.tweenaccessors.ValueAccessor;
import com.breezewaydevelopment.ui.SimpleButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe[] pipes;

	// Game Assets
	private TextureRegion bg, grass;
	private Animation birdAnimation;
	private TextureRegion birdMid, birdDown, birdUp;
	private TextureRegion skullUp, skullDown, bar;

	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();

	//UI
	private List<SimpleButton> menuButtons;

	public GameRenderer(GameWorld world, int gameHeight, int midpointY) {
		this.world = world;
		this.midpointY = midpointY;

		try {
			InputProcessor proc = Gdx.input.getInputProcessor();
			InputHandler handler = (InputHandler) proc;
			menuButtons = handler.getMenuButtons();
		} catch (Throwable t) {
			t.printStackTrace();
		}
//		menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initGameObjects();
		initAssets();
		initTweens();
	}

	private void initGameObjects() {
		bird = world.getBird();
		scroller = world.getScroller();
		pipes = scroller.getPipes();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
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

	private void initTweens() {
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad).start(manager);
	}

	public void render(float delta, float runtime) {
		// Black bg prevents flickering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw Background color
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(0, 0, 136, midpointY + 66);

		// Draw Dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, midpointY + 77, 136, 52);
		shapeRenderer.end();

		batcher.begin();
		batcher.disableBlending();
		batcher.draw(bg, 0, midpointY + 23, 136, 43);

		drawGrass();
		drawPipes();

		batcher.enableBlending();
		drawSkulls();

		if (world.isRunning()) {
			drawBird(runtime);
			drawScore();
		} else if (world.isReady()) {
			drawBird(runtime);
			drawScore();
		} else if (world.isMenu()) {
			drawBirdCentered(runtime);
			drawMenuUI();
		} else if (world.isGameOver()) {
			drawBird(runtime);
			drawScore();
		} else if (world.isHighScore()) {
			drawBird(runtime);
			drawScore();
		}

		//		if (world.isReady()) {
		//			// Draw shadow first
		//			AssetLoader.shadow.draw(batcher, "Touch me", (136 / 2) - (42), 76);
		//			// Draw text
		//			AssetLoader.font.draw(batcher, "Touch me", (136 / 2) - (42 - 1), 75);
		//		} else {
		//
		//			if (world.isGameOver() || world.isHighScore()) {
		//
		//				if (world.isGameOver()) {
		//					AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
		//					AssetLoader.font.draw(batcher, "Game Over", 24, 55);
		//
		//					AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
		//					AssetLoader.font.draw(batcher, "High Score:", 22, 105);
		//
		//					String highScore = AssetLoader.getHighScore() + "";
		//
		//					// Draw shadow first
		//					AssetLoader.shadow.draw(batcher, highScore, (136 / 2) - (3 * highScore.length()), 128);
		//					// Draw text
		//					AssetLoader.font.draw(batcher, highScore, (136 / 2) - (3 * highScore.length() - 1), 127);
		//				} else {
		//					AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
		//					AssetLoader.font.draw(batcher, "High Score!", 18, 55);
		//				}
		//
		//				AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
		//				AssetLoader.font.draw(batcher, "Try again?", 24, 75);
		//
		//				// Convert integer into String
		//				String score = world.getScore() + "";
		//
		//				// Draw shadow first
		//				AssetLoader.shadow.draw(batcher, score, (136 / 2) - (3 * score.length()), 12);
		//				// Draw text
		//				AssetLoader.font.draw(batcher, score, (136 / 2) - (3 * score.length() - 1), 11);
		//
		//			}
		//		}

		batcher.end();
		drawTransition(delta);
	}

	private void drawGrass() {
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
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

	private void drawBirdCentered(float runtime) {
		batcher.draw(birdAnimation.getKeyFrame(runtime), 59, bird.getY() - 15, bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
	}

	private void drawMenuUI() {
		batcher.draw(AssetLoader.logo, 136 / 2 - 56, midpointY - 50, AssetLoader.logo.getRegionWidth() / 1.2f, AssetLoader.logo.getRegionHeight() / 1.2f);
		for (SimpleButton button : menuButtons) {
			button.draw(batcher);
		}
	}

	private void drawScore() {
		String score = Integer.toString(world.getScore());
		// Draw shadow first
		AssetLoader.shadow.draw(batcher, score, 68 - (3 * score.length()), midpointY - 82);
		AssetLoader.font.draw(batcher, score, 68 - (3 * score.length()), midpointY - 83);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1, 1, 1, alpha.getValue());
			shapeRenderer.rect(0, 0, 136, 300);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}

}
