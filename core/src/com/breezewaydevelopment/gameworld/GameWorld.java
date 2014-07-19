package com.breezewaydevelopment.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.breezewaydevelopment.helpers.Constants;
import com.breezewaydevelopment.helpers.PreferencesHandler;
import com.breezewaydevelopment.helpers.SoundHandler;
import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.Grass;
import com.breezewaydevelopment.gameobjects.ScrollHandler;

public class GameWorld {

	private int score;
	private long currentTapTime;

	private Rectangle ground;

	private Bird bird;
	private ScrollHandler scroller;
	private GameRenderer renderer;
	private GameState currentState;

	public enum GameState {
		READY, RUNNING, GAMEOVER
	}

	public GameWorld() {
		bird = new Bird();
		scroller = new ScrollHandler(this);
		ground = new Rectangle(0, 0, Constants.GAME_WIDTH, Grass.GRASS_HEIGHT);

		setState(GameState.READY);
	}

	public void update(float delta) {
		updateVolume();
		switch (currentState) {
			case READY:
				updateReady(delta);
				break;
			case RUNNING:
				updateRunning(delta);
				break;
			case GAMEOVER:
				updateGameover(delta);
			default:
				break;
		}
	}

	/* 450ms long-tap toggles volume */
	private void updateVolume() {
		if (currentTapTime != 0 && System.currentTimeMillis() - currentTapTime >= 450) {
			PreferencesHandler.setVolume(!PreferencesHandler.getVolume());
			currentTapTime = 0;
			renderer.volumeToggle();
		}
	}

	private void updateReady(float delta) {
		bird.updateReady(delta);
		scroller.updateReady(delta);
	}

	private void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}
		bird.update(delta);
		scroller.update(delta);

		if (bird.isAlive() && scroller.handlePipes(bird)) { // Bird hits pipe
			SoundHandler.playFall();
			stop(false);
		} else if (bird.getY() - bird.getWidth() <= ground.getY() && Intersector.overlaps(bird.getBoundingCircle(), ground)) { // Bird hits ground
			stop(true);
		}
	}

	private void updateGameover(float delta) {
		bird.updateGameover(delta);
	}

	private void stop(boolean ground) {
		scroller.stop();
		if (bird.isAlive()) {
			SoundHandler.playDead();
			renderer.initTransition(0, 0, 0, .3f);
		}
		bird.stop(ground); // Let it fall if it hits a pipe

		if (ground) {
			if (score > PreferencesHandler.getHighScore()) {
				PreferencesHandler.setHighScore(score);
			}
			setState(GameState.GAMEOVER);
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

	public void addScore() {
		score++;
	}

	public void restart() {
		score = 0;
		bird.onRestart();
		scroller.onRestart();
		setState(GameState.READY);
	}

	public GameState getState() {
		return currentState;
	}

	public void setState(GameState state) {
		if (renderer != null && state == GameState.READY) {
			renderer.initTransition(0, 0, 0, 0.5f);
		}
		currentState = state;
	}

	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

	public void onTap() {
		currentTapTime = System.currentTimeMillis();
	}

	public void onRelease() {
		currentTapTime = 0;
	}

}