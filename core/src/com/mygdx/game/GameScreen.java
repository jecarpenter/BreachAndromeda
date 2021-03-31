package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class GameScreen implements Screen {

    //screen elements
    private Camera camera;
    private Viewport viewport;

    //graphics declarations
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureRegion[] backgrounds;
    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion, enemyShipTextureRegion,
            enemyShieldTextureRegion, playerLaserTextureRegion, enemyLaserTextureRegion;

    //timing
    //background timing
    private float[] backgroundOffsets;
    private float backgroundMaxScrollingSpeed;

    //enemy spawn timing
    private float timeBetweenEnemySpawns = 2f;
    private float enemySpawnTimer;


    //world parameters
    private final float WORLD_WIDTH = 100;
    private final float WORLD_HEIGHT = 130;

    //game objects
    private PlayerShip playerShip;
    private LinkedList<EnemyShip> enemyShipList;
    private LinkedList<Explosion> explosionList;
    private LinkedList<LaserLogic> playerLaserLogicList;
    private LinkedList<LaserLogic> enemyLaserLogicList;

    //scoring
    private int score = 0;

    //HUD
    BitmapFont font;
    float hudVerticalPadding, hudLeftX, hudRightX, hudCenterX, hudFirstRowY, hudSecondRowY, hudWidth;

    Background background;


    GameScreen() {
        //built in camera class with the Orthographic camera provided in libGDX library.  This camera is the default
        // for top down styles of games, so it will be what I will use here for breach andromeda.
        camera = new OrthographicCamera();

        //viewport is what's being used to determine the aspect ratio of the game, so that no matter how you change
        // the window size, it will always display what the game intends, no more, and no less.  Stretch viewport
        // takes 3 parameters, 2 integer values for width and height, and the desired camera which is set above.
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


        //texture atlas file contains low latency file containing image data such as background images, sprites, etc
        textureAtlas = new TextureAtlas("BreachAndromeda.atlas");

        background = new Background();

        background.setupBackground();
        setupHUD();
        setupTextures();


        backgrounds = background.getBackgrounds();
        backgroundMaxScrollingSpeed = background.getBackgroundMaxScrollingSpeed();
        backgroundOffsets = background.getBackgroundOffsets();


        //setup game objects
        playerShip = new PlayerShip(45, 3,
                WORLD_WIDTH / 2, WORLD_HEIGHT / 4, 10, 10,
                4f, 15, 80, 0.5f,
                playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion);

        //setting up linked lists to support game object generation
        enemyShipList = new LinkedList<>();
        playerLaserLogicList = new LinkedList<>();
        enemyLaserLogicList = new LinkedList<>();
        explosionList = new LinkedList<>();

        //instantiating sprite batch
        batch = new SpriteBatch();

    }


    @Override
    public void render(float deltaTime) {
        batch.begin();

        //render background and give it deltaTime measurement for scrolling feature
        renderBackground(deltaTime);

        detectInput(deltaTime);
        playerShip.update(deltaTime);

        spawnEnemyShips(deltaTime);

        //iterating through list of enemies
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            moveEnemy(enemyShip, deltaTime);
            enemyShip.update(deltaTime);

            //draw enemy ships
            enemyShip.draw(batch);
        }


        //draw player ship
        playerShip.draw(batch);

        //lasers
        renderLasers(deltaTime);

        //collision detection
        checkForHits();

        //explosions
        renderExplosions(deltaTime);

        //hud
        renderHUD();

        batch.end();
    }


    private void moveEnemy(EnemyShip enemyShip, float deltaTime){
        float leftLimit, rightLimit, upLimit, downLimit;

        leftLimit = -enemyShip.boundingBox.x;
        downLimit = (float)WORLD_HEIGHT / 2 - enemyShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

        float xMove = enemyShip.getDirection().x * enemyShip.movementSpeed * deltaTime;
        float yMove = enemyShip.getDirection().y * enemyShip.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        enemyShip.translate(xMove, yMove);
    }

    private void spawnEnemyShips(float deltaTime){
        enemySpawnTimer += deltaTime;

        if (enemySpawnTimer > timeBetweenEnemySpawns) {
            enemyShipList.add(
                    new EnemyShip(25, 1,
                            BreachAndromeda.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_HEIGHT - 5, 10, 10,
                            4f, 15, 50, 0.8f,
                            enemyShipTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion)
            );
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }

    }

    private void detectInput(float deltaTime){
        float leftLimit, rightLimit, upLimit, downLimit;

        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = WORLD_HEIGHT / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) {
            float xChange = playerShip.movementSpeed * deltaTime;
            xChange = Math.min(xChange, rightLimit);
            playerShip.translate(xChange, 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            float xChange = -playerShip.movementSpeed * deltaTime;
            xChange = Math.max(xChange, leftLimit);
            playerShip.translate(xChange, 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            float yChange = playerShip.movementSpeed * deltaTime;
            yChange = Math.min(yChange, upLimit);
            playerShip.translate(0f, yChange);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            float yChange = -playerShip.movementSpeed * deltaTime;
            yChange = Math.max(yChange, downLimit);
            playerShip.translate(0f, yChange);
        }
    }



    private void renderExplosions(float deltaTime){
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()) {
                explosionListIterator.remove();
            } else {
                explosion.draw(batch);
            }
        }
    }

    private void renderLasers(float deltaTime){

        //create new lasers
        //player lasers
        if (playerShip.canFireLaser()){
            LaserLogic[] laserLogics = playerShip.fireLasers();
            for (LaserLogic laserLogic : laserLogics) {
                playerLaserLogicList.add(laserLogic);
            }
        }

        //enemy lasers
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()){
                LaserLogic[] laserLogics = enemyShip.fireLasers();
                enemyLaserLogicList.addAll(Arrays.asList(laserLogics));
            }
        }

        //laser logic
        //player lasers
        ListIterator<LaserLogic> iterator = playerLaserLogicList.listIterator();
        while(iterator.hasNext()){
            LaserLogic laserLogic = iterator.next();
            laserLogic.draw(batch);
            //using speed * time to calculate position movement
            laserLogic.boundingBox.y += laserLogic.movementSpeed * deltaTime;
            //remove old lasers that exit the screen
            if(laserLogic.boundingBox.y > WORLD_HEIGHT){
                iterator.remove();
            }
        }

        //enemy lasers
        iterator = enemyLaserLogicList.listIterator();
        while(iterator.hasNext()){
            LaserLogic laserLogic = iterator.next();
            laserLogic.draw(batch);
            //using speed * time to calculate position movement
            laserLogic.boundingBox.y -= laserLogic.movementSpeed * deltaTime;
            //remove old lasers that exit the screen
            if(laserLogic.boundingBox.y + laserLogic.boundingBox.height < 0){
                iterator.remove();
            }
        }

    }

    private void renderBackground(float deltaTime){
        //what I have to do in order to get the background to properly scroll is I need to take the background at
        // array 0 (I want it to be the slowest moving of the images) and tell it to move at 1/8 the maximum speed of
        // the backgrounds.
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;

        //I'm repeating this logic for as many backgrounds as we need, making them go faster depending on the layer
        // until max
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed / 6;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 3;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 2;

        for (int layer = 0; layer < backgroundOffsets.length; layer++){
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            //in order for the scrolling background to be fluid, you have to create a second background opposite to the
            // original.  Because the original batch only draws 100% of the screen, you need an additional screen to come
            // in when the original batch is moving off the screen.
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        }

    }

    private void renderHUD(){

        //top hud row
        font.draw(batch, "Score", hudLeftX, hudFirstRowY, hudWidth, Align.left, false);
        font.draw(batch, "Shield", hudCenterX, hudFirstRowY, hudWidth, Align.center, false);
        font.draw(batch, "Lives", hudRightX, hudFirstRowY, hudWidth, Align.right, false);

        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), hudLeftX, hudSecondRowY, hudWidth,
                Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), hudCenterX, hudSecondRowY,
                hudWidth,
                Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), hudRightX, hudSecondRowY,
                hudWidth,
                Align.right, false);
    }

    private void checkForHits() {
        ListIterator<LaserLogic> laserListIterator = playerLaserLogicList.listIterator();
        while (laserListIterator.hasNext()) {
            LaserLogic laserLogic = laserListIterator.next();

            //in order to use hit detection on multiple enemies, you need to create an iterator to go through the
            // enemy ship list, and check if it's colliding with every laser in the laser list.  We are achieving
            // that using a while loop to loop through the enemies and an if statement to check contact and remove
            // laser from laser list if it hits.
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();
                if (enemyShip.intersects(laserLogic.boundingBox)){
                    //contact with enemy ship
                    if (enemyShip.hitAndCheckIfDestroyed(laserLogic)){
                        enemyShipListIterator.remove();
                        explosionList.add(new Explosion(new Rectangle(enemyShip.boundingBox), 0.5f));
                        score += 50;
                    }
                    enemyShip.hitAndCheckIfDestroyed(laserLogic);
                    laserListIterator.remove();
                    break;
                }
            }
        }

        //similar logic applies to player ship, but with only doing a single check on the player ship
        laserListIterator = enemyLaserLogicList.listIterator();
        while (laserListIterator.hasNext()) {
            LaserLogic laserLogic = laserListIterator.next();
            if (playerShip.intersects(laserLogic.boundingBox)){
                //contact with player ship
                if (playerShip.hitAndCheckIfDestroyed(laserLogic)){
                    explosionList.add(new Explosion(new Rectangle(playerShip.boundingBox), 1.5f));
                    playerShip.isAlive = false;
                    playerShip.lives --;
                    if (playerShip.lives < 0){
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
                    }
                }
                laserListIterator.remove();
            }
        }
    }

    private void setupHUD() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("8bitFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.7f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);

        font.getData().setScale(0.08f);

        hudVerticalPadding = font.getCapHeight() / 2;
        hudLeftX = hudVerticalPadding;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCenterX = WORLD_WIDTH / 3;
        hudFirstRowY = WORLD_HEIGHT - hudVerticalPadding;
        hudSecondRowY = hudFirstRowY - hudVerticalPadding - font.getCapHeight();
        hudWidth = WORLD_WIDTH / 3;
    }


    private void setupTextures(){
        playerShipTextureRegion = textureAtlas.findRegion("playership");
        playerShieldTextureRegion = textureAtlas.findRegion("playershield");
        playerLaserTextureRegion = textureAtlas.findRegion("playerlaser");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyRed");
        enemyShieldTextureRegion = textureAtlas.findRegion("enemyshield");
        enemyLaserTextureRegion = textureAtlas.findRegion("enemylaser");
    }


    @Override
    public void resize(int width, int height) {
        //this logic handles what happens when the user resizes the window (pretty self explanatory) the resize
        // method is a built in feature of the libGDX library that takes in new width/height when the window is
        // updated.
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }

    public float getWORLD_WIDTH() {
        return WORLD_WIDTH;
    }

    public float getWORLD_HEIGHT() {
        return WORLD_HEIGHT;
    }
}
