package com.adaptionsoft.games.uglytrivia;

import org.junit.Assert;
import org.junit.Test;

public class RollDieTest {
    static class SilentSinglePlayerGame extends Game {
        final int indexOfTheOnlyPlayer = 0;

        SilentSinglePlayerGame(int startingPlaceOfTheOnlyPlayer) {
            super(message -> {});
            add("::player name::");
            places[indexOfTheOnlyPlayer] = startingPlaceOfTheOnlyPlayer;
            inPenaltyBox[indexOfTheOnlyPlayer] = false;
        }

        public int getPlaceOfTheOnlyPlayer() {
            return places[this.indexOfTheOnlyPlayer];
        }

        @Override
        public void reportMessage(Object message) {
            // Shut up
        }

        @Override
        protected void askQuestion() {
            // Shut up
        }
    }

    @Test
    public void happyPath() {
        SilentSinglePlayerGame silentSinglePlayerGame = new SilentSinglePlayerGame(0);

        silentSinglePlayerGame.roll(3);

        Assert.assertEquals(3, silentSinglePlayerGame.getPlaceOfTheOnlyPlayer());
    }

    @Test
    public void goOffTheEndOfTheBoard() {
        SilentSinglePlayerGame silentSinglePlayerGame = new SilentSinglePlayerGame(11);

        silentSinglePlayerGame.roll(30);

        // CONTRACT For now, the board is a weird shape: circular, but not quite.
        Assert.assertEquals(29, silentSinglePlayerGame.getPlaceOfTheOnlyPlayer());
    }
}
