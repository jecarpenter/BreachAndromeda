package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {

    int lives;
    float respawnTimer;
    boolean isAlive;
    Texture deadShipTexture, aliveShipTexture;
    TextureRegion deadShipRegion, aliveShipRegion;

    public PlayerShip(float movementSpeed, int shield, float xCentre, float yCentre,
                      float width, float height, float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTextureRegion) {

        super(movementSpeed, shield, xCentre, yCentre, width, height,
                laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots,
                shipTexture, shieldTexture, laserTextureRegion);
        lives = 3;
        respawnTimer = 3f;
        isAlive = true;

        deadShipTexture = new Texture("deadShip.png");
        deadShipRegion = new TextureRegion(deadShipTexture);
        aliveShipTexture = new Texture("playership.png");
        aliveShipRegion = new TextureRegion(aliveShipTexture);
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

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isAlive == false){
            shield = 99;
            respawnTimer -= deltaTime;
            timeBetweenShots = 99;
            shipTextureRegion = deadShipRegion;
            lives --;
        }
        if (respawnTimer <= 0 ){
            isAlive = true;
            respawnTimer = 3;
            shield = 3;
            timeBetweenShots = 0.5f;
            shipTextureRegion = aliveShipRegion;
        }
    }
}
