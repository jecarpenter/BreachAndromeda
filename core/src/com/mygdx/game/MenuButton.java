package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class MenuButton {
    //position
    private Rectangle boundingBox;
    private float width, height;

    TextureRegion buttonTexture;

    public MenuButton(float width, float height, float xCenter, float yCenter, TextureRegion buttonTexture) {
        this.buttonTexture = buttonTexture;
        this.boundingBox = new Rectangle(xCenter - width / 2, yCenter - width / 2, width, height );
    }

    public void draw(Batch batch){
        batch.draw(buttonTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
