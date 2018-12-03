
package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;

import java.util.Random;


public class GoldenMasterRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		for (int i= 0; i < 10; i++) {
			Game aGame = new Game();

			aGame.add("Chet");
			aGame.add("Pat");
			aGame.add("Sue");

			Random rand = new Random(42+3*i);

			do {

				aGame.roll(rand.nextInt(5) + 1);

				if (rand.nextInt(9) == 7) {
					notAWinner = aGame.wrongAnswer();
				} else {
					notAWinner = aGame.wasCorrectlyAnswered();
				}


			} while (notAWinner);


		}
	}
}
