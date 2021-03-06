package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {

    private int lives;
    private float respawnTimer;
    private boolean isAlive;
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
    public LaserLogic[] fireLasers() {
        LaserLogic[] laserLogic = new LaserLogic[2];
        laserLogic[0] = new LaserLogic(getBoundingBox().x + getBoundingBox().width * 0.125f, getBoundingBox().y,
                laserWidth, laserHeight,
                laserMovementSpeed,
                laserTextureRegion);
        laserLogic[1] = new LaserLogic(getBoundingBox().x + getBoundingBox().width * 0.875f, getBoundingBox().y,
                laserWidth, laserHeight,
                laserMovementSpeed,
                laserTextureRegion);

        timeSinceLastShot = 0;
        return laserLogic;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isAlive == false){
            shield = 99;
            respawnTimer -= deltaTime;
            timeBetweenShots = 99;
            shipTextureRegion = deadShipRegion;
        }
        if (respawnTimer <= 0 ){
            isAlive = true;
            respawnTimer = 3;
            shield = 3;
            timeBetweenShots = 0.5f;
            shipTextureRegion = aliveShipRegion;
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

}
