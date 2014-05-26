package com.breezewaydevelopment.gameobjects;

import com.breezewaydevelopment.helpers.Assets;
import com.breezewaydevelopment.helpers.Constants;
import com.breezewaydevelopment.gameworld.GameWorld;

public class ScrollHandler {

	private GameWorld gameWorld;
	private Grass frontGrass, backGrass;
	private Pipe[] pipes = new Pipe[4];

	float runtime = 0;

	public ScrollHandler(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		frontGrass = new Grass(0);
		backGrass = new Grass(frontGrass.getTailX());

		for (int i = 0; i < pipes.length; i++) {
			// Here we define each pipe. If i = 0 start at x=210, otherwise start behind the previous pipe
			pipes[i] = new Pipe((i == 0 ? Constants.GAME_WIDTH + Pipe.GAP
					: pipes[i - 1].getTailX()));
		}
	}

	public void updateReady(float delta) {
		frontGrass.update(delta);
		backGrass.update(delta);

		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());
		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());
		}

	}

	public void update(float delta) {
		runtime += delta;
		frontGrass.update(delta);
		backGrass.update(delta);

		// Update and reset pipes
		int i = 0;
		for (Pipe p : pipes) {
			p.update(delta);
			if (p.isScrolledLeft()) {
				// If a pipe is scrolled left, reset it past the farthest pipe (1 -> 3, 2 -> 1, 3 -> 2)
				p.reset(pipes[(i == 0 ? pipes.length : i) - 1].getTailX());
			}
			i++;
		}

		// Same with grass
		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());
		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());
		}
	}

	public void stop() {
		frontGrass.stop();
		backGrass.stop();
		for (Pipe p : pipes) {
			p.stop();
		}
	}

	public boolean collides(Bird bird) {
		float birdTip = bird.getX() + bird.getWidth();
		for (Pipe p : pipes) {
			if (!p.isScored() && p.getX() + (p.getWidth() / 2) < birdTip) { // If the tip of the bird crosses the middle of the pipe
				gameWorld.addScore();
				p.setScored(true);
				Assets.coin.play();
			}
			if (p.collides(bird)) {
				return true;
			}
		}
		return false;
	}

	public Grass getFrontGrass() {
		return frontGrass;
	}

	public Grass getBackGrass() {
		return backGrass;
	}

	public Pipe[] getPipes() {
		return pipes;
	}

	public void onRestart() {
		frontGrass.onRestart(0);
		backGrass.onRestart(frontGrass.getTailX());
		Pipe.resetHole();
		for (int i = 0; i < pipes.length; i++) {
			// Same thing as pipes setup in our constructor
			pipes[i].onRestart((i == 0 ? Constants.GAME_WIDTH + Pipe.GAP
					: pipes[i - 1].getTailX()));
		}
	}

}