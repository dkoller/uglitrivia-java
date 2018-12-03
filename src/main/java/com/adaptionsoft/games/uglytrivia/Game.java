package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	private final ReportEngine reportEngine;
	ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

	public  Game() {
		this(new ConsoleReportEngine());
	}

	Game(ReportEngine reportEngine){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
		this.reportEngine = reportEngine;
	}

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;

		reportEngine.reportMessage(playerName + " was added");
		reportEngine.reportMessage("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	// CONTRACT The parameter roll is expected to be in [1, 6]
	public void roll(int roll) {
		reportEngine.reportMessage(players.get(currentPlayer) + " is the current player");
		reportEngine.reportMessage("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				reportEngine.reportMessage(players.get(currentPlayer) + " is getting out of the penalty box");
				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

				reportEngine.reportMessage(players.get(currentPlayer)
						+ "'s new location is "
						+ places[currentPlayer]);
				reportEngine.reportMessage("The category is " + currentCategory());
				askQuestion();
			} else {
				reportEngine.reportMessage(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

			reportEngine.reportMessage(players.get(currentPlayer)
					+ "'s new location is "
					+ places[currentPlayer]);
			reportEngine.reportMessage("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	public void reportMessage(Object message) {
		reportEngine.reportMessage(message);
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
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				reportEngine.reportMessage("Answer was correct!!!!");
				purses[currentPlayer]++;
				reportEngine.reportMessage(players.get(currentPlayer)
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
			reportEngine.reportMessage(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		reportEngine.reportMessage("Question was incorrectly answered");
		reportEngine.reportMessage(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
