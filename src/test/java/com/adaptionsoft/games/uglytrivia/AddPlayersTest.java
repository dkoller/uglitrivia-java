package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static java.util.stream.IntStream.rangeClosed;

public class AddPlayersTest {

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void maximumAllowedPlayers() {
        Game game = new Game(message -> {
        });
        //the production code "looks" like it would like to accept up to six players.
        //but it seems not possible
        rangeClosed(1, 6).forEach(i -> game.add("player" + i));
    }

    @Test
    public void five() throws Exception {
        Game game = new Game(message -> {
        });

        rangeClosed(1, 5).forEach(i -> game.add("player" + i));
    }
}
