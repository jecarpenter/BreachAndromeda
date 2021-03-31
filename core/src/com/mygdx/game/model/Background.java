package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Background {


    private TextureAtlas textureAtlas;
    private TextureRegion[] backgrounds;
    private final float WORLD_HEIGHT = 130;
    private float[] backgroundOffsets = {0, 0, 0, 0,};
    private float backgroundMaxScrollingSpeed;

    public Background() {
    }

    public void setupBackground() {
        textureAtlas = new TextureAtlas("BreachAndromeda.atlas");

        //setting up texture array with a size of 4, one index for each background layer
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("background1");
        backgrounds[1] = textureAtlas.findRegion("background3");
        backgrounds[2] = textureAtlas.findRegion("background4");
        backgrounds[3] = textureAtlas.findRegion("background5");
        backgroundMaxScrollingSpeed = WORLD_HEIGHT / 4;
    }


    public TextureRegion[] getBackgrounds() {
        return backgrounds;
    }

    public float[] getBackgroundOffsets() {
        return backgroundOffsets;
    }

    public float getBackgroundMaxScrollingSpeed() {
        return backgroundMaxScrollingSpeed;
    }
}
