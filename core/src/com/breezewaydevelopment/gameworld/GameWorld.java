package com.breezewaydevelopment.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.ScrollHandler;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;

	private int score = 0;
	
	private GameState currentState;

	public enum GameState {

	    READY, RUNNING, GAMEOVER

	}

	public GameWorld(int midpointY) {
		currentState = GameState.READY;
		bird = new Bird(33, midpointY - 5, 17, 12);
		scroller = new ScrollHandler(this, midpointY + 66);
		ground = new Rectangle(0, midpointY + 66, 136, 11);
	}

	public void updateRunning(float delta) {
		bird.update(delta);
		scroller.update(delta);

		if (scroller.collides(bird) && bird.isAlive()) { // Bird hits pipe
			stop(false);
		} else if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
			stop(true);
			currentState = GameState.GAMEOVER;
		}

	}

	public void update(float delta) {

        switch (currentState) {
        case READY:
            updateReady(delta);
            break;

        case RUNNING:
        default:
            updateRunning(delta);
            break;
        }

    }

    private void updateReady(float delta) {
        // Do nothing for now
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
