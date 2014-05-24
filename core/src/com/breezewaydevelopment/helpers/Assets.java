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
import com.badlogic.gdx.utils.Disposable;

public final class Assets {

	//	public static Texture texture, splashTexture;
	//	public static TextureRegion splash, logo, grass, bird, birdDown, birdUp,
	//			skullBottom, skullTop, bar;

	private static TextureAtlas atlas;
	public static AtlasRegion bar, bird, birdDown, birdUp, grass, ring, splash;

	public static Animation birdAnimation;
	public static BitmapFont font;
	public static Sound dead, flap, coin, fall;

	private static List<Disposable> disposables;

	private Assets() {
		throw new AssertionError(Assets.class);
	}

	public static void load() {
		disposables = new ArrayList<Disposable>();
		loadTextures();
		loadAnim();
		loadSounds();
		loadFonts();
	}

	public static void dispose() {
		for (Disposable d : disposables) {
			d.dispose();
		}
	}

	private static void loadTextures() {
		atlas = new TextureAtlas(Gdx.files.internal("data/textures.pack"));

		bar = atlas.findRegion("bar");
		bird = atlas.findRegion("bird");
		birdDown = atlas.findRegion("bird-down");
		birdUp = atlas.findRegion("bird-up");
		grass = atlas.findRegion("grass");
		ring = atlas.findRegion("ring");
		splash = atlas.findRegion("splash");
		// splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		disposables.add(atlas);
	}

	private static void loadAnim() {
		birdAnimation = new Animation(0.1f, new TextureRegion[] { birdDown, bird, birdUp });
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG); // Back and forth
	}

	private static void loadSounds() {
		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));

		disposables.add(dead);
		disposables.add(flap);
		disposables.add(coin);
		disposables.add(fall);
	}

	private static void loadFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Roboto-Light.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 64;
		parameter.characters = "aFlopTt1234567890";

		font = generator.generateFont(parameter);
		font.setScale(0.25f);
		font.setColor(1, 1, 1, 1);
		disposables.add(generator);
	}

}
