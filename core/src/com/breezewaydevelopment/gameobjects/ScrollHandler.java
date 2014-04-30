package com.breezewaydevelopment.gameobjects;

import com.breezewaydevelopment.gameworld.GameWorld;
import com.breezewaydevelopment.helpers.AssetLoader;

public class ScrollHandler {

	public static final int SCROLL_SPEED = -59; // How fast we need to scroll
	public static final int PIPE_GAP = 49; // Gap between the pipes

	private GameWorld world;
	private Grass frontGrass, secondGrass, thirdGrass, fourthGrass;
	private Pipe[] pipes = new Pipe[5];

	public ScrollHandler(GameWorld world, float yPos) { // Where to begin our grass and pipe objects
		this.world = world;
		frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		secondGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);
		thirdGrass = new Grass(secondGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);
		fourthGrass = new Grass(thirdGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);

		for (int i = 0; i < pipes.length; i++) {
			// Here we define each pipe. If i = 0 start at x=210, otherwise start behind the previous pipe
			pipes[i] = new Pipe(i == 0 ? 330 : pipes[i - 1].getTailX() + PIPE_GAP, 0, 22, 60 + (i * 10), SCROLL_SPEED, yPos);
		}
	}

	public void update(float delta) {
		// Update our objects
		frontGrass.update(delta);
		secondGrass.update(delta);
		thirdGrass.update(delta);
		fourthGrass.update(delta);

		// Update and reset pipes
		int i = 0;
		for (Pipe p : pipes) { // for-each loop
			p.update(delta);
			if (p.isScrolledLeft()) {
				// If a pipe is scrolled left, reset it past the farthest pipe
				p.reset(pipes[(i == 0 ? pipes.length : i) - 1].getTailX() + PIPE_GAP);
			}
			i++;
		}

		// Reset grass
		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(fourthGrass.getTailX());
		} else if (secondGrass.isScrolledLeft()) {
			secondGrass.reset(frontGrass.getTailX());
		} else if (thirdGrass.isScrolledLeft()) {
			thirdGrass.reset(secondGrass.getTailX());
		} else if (fourthGrass.isScrolledLeft()) {
			fourthGrass.reset(thirdGrass.getTailX());
		}

	}

	public void stop() {
		frontGrass.stop();
		secondGrass.stop();
		thirdGrass.stop();
		fourthGrass.stop();

		for (Pipe p : pipes) {
			p.stop();
		}
	}

	public boolean collides(Bird bird) {
		for (Pipe p : pipes) {
			if (!p.isScored() && p.getX() + (p.getWidth() / 2) < bird.getX() + bird.getWidth()) {
				world.addScore(1);
				p.setScored(true);
				AssetLoader.coin.play();
			}
			if (p.collides(bird)) {
				return true;
			}
		}
		return false;
	}

	// The getters for our five instance variables
	public Grass getFrontGrass() {
		return frontGrass;
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
		frontGrass.onRestart(0, SCROLL_SPEED);
		secondGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
		thirdGrass.onRestart(secondGrass.getTailX(), SCROLL_SPEED);
		fourthGrass.onRestart(thirdGrass.getTailX(), SCROLL_SPEED);
		
		for (int i = 0; i < pipes.length; i++) {
			// Same thing as pipes setup in our constructor
			pipes[i].onRestart(i == 0 ? 330 : pipes[i - 1].getTailX() + PIPE_GAP, SCROLL_SPEED);
		}
	}
}
