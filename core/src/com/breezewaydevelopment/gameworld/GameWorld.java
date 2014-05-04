package com.breezewaydevelopment.gameworld;

import com.breezewaydevelopment.gameobjects.Bird;
import com.breezewaydevelopment.gameobjects.ScrollHandler;
import com.breezewaydevelopment.helpers.AssetLoader;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld {

	public int midpointY;
	private int score = 0;
	private float runtime = 0;

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;

	private GameState state;

	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
	}

	public GameWorld(int midpointY) {
		this.midpointY = midpointY;
		bird = new Bird(33, midpointY - 5, 17, 12);
		scroller = new ScrollHandler(this, midpointY + 66);
		ground = new Rectangle(0, midpointY + 66, 136, 11);
		state = GameState.MENU;
	}

	public void update(float delta) {
		runtime += delta;
		switch (state) {
			case MENU:
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
		bird.updateReady(runtime);
		scroller.updateReady(delta);
	}

	private void run(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		scroller.update(delta);

		if (scroller.collides(bird) && bird.isAlive()) { // Bird hits pipe
			stop(false);
		} else if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
			stop(true);
			state = GameState.GAMEOVER;

			if (score > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score);
				state = GameState.HIGHSCORE;
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
	
	public int getMidpointY() {
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
      state = GameState.RUNNING;
  }

  public void ready() {
      state = GameState.READY;
  }

  public void restart() {
      score = 0;
      bird.onRestart(midpointY - 5);
      scroller.onRestart();
      state = GameState.READY;
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

  public boolean isMenu() {
      return state == GameState.MENU;
  }

  public boolean isRunning() {
      return state == GameState.RUNNING;
  }

}
