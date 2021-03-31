package com.mygdx.game;

import com.mygdx.game.screens.GameScreen;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameScreenTest {

    @Test
    public void testShipDamage(){
        GameScreen gameTest = new GameScreen();
        assertEquals(130, gameTest.getWORLD_HEIGHT(), 0);
    }

}