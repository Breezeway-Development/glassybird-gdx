package com.breezewaydevelopment.gameworld;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.primitives.MutableFloat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Assets;
import com.breezewaydevelopment.helpers.Constants;
import com.breezewaydevelopment.helpers.HighScoreHandler;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.Grass;
import com.breezewaydevelopment.gameobjects.Pipe;
import com.breezewaydevelopment.gameobjects.ScrollHandler;

public class GameRenderer {

	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe[] pipes;

	// Game Assets
	private Animation birdReady, birdRunning;
	private BitmapFont font;
	private TextureRegion grass, birdMid, ringBottom, ringTop, bar;

	// Tween stuff
	private TweenManager manager;
	private MutableFloat alpha;
	private Color transitionColor;

	public GameRenderer(GameWorld world) {
		this.world = world;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initGameObjects();
		initAssets();

		transitionColor = new Color();
		alpha = new MutableFloat(1);
		initTransition(1, 1, 1, .5f);
	}

	private void initGameObjects() {
		bird = world.getBird();
		scroller = world.getScroller();
		pipes = scroller.getPipes();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
	}

	private void initAssets() {
		birdReady = Assets.birdReady;
		birdRunning = Assets.birdRunning;
		font = Assets.font;
		grass = Assets.grass;
		birdMid = Assets.bird;
		ringBottom = Assets.ring;
		ringTop = new TextureRegion(ringBottom);
		ringTop.flip(false, true);
		bar = Assets.bar;
	}

	private void drawGrass() {
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawPipes() {
		for (Pipe p : pipes) {
			drawRect(p.getBarBottom(), bar);
			drawRect(p.getRingBottom(), ringBottom);
			drawRect(p.getBarTop(), bar);
			drawRect(p.getRingTop(), ringTop);
		}
	}

	private void drawRect(Rectangle r, TextureRegion texture) {
		batcher.draw(texture, r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	/*
	 * draw(x, y, originX, originY, width, height, scaleX, scaleY, rotation) The
	 * rectangle is offset by originX, originY relative to the origin. Scale
	 * specifies the scaling factor by which the rectangle should be scaled
	 * around originX, originY. Rotation specifies the angle of counter clockwise
	 * rotation of the rectangle around originX, originY.
	 */

	private void drawBird(boolean running) {
		if (bird.shouldFlap()) {
			if (running) {
				drawBird(birdRunning.getKeyFrame(bird.getRuntime()));
			} else {
				drawBird(birdReady.getKeyFrame(bird.getRuntime()));
			}
		} else {
			drawBird(birdMid);
		}
	}

	private void drawBird(TextureRegion birdTexture) {
		float birdW = bird.getWidth(), birdH = bird.getHeight();
		batcher.draw(birdTexture, bird.getX(), bird.getY(), birdW / 2.0f, birdH / 2.0f, birdW, birdH, 1, 1, bird.getRotation());
	}

	private void drawReady() {
		drawString("Tap to Flap");
		drawString("Highscore: " + HighScoreHandler.getHighScore(), Grass.GRASS_HEIGHT + font.getCapHeight() * 2);
	}

	private void drawRetry() {
		drawString("Tap to Retry", Grass.GRASS_HEIGHT + font.getCapHeight() * 5);
		drawString("Highscore: " + HighScoreHandler.getHighScore(), Grass.GRASS_HEIGHT + font.getCapHeight() * 2);
	}

	private void drawScore() {
		drawString(Integer.toString(world.getScore()));
	}

	private void drawString(String str) {
		drawString(str, Constants.GAME_HEIGHT - font.getCapHeight());
	}

	private void drawString(String str, float y) {
		font.draw(batcher, str, Constants.MIDPOINT_X - (font.getBounds(str).width / 2), y);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//		shapeRenderer.begin(ShapeType.Filled);
		//		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		//		shapeRenderer.rect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		//		shapeRenderer.end();

		//		drawBirdCirc();
		//		drawPipeRect();

		batcher.begin();
		batcher.enableBlending();
		drawPipes();
		switch (world.getState()) {
			case READY:
				drawBird(false);
				drawReady();
				break;
			case RUNNING:
				drawBird(true);
				drawScore();
				break;
			case GAMEOVER:
				drawBird(false);
				drawScore();
				drawRetry();
				break;
			default:
				break;
		}
		batcher.disableBlending();
		drawGrass();
		batcher.end();

		drawTransition(delta);
	}

	public void initTransition(float r, float g, float b, float duration) {
		transitionColor.set(r, g, b, 1);
		alpha.setValue(1);
		Tween.registerAccessor(MutableFloat.class, alpha);
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.floatValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g, transitionColor.b, alpha.floatValue());
			shapeRenderer.rect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}

	private void drawPipeRect() {
		shapeRenderer.begin(ShapeType.Filled);
		for (Pipe p : pipes) {
			shapeRenderer.setColor(Color.CYAN);
			shapeRenderer.rect(p.getRingBottom().getX(), p.getRingBottom().getY(), p.getRingBottom().getWidth(), p.getRingBottom().getHeight());
			shapeRenderer.rect(p.getBarBottom().getX(), p.getBarBottom().getY(), p.getBarBottom().getWidth(), p.getBarBottom().getHeight());
			shapeRenderer.setColor(Color.BLUE);
			shapeRenderer.rect(p.getBarTop().getX(), p.getBarTop().getY(), p.getBarTop().getWidth(), p.getBarTop().getHeight());
			shapeRenderer.rect(p.getRingTop().getX(), p.getRingTop().getY(), p.getRingTop().getWidth(), p.getRingTop().getHeight());
		}
		shapeRenderer.end();
	}

	private void drawBirdCirc() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		Circle circ = bird.getBoundingCircle();
		shapeRenderer.circle(circ.x, circ.y, circ.radius);
		shapeRenderer.end();
	}

}