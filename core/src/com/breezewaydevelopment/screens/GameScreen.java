package com.breezewaydevelopment.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30; //opengl es 3.0

public class GameScreen implements Screen {

	@Override
	public void render(float delta) {
		// Draws the RGB color 10, 15, 230, at 100% opacity
        Gdx.gl.glClearColor(230/255.0f, 15/255.0f, 10/255.0f, 1f);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        System.out.println(1 / delta);
	}

	@Override
    public void resize(int width, int height) {
        System.out.println("GameScreen - resize called");
    }

    @Override
    public void show() {
        System.out.println("GameScreen - show called");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen - hide called");     
    }

    @Override
    public void pause() {
        System.out.println("GameScreen - pause called");        
    }

    @Override
    public void resume() {
        System.out.println("GameScreen - resume called");       
    }

    @Override
    public void dispose() {
        // Leave blank
    }

}
