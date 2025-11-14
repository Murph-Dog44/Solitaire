// Taiyo Murphy, Roi Porat-Shliom, Ulises Cantor
// Menchukov, Period 5, 11/14/2025
// This class establishes the game logic for a game of blackjack, with win conditions and game rules

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
	Stack<Card> playerHand2 = new Stack<>();
	Stack<Card> dealerHand = new Stack();
	Stack<Card> deck = new Stack();

	boolean playerHand1Done = false;
	boolean playerHand2Done = true;
    boolean dealerTurn = false;
    boolean gameOver = false;

	String backgroundText = "";
	int aces = 0;
	int score = 0;

	ArrayList<Stack<Card>> doubledHands = new ArrayList<>();


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
		dealerTurn = false;
		playerHand1Done = false;
        playerHand2Done = true;
        doubledHands.clear();
        backgroundText = "";

		// adds cards to deck and then randomizes their order
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
		int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

		
		// checks all of the win conditions at the beginning (if either the player and/or the dealer got blackjack)
		if (playerValue == 21 && dealerValue == 21) {
            backgroundText = "Push";
            gameOver = true;
            playerHand1Done = true;
        } else if (playerValue == 21) {
            backgroundText = "You Win";
            score += 1;
            gameOver = true;
            playerHand1Done = true;
        } else if (dealerValue == 21) {
            backgroundText = "You Lose";
            score -= 1;
            gameOver = true;
            playerHand1Done = true;
        }

		// returns what's left of the shuffled deck
		return newDeck;
	}

	// returns the value of the hand 
	public int calculateHandValue(Stack<Card> hand) {
        int value = 0;
        int aces = 0;

        for (int i = 0; i < hand.size(); i++) {
            int cardValue = hand.get(i).getValue();
            value += cardValue;
            if (hand.get(i).getTrueValue() == 1) {
                aces++;
            }
        }

        // Adjust for aces
        while (aces > 0 && value > 21) {
            value -= 10;
            aces--;
        }

        return value;
    }


	// precondition: cards have been dealt
	// postcondition: ends game and calculates new score (+1 if player wins, -1 if player loses, and no change if hand pushes)
	public void stand(Stack<Card> hand) {
        if (hand == playerHand) {
			playerHand1Done = true;
		}
        else if (hand == playerHand2) {
			playerHand2Done = true;
		}
        if (playerHand1Done && (playerHand2.isEmpty() || playerHand2Done)) {
            dealerTurn();
        }
    }

	public boolean canSplit() {
        if (playerHand.size() != 2) {
			return false;
		}
        return (playerHand.get(0).getTrueValue() == playerHand.get(1).getTrueValue());
    }

	// precondition: cards are dealt and both player cards have the same rank
	// postcondition: creates new hand to play with and separates the original hand's cards into two hands
	public void split() {
        if (!canSplit() || deck.isEmpty()) {
			return;
		}
        playerHand2 = new Stack<>();
        playerHand2.push(playerHand.pop());
        if (!deck.isEmpty()) {
			playerHand.push(deck.pop());
		}
        if (!deck.isEmpty()) {
			playerHand2.push(deck.pop());
		}
        playerHand1Done = false;
        playerHand2Done = false;
        // clear double status for both hands
        doubledHands.remove(playerHand);
        doubledHands.remove(playerHand2);
    }

	// precondition: hands have been dealt
	// postcondition: another card is added to the player hand, game checks if player busted or not
	public void hit(Stack<Card> hand) {
        if (gameOver || deck.isEmpty()) {
			return;
		}
        hand.push(deck.pop());
        int value = calculateHandValue(hand);
        if (value > 21) {
            if (hand == playerHand) {
				playerHand1Done = true;
			}
            else if (hand == playerHand2) {
				playerHand2Done = true;
			}
            if (playerHand1Done && (playerHand2.isEmpty() || playerHand2Done)) {
                dealerTurn();
            }
        }
    }

	// precondition: cards are dealt to player and dealer
	// postcondition: boolean for the double down is made true and now all points earned or lost are doubled, also only one more card is drawn automatically
	public void doubleDown(Stack<Card> hand) {
        if (!doubledHands.contains(hand)) {
            doubledHands.add(hand);
        }
        hit(hand);
        if (hand == playerHand) {
            playerHand1Done = true;
        } else if (hand == playerHand2) {
            playerHand2Done = true;
        }
        if (playerHand1Done && (playerHand2.isEmpty() || playerHand2Done)) {
            dealerTurn();
        }
    }

	// sets game state to see if play continues
	public void setGameOver(boolean over) {
		this.gameOver = over;
	}

		// checks all of the win conditions at the beginning (if either the player and/or the dealer got blackjack)
	public void checkInitialBlackjack() {
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

        if (playerValue == 21 && dealerValue == 21) {
            backgroundText = "Push!";
            playerHand1Done = true;
            gameOver = true;
        } else if (playerValue == 21) {
            backgroundText = "You Win!";
            score++;
            playerHand1Done = true;
            gameOver = true;
        } else if (dealerValue == 21) {
            backgroundText = "You Lose!";
            score--;
            playerHand1Done = true;
            gameOver = true;
        }
    }

	// deals dealer cards until they reach 17 or higher, then checks the state of the game
	public void dealerTurn() {
        if (gameOver) {
			return;
		}
        dealerTurn = true;
			dealerHand.get(0).show();
        int dealerValue = calculateHandValue(dealerHand);
        while (dealerValue < 17 && !deck.isEmpty()) {
            dealerHand.push(deck.pop());
            dealerValue = calculateHandValue(dealerHand);
        }
        evaluateHands();
        gameOver = true;
    }

	// checks the win conditions to determine if the player or dealer won, lost, pushed, or busted based on their total values
	public void evaluateHands() {
        int dealerValue = calculateHandValue(dealerHand);
        String result = "";

        // Evaluate hand 1
        if (!playerHand.isEmpty()) {
            int hand1Value = calculateHandValue(playerHand);
            int scoreChange = 0;
            String message = "";

            if (hand1Value > 21) {
                message += "Bust";
                scoreChange = -1;
            } else if (dealerValue > 21) {
                message += "You Win";
                scoreChange = 1;
            } else if (hand1Value > dealerValue) {
                message += "You Win";
                scoreChange = 1;
            } else if (hand1Value < dealerValue) {
                message += "You Lose";
                scoreChange = -1;
            } else {
                message += "Push";
                scoreChange = 0;
            }

            // Apply double if hand 1 doubled
            if (doubledHands.contains(playerHand)) {
                scoreChange *= 2;
            }

            score += scoreChange;
            result += message;
        }

        // Evaluate hand 2 if splits happned
        if (!playerHand2.isEmpty()) {
            int hand2Value = calculateHandValue(playerHand2);
            int scoreChange = 0;
            String message = " | ";

            if (hand2Value > 21) {
                message += "Bust";
                scoreChange = -1;
            } else if (dealerValue > 21) {
                message += "You Win";
                scoreChange = 1;
            } else if (hand2Value > dealerValue) {
                message += "You Win";
                scoreChange = 1;
            } else if (hand2Value < dealerValue) {
                message += "You Lose";
                scoreChange = -1;
            } else {
                message += "Push";
                scoreChange = 0;
            }

            // Apply double if hand 2 doubled
            if (doubledHands.contains(playerHand2)) {
                scoreChange *= 2;
                message += " (Double)";
            }

            score += scoreChange;
            result+= message;
        }

        backgroundText = result.toString();
    }
}
