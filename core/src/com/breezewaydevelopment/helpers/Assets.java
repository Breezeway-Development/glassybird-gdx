package com.breezewaydevelopment.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public final class Assets {

	public static Texture texture, splashTexture;
	public static TextureRegion splash, logo, grass, bird, birdDown, birdUp,
			skullUp, skullDown, bar, playButtonUp, playButtonDown, ready,
			gameOver, highScore, scoreboard, star, noStar, retry;
	public static Animation birdAnimation;
	public static BitmapFont greenFont, shadow, whiteFont;
	public static Sound dead, flap, coin, fall;

	private static List<Disposable> disposables;

	private Assets() {
		throw new AssertionError(Assets.class);
	}

	public static void load() {
		disposables = new ArrayList<Disposable>();
		loadTextures();
		loadTextureRegions();
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
		splashTexture = new Texture(Gdx.files.internal("data/logo.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		disposables.add(splashTexture);
		disposables.add(texture);
	}

	private static void loadTextureRegions() {
		// Logos
		logo = new TextureRegion(texture, 0, 55, 135, 24);
		logo.flip(false, true);
		splash = new TextureRegion(splashTexture, 0, 0, 512, 114);

		// UI
		ready = new TextureRegion(texture, 59, 83, 34, 7);
		retry = new TextureRegion(texture, 59, 110, 33, 7);
		gameOver = new TextureRegion(texture, 59, 92, 46, 7);
		scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
		star = new TextureRegion(texture, 152, 70, 10, 10);
		noStar = new TextureRegion(texture, 165, 70, 10, 10);
		highScore = new TextureRegion(texture, 59, 101, 48, 7);
		playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
		playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);

		// Game
		grass = new TextureRegion(texture, 0, 43, 143, 11);
		bird = new TextureRegion(texture, 153, 0, 17, 12);
		birdUp = new TextureRegion(texture, 170, 0, 17, 12);
		birdDown = new TextureRegion(texture, 136, 0, 17, 12);
		bar = new TextureRegion(texture, 136, 16, 22, 3);
		skullUp = new TextureRegion(texture, 192, 0, 24, 14);
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false, true); // Flip y from skullUp
	}

	private static void loadAnim() {
		birdAnimation = new Animation(0.06f, new TextureRegion[] { birdDown, bird, birdUp }); //.06 sec flapping anim
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
		greenFont = new BitmapFont(Gdx.files.internal("data/greentext.fnt"));
		greenFont.setScale(.25f, .25f);
		whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
		whiteFont.setScale(.1f, .1f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, .25f);

		disposables.add(greenFont);
		disposables.add(whiteFont);
		disposables.add(shadow);
	}

}
