package com.breezewaydevelopment.gameobjects;

import com.breezewaydevelopment.helpers.Assets;
import com.breezewaydevelopment.gameworld.GameWorld;

public class ScrollHandler {

	private GameWorld gameWorld;
	private Grass firstGrass, secondGrass, thirdGrass, fourthGrass;
	private Pipe[] pipes = new Pipe[5];

	public ScrollHandler(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		firstGrass = new Grass(0);
		secondGrass = new Grass(firstGrass.getTailX());
		thirdGrass = new Grass(secondGrass.getTailX());
		fourthGrass = new Grass(thirdGrass.getTailX());

		for (int i = 0; i < pipes.length; i++) {
			// Here we define each pipe. If i = 0 start at x=210, otherwise start behind the previous pipe
			pipes[i] = new Pipe((i == 0 ? 210 : pipes[i - 1].getTailX()));
		}
	}

	public void updateReady(float delta) {

		firstGrass.update(delta);
		secondGrass.update(delta);
		thirdGrass.update(delta);
		fourthGrass.update(delta);

		// Same with grass
		if (firstGrass.isScrolledLeft()) {
			firstGrass.reset(fourthGrass.getTailX());
		} else if (secondGrass.isScrolledLeft()) {
			secondGrass.reset(firstGrass.getTailX());
		} else if (thirdGrass.isScrolledLeft()) {
			thirdGrass.reset(secondGrass.getTailX());
		} else if (fourthGrass.isScrolledLeft()) {
			fourthGrass.reset(thirdGrass.getTailX());
		}

	}

	public void update(float delta) {
		// Update our objects
		firstGrass.update(delta);
		secondGrass.update(delta);
		thirdGrass.update(delta);
		fourthGrass.update(delta);

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
		if (firstGrass.isScrolledLeft()) {
			firstGrass.reset(fourthGrass.getTailX());
		} else if (secondGrass.isScrolledLeft()) {
			secondGrass.reset(firstGrass.getTailX());
		} else if (thirdGrass.isScrolledLeft()) {
			thirdGrass.reset(secondGrass.getTailX());
		} else if (fourthGrass.isScrolledLeft()) {
			fourthGrass.reset(thirdGrass.getTailX());
		}
	}

	public void stop() {
		firstGrass.stop();
		secondGrass.stop();
		thirdGrass.stop();
		fourthGrass.stop();
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

	public Grass getFirstGrass() {
		return firstGrass;
	}

	public Grass getSecondGrass() {
		return secondGrass;
	}

	public Grass getThirdGrass() {
		return thirdGrass;
	}
	
	public Grass getFourthGrass() {
		return fourthGrass;
	}
	
	public Pipe[] getPipes() {
		return pipes;
	}

	public void onRestart() {

		firstGrass.onRestart(0);
		secondGrass.onRestart(firstGrass.getTailX());
		thirdGrass.onRestart(secondGrass.getTailX());
		fourthGrass.onRestart(thirdGrass.getTailX());
		for (int i = 0; i < pipes.length; i++) {
			// Same thing as pipes setup in our constructor
			pipes[i].onRestart((i == 0 ? 210 : pipes[i - 1].getTailX()));
		}
	}

}
