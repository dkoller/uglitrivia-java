package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private final ReportEngine reportEngine;
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    private final List<PlayerState> playerStates;

    public Game() {
        this(new ConsoleReportEngine());
    }

    Game(ReportEngine reportEngine) {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
        playerStates = new ArrayList<>();
        this.reportEngine = reportEngine;
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        PlayerState playerState = new PlayerState(playerName);
        players.add(playerState.name);
        places[howManyPlayers()] = playerState.place;
        purses[howManyPlayers()] = playerState.purse;
        inPenaltyBox[howManyPlayers()] = playerState.inPenaltyBox;
        playerStates.add(playerState);

        reportEngine.reportMessage(playerName + " was added");
        reportEngine.reportMessage("They are player number " + players.size());
        return true;
    }

    static class PlayerState {
        private final String name;
        private final int place;
        private final int purse;
        private final boolean inPenaltyBox;

        PlayerState(String name) {
            this.name = name;
            this.place = 0;
            this.purse = 0;
            this.inPenaltyBox = false;
        }
    }

    public int howManyPlayers() {
        return players.size();
    }

    // CONTRACT The parameter roll is expected to be in [1, 6]
    public void roll(int roll) {
        reportEngine.reportMessage(currentPlayerName() + " is the current player");
        reportEngine.reportMessage("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                reportEngine.reportMessage(currentPlayerName() + " is getting out of the penalty box");
                int nextPlace = calculateNextPlace(places[currentPlayer], roll);

                reportEngine.reportMessage(currentPlayerName() + "'s new location is " + nextPlace);
                reportEngine.reportMessage("The category is " + category(nextPlace));
                places[currentPlayer] = nextPlace;
                askQuestion();
            } else {
                reportEngine.reportMessage(currentPlayerName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            places[currentPlayer] = calculateNextPlace(places[currentPlayer], roll);

            reportEngine.reportMessage(currentPlayerName()
                    + "'s new location is "
                    + places[currentPlayer]);
            reportEngine.reportMessage("The category is " + currentCategory());
            askQuestion();
        }
    }

    private String currentPlayerName() {
        return playerStates.get(currentPlayer).name;
    }

    // this method should not overflow when you role more than 24
    // should be (place+roll)%12
    private static int calculateNextPlace(int place, int roll) {
        int nextPlace = place + roll;
        if (nextPlace <= 11) {
            return nextPlace;
        } else {
            return nextPlace - 12;
        }
    }

    protected void askQuestion() {
        if (currentCategory() == "Pop")
            reportEngine.reportMessage(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            reportEngine.reportMessage(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            reportEngine.reportMessage(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            reportEngine.reportMessage(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        return category(places[currentPlayer]);
    }

    private static String category(int place) {
        if (place == 0) return "Pop";
        if (place == 4) return "Pop";
        if (place == 8) return "Pop";
        if (place == 1) return "Science";
        if (place == 5) return "Science";
        if (place == 9) return "Science";
        if (place == 2) return "Sports";
        if (place == 6) return "Sports";
        if (place == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                reportEngine.reportMessage("Answer was correct!!!!");
                purses[currentPlayer]++;
                reportEngine.reportMessage(currentPlayerName()
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }
        } else {
            reportEngine.reportMessage("Answer was corrent!!!!");
            purses[currentPlayer]++;
            reportEngine.reportMessage(currentPlayerName()
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        reportEngine.reportMessage("Question was incorrectly answered");
        reportEngine.reportMessage(currentPlayerName() + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }

    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
