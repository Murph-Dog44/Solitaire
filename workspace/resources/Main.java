package resources;

public class Main {

	public static void main(String[] args) {
		Blackjack game = new Blackjack();
		GUI gui = new GUI(game);
	}

// public void gameLost() {
// 	gui.addText("You Lost!");
// }

// public void gameWon() {
// 	gui.addText("You Won!");
// }

// public void gameDrawn() {
// 	gui.addText("Draw!");
// }



}