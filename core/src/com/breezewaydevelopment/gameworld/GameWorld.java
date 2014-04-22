package com.breezewaydevelopment.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.ScrollHandler;
import com.breezewaydevelopment.helpers.AssetLoader;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	
	private int score = 0;

	public GameWorld(int midpointY) {
		bird = new Bird(33, midpointY - 5, 17, 12);
		scroller = new ScrollHandler(this, midpointY + 66);
		ground = new Rectangle(0, midpointY + 66, 136, 11);
	}

	public void update(float delta) {
		bird.update(delta);
		scroller.update(delta);
		
		if (scroller.collides(bird) && bird.isAlive()) { // Bird hits pipe
			stop(false);
		} else if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            stop(true);
        }
		
	}

	public Bird getBird() {
		return bird;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int increment) {
		score += increment;
	}
	
	private void stop(boolean decel) {
		scroller.stop();
        if (bird.isAlive()) {
        	bird.die();
        }
        bird.stop(decel);
	}

}
