package com.breezewaydevelopment.helpers;

import java.util.concurrent.Executors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class PreferencesHandler {

	private static boolean soundOn;
	private static int highscore;
	private static Preferences prefs;

	private PreferencesHandler() {
		throw new AssertionError(PreferencesHandler.class);
	}

	public static void init() {
		prefs = Gdx.app.getPreferences("glassybird");

		if (!prefs.contains("highscore")) {
			setHighScore(0);
		} else {
			highscore = prefs.getInteger("highscore");
		}

		if (!prefs.contains("volume")) {
			setVolume(true);
		} else {
			soundOn = prefs.getBoolean("volume");
		}
	}

	/* concurrent read/write so it doesn't lag up the game */
	public static void setHighScore(int score) {
		highscore = score;
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				prefs.putInteger("highscore", highscore);
				prefs.flush();
			}
		});
	}

	public static int getHighScore() {
		return highscore;
	}

	/* concurrent read/write so it doesn't lag up the game */
	public static void setVolume(boolean on) {
		soundOn = on;
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				prefs.putBoolean("volume", soundOn);
				prefs.flush();
			}
		});
	}

	public static boolean getVolume() {
		return soundOn;
	}

}