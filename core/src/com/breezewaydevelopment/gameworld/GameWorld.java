package com.breezewaydevelopment.gameworld;

import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.ScrollHandler;
import com.breezewaydevelopment.helpers.AssetLoader;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	
	private boolean isAlive = true;

	public GameWorld(int midpointY) {
		bird = new Bird(33, midpointY - 5, 17, 12);
		scroller = new ScrollHandler(midpointY + 66);
	}

	public void update(float delta) {
		bird.update(delta);
		scroller.update(delta);
		
		if (isAlive && scroller.collides(bird)) {
			scroller.stop();
			AssetLoader.dead.play();
			isAlive = false;
		}
	}

	public Bird getBird() {
		return bird;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

}
