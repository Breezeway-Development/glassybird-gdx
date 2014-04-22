package com.breezewaydevelopment.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion bg, grass;

    public static Animation birdAnimation;
    public static TextureRegion bird, birdDown, birdUp;

    public static TextureRegion skullUp, skullDown, bar; //pipes have skulls at the end of them

    public static void load() {

        texture = new Texture(Gdx.files.internal("data/texture.png")); // Texture is like a spritesheet
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); // Minification and magnification

        bg = new TextureRegion(texture, 0, 0, 136, 43); // x, y, width, height
        bg.flip(false, true); // flip x, y. libGDX assumes a y-up coord system

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown.flip(false, true);

        bird = new TextureRegion(texture, 153, 0, 17, 12);
        bird.flip(false, true);

        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);

        
        birdAnimation = new Animation(0.06f, new TextureRegion[]  { birdDown, bird, birdUp }); //.06 second anim
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true); // Flip y from skullUp

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);

    }
    
    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }

}