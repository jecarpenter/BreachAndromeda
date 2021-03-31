package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class BreachAndromeda extends Game {

	//setting gameScreen class at top level
	GameScreen gameScreen;

	//random number generator used throughout the application
	public static Random random = new Random();

	//default spritebatch class
	public SpriteBatch batch;


	@Override
	public void create() {
		//create method to make a new game screen and set it on creation
		gameScreen = new GameScreen();
		batch = new SpriteBatch();

		setScreen(new MenuScreen());
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
}
