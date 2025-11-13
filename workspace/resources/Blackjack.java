package resources;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.Collections;
import java.util.*;

import javax.swing.JLayeredPane;

public class Blackjack {

	// creates instance variables for hands, score, etc. 
	Stack<Card> playerHand = new Stack();
	Stack<Card> playerHand2 = new Stack();
	Stack<Card> dealerHand = new Stack();
	Stack<Card> deck = new Stack();
	String backgroundText = "";
	boolean gameOver = false;
	int aces = 0;
	int score = 0;
	boolean doubleD = false;


	// constructor
	public Blackjack() {
		deck = shuffle();
	}

	// precondition: deck is not null
	// postcondition: cards in deck are placed in a random order and cards are dealt to player and dealer
	public Stack<Card> shuffle() {
		playerHand = new Stack();
		dealerHand = new Stack();
		gameOver = false;
		doubleD = false;
		backgroundText = "";
		Stack<Card> newDeck = new Stack();
		for (int i = 1; i < 14; i++) {
			newDeck.push(new Card(i, Card.Suit.Diamonds));
			newDeck.push(new Card(i, Card.Suit.Hearts));
			newDeck.push(new Card(i, Card.Suit.Spades));
			newDeck.push(new Card(i, Card.Suit.Clubs));
		}
		Collections.shuffle(newDeck);

		// player receives cards from deck, removing them from deck
		playerHand.push(newDeck.pop());
		playerHand.push(newDeck.pop());

		// dealer receives cards from deck, removing them from deck
		dealerHand.push(newDeck.pop());
		dealerHand.push(newDeck.pop());

		// dealer second card is hidden from player
		dealerHand.get(0).hide();

		// calculates player and dealer scores from dealing at the beginning
		int countPlayer = 0;

		int dealerValue = 0;
		for (int i = 0; i < dealerHand.size(); i++) {
			dealerValue += dealerHand.get(i).getValue();
		}

		for (int i = 0; i < playerHand.size(); i++) {
			countPlayer += playerHand.get(i).getValue();
		}

		
		// checks all of the win conditions a
		if (countPlayer == 21 && dealerValue == 21) {
			backgroundText = "Push!";
			gameOver = true;
			// end the game - player win

		}

		if (countPlayer == 21) {
			backgroundText = "You Win!";
			score++;
			gameOver = true;
			// end the game - player win

		}

				if (countPlayer == 21) {
			backgroundText = "You Lose!";
			score--;
			gameOver = true;
			// end the game - player win

		}

		return newDeck;
	}

	// dealerHand must be populated already
	public void stand() {
		// flips dealer card and deals until dealer hand value >= 17 or busts.
		gameOver = true;
		dealerHand.get(0).show();
		int dealerValue = 0;
		for (int i = 0; i < dealerHand.size(); i++) {
			dealerValue += dealerHand.get(i).getValue();
		}
		while (dealerValue < 17) {
			dealerHand.push(deck.pop());
			dealerValue = 0;
			for (int i = 0; i < dealerHand.size(); i++) {
				dealerValue += dealerHand.get(i).getValue();
			}
		}

		int countPlayer = 0;
		for (int i = 0; i < playerHand.size(); i++) {
			countPlayer += playerHand.get(i).getValue();
		}

		if (dealerValue > 21 || countPlayer > dealerValue) {
			backgroundText = "You Win!";
			if (doubleD){
				score++;
			}
			score++;
			// end the game - player win

		}

		if (countPlayer < dealerValue && dealerValue <= 21) {
			backgroundText = "You Lose!";
			if (doubleD){
				score--;
			}
			score--;
			// end the game - player loss

		}

		if (countPlayer == dealerValue) {
			backgroundText = "Push!";
			// end the game - draw

		}

	}

	public void split(){
		playerHand2.push(playerHand.pop());
		playerHand.push(deck.pop());
		playerHand2.push(deck.pop());

		gameOver = true;
		dealerHand.get(0).show();
		int dealerValue = 0;
		for (int i = 0; i < dealerHand.size(); i++) {
			dealerValue += dealerHand.get(i).getValue();
		}
		while (dealerValue < 17) {
			dealerHand.push(deck.pop());
			dealerValue = 0;
			for (int i = 0; i < dealerHand.size(); i++) {
				dealerValue += dealerHand.get(i).getValue();
			}
		}

		int handValue1 = 0;
		int handValue2 = 0;

		for (int i = 0; i < playerHand.size(); i++) {
			handValue1 += playerHand.get(i).getValue();
		}

		if (dealerValue > 21 || handValue1 > dealerValue) {
			backgroundText = "You Win!";
			if (doubleD){
				score++;
			}
			score++;
			// end the game - player win

		}

		if (handValue1 < dealerValue && dealerValue <= 21) {
			backgroundText = "You Lose!";
			if (doubleD){
				score--;
			}
			score--;
			// end the game - player loss

		}

		if (handValue1 == dealerValue) {
			backgroundText = "Push!";
			// end the game - draw

		}
		//add the code to check for the second hand when split
	}

	public void hit() {
		aces = 0;
		playerHand.push(deck.pop());
		int playerValue = 0;
		for (int i = 0; i < playerHand.size(); i++) {
			playerValue += playerHand.get(i).getValue();
			if (playerHand.get(i).getValue() == 11) {
				aces++; // this is smart taiyo
			}
			System.out.println(playerHand.get(i));
		}
		while (aces > 0 && playerValue > 21) {
			playerValue -= 10;
			aces--;
		}
		if (playerValue > 21) {
			dealerHand.get(0).show();
			gameOver = true;
			backgroundText = "You Lose!";
			if (doubleD){
				score--;
			}
			score--;
			// end of game - player loss
		}
		if (playerValue == 21) {
			dealerHand.get(0).show();
			gameOver = true;
			backgroundText = "Blackjack!";
			score++;
		}

	}

	public void doubleDown() {
		doubleD = true;
		hit();
		stand();
	}

	public void setGameOver(boolean over) {
		this.gameOver = over;
	}
}
