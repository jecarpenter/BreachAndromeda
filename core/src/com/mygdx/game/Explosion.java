package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Explosion {
    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;

    private Rectangle boundingBox;

    private TextureAtlas explosionAtlas = new TextureAtlas("explosion.atlas");

    Explosion(Rectangle boundingBox, float totalAnimationTime){
        this.boundingBox = boundingBox;

        explosionAnimation = new Animation(totalAnimationTime / 7, explosionAtlas.findRegions("Explosion"));
        explosionTimer = 0;
    }

    public void update (float deltaTime){
        explosionTimer += deltaTime;
    }

    public void draw (SpriteBatch batch) {
        batch.draw(explosionAnimation.getKeyFrame(explosionTimer),
                boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public boolean isFinished(){
        //if the timer has gone beyond the total animation time, this will return true
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }

}
