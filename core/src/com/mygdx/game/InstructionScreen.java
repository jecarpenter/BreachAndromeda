package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InstructionScreen implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;


    //background
    //parameters
    private final float WORLD_WIDTH = 100;
    private final float WORLD_HEIGHT = 130;
    private float backgroundHeight;

    //timing
    private float[] backgroundOffsets = {0, 0, 0, 0,};
    private float backgroundMaxScrollingSpeed;

    private TextureAtlas textureAtlas;
    private TextureRegion[] backgrounds;

    private Texture instructionsImage;
    private Sprite instructionsImageSprite;

    public InstructionScreen()
    {
        atlas = new TextureAtlas("skin/neon-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/neon-ui.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        //background
        textureAtlas = new TextureAtlas("BreachAndromeda.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("background1");
        backgrounds[1] = textureAtlas.findRegion("background3");
        backgrounds[2] = textureAtlas.findRegion("background4");
        backgrounds[3] = textureAtlas.findRegion("background5");


        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        instructionsImage = new Texture("instructions.png");

        instructionsImageSprite = new Sprite(instructionsImage);
        instructionsImageSprite.setSize(100, 130);
        instructionsImageSprite.setPosition(0, 0);


    }


    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.center();
        mainTable.padTop(10);
        mainTable.padLeft(40);

        //Create buttons
        TextButton menuButton = new TextButton("Main Menu", skin);

        menuButton.setTransform(true);

        menuButton.scaleBy(-0.6f);


        //Add listeners to buttons
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });


        //Add buttons to table
        mainTable.add(menuButton).padLeft(22).padTop(70);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        renderBackground(delta);
        instructionsImageSprite.draw(batch);
        batch.end();

        stage.act();
        stage.draw();
    }

    private void renderBackground(float delta){
        backgroundOffsets[0] += delta * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[3] += delta * backgroundMaxScrollingSpeed / 6;
        backgroundOffsets[2] += delta * backgroundMaxScrollingSpeed / 3;
        backgroundOffsets[1] += delta * backgroundMaxScrollingSpeed / 2;

        for (int layer = 0; layer < backgroundOffsets.length; layer++){
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        skin.dispose();
        atlas.dispose();
    }
}
