package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MenuScreen;

import java.util.Random;

public class BreachAndromeda extends Game {

	//random number generator used throughout the application
	public static Random random = new Random();

	//default spritebatch class
	public SpriteBatch batch;


	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new MenuScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
