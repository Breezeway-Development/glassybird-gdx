package com.breezewaydevelopment.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public final class Assets {

	public static Animation birdReady, birdRunning;
	public static AtlasRegion bar, bird, birdDown, birdUp, grass, ring, splash;
	public static BitmapFont font, volumeFont;
	public static Sound dead, flap, coin, fall;

	private static List<Disposable> disposables;
	private static TextureAtlas atlas;

	private Assets() {
		throw new AssertionError(Assets.class);
	}

	public static void load() {
		disposables = new ArrayList<Disposable>();

		atlas = new TextureAtlas(Gdx.files.internal("data/textures.pack"));
		bar = atlas.findRegion("bar");
		bird = atlas.findRegion("bird");
		birdDown = atlas.findRegion("bird-down");
		birdUp = atlas.findRegion("bird-up");
		grass = atlas.findRegion("grass");
		ring = atlas.findRegion("ring");
		splash = atlas.findRegion("splash");
		disposables.add(atlas);

		Array<TextureRegion> birds = new Array<TextureRegion>(new TextureRegion[] { birdDown, bird, birdUp });
		birdReady = new Animation(0.15f, birds, Animation.PlayMode.LOOP_PINGPONG);
		birdRunning = new Animation(0.07f, birds, Animation.PlayMode.LOOP_PINGPONG);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));
		disposables.add(dead);
		disposables.add(flap);
		disposables.add(coin);
		disposables.add(fall);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Roboto-Light.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 96;
		//		parameter.characters = "aFlopTt1234567890";
		font = generator.generateFont(parameter);
		font.setScale(0.15f);
		font.setColor(1, 1, 1, 1);
		volumeFont = generator.generateFont(parameter);
		volumeFont.setScale(0.15f);
		volumeFont.setColor(1, 1, 1, 0);

		disposables.add(generator);

		PreferencesHandler.init();
	}

	public static void dispose() {
		for (Disposable d : disposables) {
			d.dispose();
		}
	}

}
