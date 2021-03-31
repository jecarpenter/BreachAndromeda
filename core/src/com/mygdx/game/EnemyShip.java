package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip extends Ship {

    Vector2 direction;
    float timeSinceLastDirectionChange = 0;
    float directionChangeFrequency = 0.75f;

    public EnemyShip(float movementSpeed, int shield, float xCenter, float yCenter,
                      float width, float height, float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTextureRegion) {

        super(movementSpeed, shield, xCenter, yCenter, width, height,
                laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots,
                shipTexture, shieldTexture, laserTextureRegion);

        direction = new Vector2(0, -1);
    }

    public Vector2 getDirection() {
        return direction;
    }

    private void randomizeDirectionVector(){
        //uses RNG to determine angle of next direction
        double directionAngle = BreachAndromeda.random.nextDouble() * 6.283185;

        //using trig to tell the ship to transform to new direction angle
        direction.x = (float)Math.sin(directionAngle);
        direction.y = (float)Math.cos(directionAngle);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        //logic to check when to change direction
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }

    @Override
    public LaserLogic[] fireLasers() {
        LaserLogic[] laserLogic = new LaserLogic[1];
        laserLogic[0] = new LaserLogic(boundingBox.x + boundingBox.width * 0.500f, boundingBox.y - boundingBox.height,
                laserWidth, laserHeight,
                laserMovementSpeed,
                laserTextureRegion);

        timeSinceLastShot = 0;

        return laserLogic;
    }

    @Override
    public void draw(Batch batch){
        batch.draw(shipTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if (shield > 0){
            //determining shield position and rendering if shield value is greater than 0
            batch.draw(shieldTextureRegion, boundingBox.x - boundingBox.width * 0.20f,
                    boundingBox.y - boundingBox.height * 0.25f,
                    boundingBox.width * 1.5f, boundingBox.height * 1.5f);
        }
    }

}
