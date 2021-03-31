package com.mygdx.game.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.model.Background;

public class GameOverScreen implements Screen {
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
    private Background background;

    //timing
    private float[] backgroundOffsets;
    private float backgroundMaxScrollingSpeed;

    private TextureRegion[] backgrounds;

    private Texture gameOver;
    private Sprite gameOverSprite;

    public GameOverScreen()
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
        background = new Background();

        background.setupBackground();
        backgrounds = background.getBackgrounds();
        backgroundMaxScrollingSpeed = background.getBackgroundMaxScrollingSpeed();
        backgroundOffsets = background.getBackgroundOffsets();

        renderGameOver();

    }


    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);


        //Create buttons
        TextButton playAgainButton = new TextButton("Play Again", skin);
        TextButton mainMenuButton = new TextButton("Main Menu", skin);

        playAgainButton.setTransform(true);
        mainMenuButton.setTransform(true);

        playAgainButton.setPosition(28, 30);
        mainMenuButton.setPosition(28,5);

        playAgainButton.scaleBy(-0.6f);
        mainMenuButton.scaleBy(-0.6f);

        //Add listeners to buttons
        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });


        //Add table to stage
        stage.addActor(playAgainButton);
        stage.addActor(mainMenuButton);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        renderBackground(delta);
        batch.end();

        stage.act();
        stage.draw();

        batch.begin();
        gameOverSprite.draw(batch);
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

    private void renderGameOver(){
        gameOver = new Texture("GameOver.png");

        gameOverSprite = new Sprite(gameOver);
        gameOverSprite.setSize(100, 100);
        gameOverSprite.setPosition(2.5f, 20);
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
