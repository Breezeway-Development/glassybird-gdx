package com.breezewaydevelopment.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.breezewaydevelopment.glassybird.GBGame;
import com.breezewaydevelopment.helpers.Assets;
import com.breezewaydevelopment.helpers.Constants;
import com.breezewaydevelopment.tweenaccessors.SpriteAccessor;

public class SplashScreen implements Screen {

	// TODO: Improve the splash image

	private TweenManager manager;
	private SpriteBatch batcher;
	private Sprite sprite;
	private GBGame game;

	public SplashScreen(GBGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		Assets.load();
		Constants.calc();

		sprite = new Sprite(Assets.splash);
		sprite.setColor(0, 0, 0, 0);

		float scale = Constants.SCREEN_WIDTH / sprite.getWidth();
		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);

		sprite.setPosition((Constants.SCREEN_WIDTH / 2) - (sprite.getWidth() / 2), (Constants.SCREEN_HEIGHT / 2) - (sprite.getHeight() / 2));
		batcher = new SpriteBatch();
		setupTween();
	}

	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		manager = new TweenManager();
		TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				game.setScreen(new GameScreen());
			}
		};
		//1.3 sec transition with .3 sec pause in the middle from alpha 0 -> 1 -> 0
		Tween.to(sprite, SpriteAccessor.ALPHA, 1.5f).target(1).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.45f).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
	}

	@Override
	public void render(float delta) {
		manager.update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
		sprite.draw(batcher);
		batcher.end();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
