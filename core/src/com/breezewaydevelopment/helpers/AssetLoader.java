package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture, splashTexture;
	public static TextureRegion splash, logo, bg, grass, bird, birdDown, birdUp,
			skullUp, skullDown, bar, playButtonUp, playButtonDown, ready,
			gameOver, highScore, scoreboard, star, noStar, retry;
	public static Animation birdAnimation;
	public static Sound dead, flap, coin, fall;
	public static BitmapFont greenFont, shadow, whiteFont;
	private static Preferences prefs;

	public static void load() {

		splashTexture = new Texture(Gdx.files.internal("data/logo.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		splash = new TextureRegion(splashTexture, 0, 0, 512, 114);

		playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
		playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);

		ready = new TextureRegion(texture, 59, 83, 34, 7);
		ready.flip(false, true);
		retry = new TextureRegion(texture, 59, 110, 33, 7);
		retry.flip(false, true);
		gameOver = new TextureRegion(texture, 59, 92, 46, 7);
		gameOver.flip(false, true);
		scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
		scoreboard.flip(false, true);
		star = new TextureRegion(texture, 152, 70, 10, 10);
		star.flip(false, true);
		noStar = new TextureRegion(texture, 165, 70, 10, 10);
		noStar.flip(false, true);
		highScore = new TextureRegion(texture, 59, 101, 48, 7);
		highScore.flip(false, true);

		logo = new TextureRegion(texture, 0, 55, 135, 24);
		logo.flip(false, true);

		bg = new TextureRegion(texture, 0, 0, 136, 43);
		bg.flip(false, true);
		grass = new TextureRegion(texture, 0, 43, 143, 11);
		grass.flip(false, true);

		bird = new TextureRegion(texture, 153, 0, 17, 12);
		bird.flip(false, true);
		birdUp = new TextureRegion(texture, 170, 0, 17, 12);
		birdUp.flip(false, true);
		birdDown = new TextureRegion(texture, 136, 0, 17, 12);
		birdDown.flip(false, true);
		birdAnimation = new Animation(0.06f, new TextureRegion[] { birdDown, bird, birdUp }); //.06 sec flapping anim
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG); // Back and forth

		skullUp = new TextureRegion(texture, 192, 0, 24, 14);
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false, true); // Flip y from skullUp
		bar = new TextureRegion(texture, 136, 16, 22, 3);
		bar.flip(false, true);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));

		greenFont = new BitmapFont(Gdx.files.internal("data/greentext.fnt"));
		greenFont.setScale(.25f, -.25f);
		whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
		whiteFont.setScale(.1f, -.1f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);

		prefs = Gdx.app.getPreferences("glassybird");
		if (!prefs.contains("highscore")) {
			setHighScore(0);
		}
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highscore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highscore");
	}

	public static void dispose() {
		texture.dispose();
		splashTexture.dispose();

		dead.dispose();
		flap.dispose();
		coin.dispose();
		fall.dispose();

		greenFont.dispose();
		whiteFont.dispose();
		shadow.dispose();
	}

}