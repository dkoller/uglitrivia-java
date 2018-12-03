package com.adaptionsoft.games.uglytrivia;

import org.junit.Assert;
import org.junit.Test;

public class RollDieTest {
    static class SinglePlayerGame extends Game {
        final int indexOfTheOnlyPlayer = 0;

        SinglePlayerGame(int startingPlaceOfTheOnlyPlayer) {
            super();
            add("::player name::");
            places[indexOfTheOnlyPlayer] = startingPlaceOfTheOnlyPlayer;
        }

        public int getPlaceOfTheOnlyPlayer() {
            return places[this.indexOfTheOnlyPlayer];
        }
    }

    @Test
    public void happyPath() {
        SinglePlayerGame singlePlayerGame = new SinglePlayerGame(0);

        singlePlayerGame.roll(3);

        Assert.assertEquals(3, singlePlayerGame.getPlaceOfTheOnlyPlayer());
    }
}
