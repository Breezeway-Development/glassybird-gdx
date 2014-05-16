package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class HighScoreHandler {
	
	private static Preferences prefs;

	private HighScoreHandler() {
		throw new AssertionError(HighScoreHandler.class);
	}

	public static void init() {
		
		prefs = Gdx.app.getPreferences("glassybird");
		if (!prefs.contains("highscore")) {
			setHighScore(0);
		}
	}
	
	public static void setHighScore(int score) {
		prefs.putInteger("highscore", score);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highscore");
	}

}