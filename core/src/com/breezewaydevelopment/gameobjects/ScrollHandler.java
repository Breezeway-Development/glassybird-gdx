package com.breezewaydevelopment.gameobjects;

import com.breezewaydevelopment.gameworld.GameWorld;
import com.breezewaydevelopment.helpers.AssetLoader;

public class ScrollHandler {

	public static final int SCROLL_SPEED = -59; // How fast we need to scroll
	public static final int PIPE_GAP = 49; // Gap between the pipes

	private GameWorld world;
	private Grass frontGrass, backGrass;
	private Pipe[] pipes = new Pipe[3];

	public ScrollHandler(GameWorld world, float yPos) { // Where to begin our grass and pipe objects
		this.world = world;
		frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);

		for (int i = 0; i < pipes.length; i++) {
			// Here we define each pipe. If i = 0 start at x=210, otherwise start behind the previous pipe
			pipes[i] = new Pipe(i == 0 ? 210 : pipes[i - 1].getTailX() + PIPE_GAP, 0, 22, 60 + (i * 10), SCROLL_SPEED, yPos);
		}
	}

	public void update(float delta) {
		// Update our objects
		frontGrass.update(delta);
		backGrass.update(delta);

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

	public Grass getBackGrass() {
		return backGrass;
	}

	public Pipe[] getPipes() {
		return pipes;
	}

	public void onRestart() {
		frontGrass.onRestart(0, SCROLL_SPEED);
		backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
		
		for (int i = 0; i < pipes.length; i++) {
			// Same thing as pipes setup in our constructor
			pipes[i].onRestart(i == 0 ? 210 : pipes[i - 1].getTailX() + PIPE_GAP, SCROLL_SPEED);
		}
	}
}
