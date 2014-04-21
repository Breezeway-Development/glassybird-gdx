package com.breezewaydevelopment.gameworld;

import com.breezewaydevelopment.gameobjects.Bird;

public class GameWorld {
	
	private Bird bird;
	
	public GameWorld(int midpointY) {
		bird = new Bird(33, midpointY - 5, 17, 12);
	}
	
	public void update(float delta) {
		bird.update(delta);
    }
	
	public Bird getBird() {
		return bird;
	}

}
