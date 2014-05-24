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
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Assets;
import com.breezewaydevelopment.helpers.Constants;
import com.breezewaydevelopment.helpers.HighScoreHandler;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.Grass;
import com.breezewaydevelopment.gameobjects.Pipe;
import com.breezewaydevelopment.gameobjects.ScrollHandler;

public class GameRenderer {

	private float midpointY;

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
	private Animation birdAnimation;
	public BitmapFont greenFont, shadow;
	private TextureRegion grass, birdMid, skullBottom, skullTop, bar;

	// Tween stuff
	private TweenManager manager;
	private MutableFloat alpha;
	private Color transitionColor;

	public GameRenderer(GameWorld world) {
		this.world = world;

		midpointY = Constants.MIDPOINT_Y;

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
		birdAnimation = Assets.birdAnimation;
		greenFont = Assets.greenFont;
		shadow = Assets.shadow;
		grass = Assets.grass;
		birdMid = Assets.bird;
		skullBottom = Assets.skullBottom;
		skullTop = Assets.skullTop;
		bar = Assets.bar;
	}

	private void drawGrass() {
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawPipes() {
		for (Pipe p : pipes) {
			drawRect(p.getBarBottom(), bar);
			drawRect(p.getSkullBottom(), skullBottom);
			drawRect(p.getBarTop(), bar);
			drawRect(p.getSkullTop(), skullTop);
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

	private void drawBird(float runtime) {
		float birdW = bird.getWidth(), birdH = bird.getHeight();
		batcher.draw(bird.shouldFlap() ? birdAnimation.getKeyFrame(runtime)
				: birdMid, bird.getX(), bird.getY(), birdW / 2.0f, birdH / 2.0f, birdW, birdH, 1, 1, bird.getRotation());
	}

	private void drawReady() {
		//		batcher.draw(ready, 36, midpointY + 50, 68, 14);
	}

	// TODO: Look into cool bitmap fonts
	// TODO: Constants for score rendering
	private void drawScore() {
		String score = Integer.toString(world.getScore());
		shadow.draw(batcher, score, 15, Constants.GAME_HEIGHT - 10);
		greenFont.draw(batcher, score, 15, Constants.GAME_HEIGHT - 10);
	}

	public void render(float delta, float runtime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//		shapeRenderer.begin(ShapeType.Filled);
		//		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		//		shapeRenderer.rect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		//		shapeRenderer.end();

		batcher.begin();
		batcher.enableBlending();
		drawPipes();
		batcher.disableBlending();
		drawGrass();
		batcher.enableBlending();

		switch (world.getState()) {
			case READY:
				drawBird(runtime);
				drawReady();
				break;
			case RUNNING:
				drawBird(runtime);
				drawScore();
				break;
			case GAMEOVER:
				drawBird(runtime);
				break;
			default:
				break;
		}
		batcher.end();

		drawTransition(delta);
		//drawPipeRect();
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

	//	private void drawPipeRect() {
	//		shapeRenderer.begin(ShapeType.Filled);
	//		for (Pipe p : pipes) {
	//			shapeRenderer.setColor(Color.BLACK);
	//			shapeRenderer.rect(p.getSkullBottom().getX(), p.getSkullBottom().getY(), p.getSkullBottom().getWidth(), p.getSkullBottom().getHeight());
	//			shapeRenderer.rect(p.getBarBottom().getX(), p.getBarBottom().getY(), p.getBarBottom().getWidth(), p.getBarBottom().getHeight());
	//			shapeRenderer.setColor(Color.WHITE);
	//			shapeRenderer.rect(p.getBarTop().getX(), p.getBarTop().getY(), p.getBarTop().getWidth(), p.getBarTop().getHeight());
	//			shapeRenderer.rect(p.getSkullTop().getX(), p.getSkullTop().getY(), p.getSkullTop().getWidth(), p.getSkullTop().getHeight());
	//		}
	//		shapeRenderer.end();
	//	}

}