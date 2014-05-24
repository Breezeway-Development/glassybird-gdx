package com.breezewaydevelopment.glassybird.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.breezewaydevelopment.glassybird.GBGame;
import com.google.android.glass.view.WindowUtils;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		getWindow().addFlags(WindowUtils.FLAG_DISABLE_HEAD_GESTURES);
		initialize(new GBGame(), config);
	}
}
