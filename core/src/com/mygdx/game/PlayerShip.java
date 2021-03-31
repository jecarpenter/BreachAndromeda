package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {

    int lives;

    public PlayerShip(float movementSpeed, int shield, float xCentre, float yCentre,
                      float width, float height, float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTextureRegion) {

        super(movementSpeed, shield, xCentre, yCentre, width, height,
                laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots,
                shipTexture, shieldTexture, laserTextureRegion);
        lives = 3;
    }

    @Override
    public Laser[] fireLasers() {
        Laser [] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + boundingBox.width * 0.125f, boundingBox.y,
                laserWidth, laserHeight,
                laserMovementSpeed,
                laserTextureRegion);
        laser[1] = new Laser(boundingBox.x + boundingBox.width * 0.875f, boundingBox.y,
                laserWidth, laserHeight,
                laserMovementSpeed,
                laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }

}
