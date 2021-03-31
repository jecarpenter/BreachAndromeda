package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.w3c.dom.Text;

public class MainMenuScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private TextureAtlas textureAtlas, menuAtlas;
    private TextureRegion[] backgrounds, menuTextures;
    private SpriteBatch batch;


    //timing
    private float[] backgroundOffsets = {0, 0, 0, 0,};
    private float backgroundMaxScrollingSpeed;

    //parameters
    private final float WORLD_WIDTH = 100;
    private final float WORLD_HEIGHT = 130;
    private float backgroundHeight;

    //menu objects
    Sprite title, start, instructions, exit;



    MainMenuScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


        textureAtlas = new TextureAtlas("BreachAndromeda.atlas");
        menuAtlas = new TextureAtlas("MainMenu.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("background1");
        backgrounds[1] = textureAtlas.findRegion("background3");
        backgrounds[2] = textureAtlas.findRegion("background4");
        backgrounds[3] = textureAtlas.findRegion("background5");


        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        menuTextures = new TextureRegion[7];
        menuTextures[0] = textureAtlas.findRegion("BreachAndromedaTitle");
        menuTextures[1] = textureAtlas.findRegion("Start");
        menuTextures[2] = textureAtlas.findRegion("StartHovered");
        menuTextures[3] = textureAtlas.findRegion("Instructions");
        menuTextures[4] = textureAtlas.findRegion("InstructionsHovered");
        menuTextures[5] = textureAtlas.findRegion("Exit");
        menuTextures[6] = textureAtlas.findRegion("ExitHovered");

        rendertitle();
        renderStart();
        renderInstructions();
        renderExit();
        


        batch = new SpriteBatch();
    }


    @Override
    public void render(float deltaTime) {
        batch.begin();

        renderBackground(deltaTime);

        title.draw(batch);
        start.draw(batch);
        instructions.draw(batch);
        exit.draw(batch);

        batch.end();

    }

    private void renderBackground(float deltaTime){
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed / 6;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 3;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 2;

        for (int layer = 0; layer < backgroundOffsets.length; layer++){
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        }
    }


    private void rendertitle(){
        Texture mainTitle = new Texture("Menu/BreachAndromedaTitle.png");
        title = new Sprite(mainTitle);
        title.setSize(80, 70);
        title.setPosition(10, 65);
    }

    private void renderStart(){
        Texture startButton = new Texture("Menu/Start.png");
        start = new Sprite(startButton);
        start.setSize(40, 20);
        start.setPosition(29, 50);

    }

    private void renderInstructions(){
        Texture instructionsButton = new Texture("Menu/Instructions.png");
        instructions = new Sprite(instructionsButton);
        instructions.setSize(39, 20);
        instructions.setPosition((float) 29.5, 30);
    }

    private void renderExit(){
        Texture exitButton = new Texture("Menu/Exit.png");
        exit = new Sprite(exitButton);
        exit.setSize(41, 20);
        exit.setPosition((float) 28.5, 10);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {
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
}
