package com.breezewaydevelopment.glassybird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.breezewaydevelopment.glassybird.GBGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Glassy Bird";
		config.useGL30 = false;
		config.width = 272;
		config.height = 408;
//		config.width = 640;
//		config.height = 360;

		new LwjglApplication(new GBGame(), config);
	}
}
