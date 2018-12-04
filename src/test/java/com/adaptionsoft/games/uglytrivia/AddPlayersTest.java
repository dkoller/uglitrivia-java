package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static java.util.stream.IntStream.rangeClosed;

public class AddPlayersTest {

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void maximumAllowed() {
        Game game = new Game(message -> {
        });
        rangeClosed(1, 6).forEach(i -> game.add("player" + i));
    }
}
