package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.audio.Sound;

public final class SoundHandler {

	public SoundHandler() {
		throw new AssertionError(SoundHandler.class);
	}

	private static void play(Sound snd) {
		if (PreferencesHandler.getVolume()) {
			snd.play();
		}
	}

	public static void playCoin() {
		play(Assets.coin);
	}

	public static void playDead() {
		play(Assets.dead);
	}

	public static void playFall() {
		play(Assets.fall);
	}

	public static void playFlap() {
		play(Assets.flap);
	}

}
