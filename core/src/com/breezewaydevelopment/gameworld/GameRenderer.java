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
import com.breezewaydevelopment.helpers.PreferencesHandler;
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

	// All of this just for volume adjustment
	private MutableFloat volumeAlpha;
	private TweenManager volumeManager;
	private BitmapFont volumeFont;

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
		volumeAlpha = new MutableFloat(0);
		initTransition(0, 0, 0, .5f);
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
		volumeFont = Assets.volumeFont;
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
		drawString("Highscore: " + PreferencesHandler.getHighScore(), Grass.GRASS_HEIGHT + font.getCapHeight() * 2);
	}

	private void drawRetry() {
		drawString("Tap to Retry", Constants.MIDPOINT_Y + font.getCapHeight());
		drawString("Highscore: " + PreferencesHandler.getHighScore(), Grass.GRASS_HEIGHT + font.getCapHeight() * 2);
	}

	private void drawScore() {
		drawString(Integer.toString(world.getScore()));
	}

	private void drawVolume() {
		volumeFont.setColor(1, 1, 1, volumeAlpha.floatValue());
		drawString("Volume " + (PreferencesHandler.getVolume() ? "On" : "Off"), Constants.MIDPOINT_Y + volumeFont.getCapHeight(), volumeFont);
	}

	private void drawString(String str) {
		drawString(str, Constants.GAME_HEIGHT - font.getCapHeight());
	}

	private void drawString(String str, float y) {
		drawString(str, y, font);
	}

	private void drawString(String str, float y, BitmapFont font) {
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
		drawVolume();
		batcher.disableBlending();
		drawGrass();
		batcher.end();

		if (volumeAlpha.floatValue() != 0) {
			updateVolumeAlpha(delta);
		}
		drawTransition(delta);
	}

	public void volumeToggle() {
		volumeAlpha.setValue(1);
		Tween.registerAccessor(MutableFloat.class, volumeAlpha);
		volumeManager = new TweenManager();
		Tween.to(volumeAlpha, -1, 0.75f).target(0).ease(TweenEquations.easeInQuad).start(volumeManager);
	}

	public void updateVolumeAlpha(float delta) {
		volumeManager.update(delta);
	}

	public void initTransition(float r, float g, float b, float duration) {
		transitionColor.set(r, g, b, 1);
		alpha.setValue(1);
		volumeAlpha.setValue(0);
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