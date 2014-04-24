package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture;

	public static Sound dead, flap, coin;

	public static BitmapFont font, shadow;

	public static TextureRegion bg, grass;
	public static TextureRegion bird, birdDown, birdUp; // Wing position
	public static TextureRegion skullUp, skullDown, bar; // Pipes (bars) have skulls at the end of them

	public static Animation birdAnimation;

	public static void load() {
		texture = new Texture(Gdx.files.internal("data/texture.png")); // Texture is like a spritesheet
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); // Minification and magnification

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);

		bg = new TextureRegion(texture, 0, 0, 136, 43); // x, y, width, height
		bg.flip(false, true); // libGDX assumes a y-up coord system (usage flip(boolean x, boolean y))
		grass = new TextureRegion(texture, 0, 43, 143, 11);
		grass.flip(false, true);

		skullUp = new TextureRegion(texture, 192, 0, 24, 14);
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false, true); // Flip y from skullUp
		bar = new TextureRegion(texture, 136, 16, 22, 3);
		bar.flip(false, true);

		birdDown = new TextureRegion(texture, 136, 0, 17, 12);
		birdDown.flip(false, true);
		bird = new TextureRegion(texture, 153, 0, 17, 12);
		bird.flip(false, true);
		birdUp = new TextureRegion(texture, 170, 0, 17, 12);
		birdUp.flip(false, true);
		birdAnimation = new Animation(0.06f, new TextureRegion[] { birdDown, bird, birdUp }); //.06 sec flapping anim
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG); // Back and forth
	}

	public static void dispose() {
		texture.dispose();

		dead.dispose();
		flap.dispose();
		coin.dispose();

		font.dispose();
		shadow.dispose();
	}

}