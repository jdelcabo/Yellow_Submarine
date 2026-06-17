package com.mygdx.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

//Game launcher
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Settings configuration
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		//Disable mobile functions to thrift battery
		config.useAccelerometer = false;
		config.useCompass = false;

		//Initialize game with selected configuration
		initialize(new GamePage(), config);

	}
}
