package com.breezewaydevelopment.glassybird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.breezewaydevelopment.glassybird.GBGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Glassy Bird";
		config.useGL30 = false;
		//		config.width = 1088 / 4; // 272 (divisible by 136)
		//		config.height = 1656 / 2; // 414 (480-66)
		config.width = 1920 / 3; // 640
		config.height = 1080 / 3; // 360
		new LwjglApplication(new GBGame(), config);
	}
}
