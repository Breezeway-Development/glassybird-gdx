package com.breezewaydevelopment.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.AssetLoader;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.ScrollHandler;

public class GameWorld {

	private int score = 0;
	private float runTime = 0;
	private int midpointY;

	private Rectangle ground;

	private Bird bird;
	private ScrollHandler scroller;
	private GameRenderer renderer;
	private GameState currentState;

	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
	}

	public GameWorld(int midpointY) {
		this.midpointY = midpointY;
		bird = new Bird(33, midpointY - 5, 17, 12);
		scroller = new ScrollHandler(this, midpointY + 66); // The grass should start 66 pixels below the midpointY
		ground = new Rectangle(0, midpointY + 66, 137, 11);

		currentState = GameState.MENU;
	}

	public void update(float delta) {
		runTime += delta;
		switch (currentState) {
			case MENU:
			case READY:
				updateReady(delta);
				break;
			case RUNNING:
				updateRunning(delta);
				break;
			default:
				break;
		}

	}

	private void updateReady(float delta) {
		bird.updateReady(runTime);
		scroller.updateReady(delta);
	}

	private void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		scroller.update(delta);

		if (bird.isAlive() && scroller.collides(bird)) { // Bird hits pipe
			AssetLoader.fall.play();
			stop(false);
		} else if (Intersector.overlaps(bird.getBoundingCircle(), ground)) { // Bird hits ground
			stop(true);

		}
	}

	private void stop(boolean ground) {
		scroller.stop();
		if (bird.isAlive()) {
			AssetLoader.dead.play();
			renderer.initTransition(255, 255, 255, .3f);
		}
		bird.stop(ground); // Let it fall if it hits a pipe

		if (ground) {
			currentState = GameState.GAMEOVER;
			if (score > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score);
				currentState = GameState.HIGHSCORE;
			}
		}
	}

	public Bird getBird() {
		return bird;

	}

	public int getMidPointY() {
		return midpointY;
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

	public void start() {
		currentState = GameState.RUNNING;
	}

	public void ready() {
		currentState = GameState.READY;
		renderer.initTransition(0, 0, 0, 1f);
	}

	public void restart() {
		score = 0;
		bird.onRestart(midpointY - 5);
		scroller.onRestart();
		ready();
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

}
