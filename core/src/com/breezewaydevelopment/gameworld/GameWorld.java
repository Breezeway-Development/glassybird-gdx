package com.breezewaydevelopment.gameworld;

import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.ScrollHandler;
import com.breezewaydevelopment.helpers.AssetLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld {

	public int midpointY;
	private int score = 0;

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;

	private GameState state;

	public GameWorld(int midpointY) {
		this.midpointY = midpointY;
		bird = new Bird(33, midpointY - 5, 17, 12);
		scroller = new ScrollHandler(this, midpointY + 66);
		ground = new Rectangle(0, midpointY + 66, 136, 11);
		state = GameState.READY;
	}

	public enum GameState {
		READY, RUNNING, GAMEOVER, HIGHSCORE
	}

	public void update(float delta) {
		switch (state) {
			case READY:
				ready(delta);
				break;
			case RUNNING:
				run(delta);
				break;
			default:
				break;
		}
	}

	private void ready(float delta) {
		// Nothing to see here
	}

	private void run(float delta) {
		bird.update(delta);
		scroller.update(delta);

		if (scroller.collides(bird) && bird.isAlive()) { // Bird hits pipe
			stop(false);
		} else if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
			stop(true);
			state = GameState.GAMEOVER;

			if (score > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score);
			}
		}
	}

	private void stop(boolean decel) {
		scroller.stop();
		if (bird.isAlive()) {
			bird.die();
		}
		bird.stop(decel);
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

	public boolean isReady() {
		return state == GameState.READY;
	}

	public boolean isGameOver() {
		return state == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return state == GameState.HIGHSCORE;
	}

	public void start() {
		state = GameState.RUNNING;
	}

	public void restart() {
		score = 0;
		bird.onRestart(midpointY - 5);
		scroller.onRestart();
		state = GameState.READY;
	}

}
