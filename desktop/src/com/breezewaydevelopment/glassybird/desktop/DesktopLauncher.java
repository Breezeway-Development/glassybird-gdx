package com.breezewaydevelopment.glassybird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.breezewaydevelopment.glassybird.GBGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Glassy Bird";
		config.useGL30 = false;
		config.width = 1080 / 3;
		config.height = 1920 / 3;
//		config.width = 1280 / 3;
//		config.height = 720 / 3;

		new LwjglApplication(new GBGame(), config);
	}
}
