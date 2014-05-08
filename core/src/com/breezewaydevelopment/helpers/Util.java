package com.breezewaydevelopment.helpers;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class Util {

	private static Preferences prefs;
	private static Random r;
	
	private Util() {
		throw new AssertionError(Util.class);
	}

	public static void init() {
		r = new Random();
		
		prefs = Gdx.app.getPreferences("glassybird");
		if (!prefs.contains("highscore")) {
			setHighScore(0);
		}
		
		Assets.load();
		Constants.load();
	}
	
	public static void exit() {
		Assets.dispose();
	}

	public static void setHighScore(int score) {
		prefs.putInteger("highscore", score);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highscore");
	}
	
	public static int genPipeHeight() {
		return r.nextInt(90) + 15;
	}
	
}