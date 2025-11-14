// creates a module for the player hand so that the player can hit and stand and the game updates for the individual player hand

package resources;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.Collections;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Hand {
    
    // instance variables
	Stack<Card> playerHand = new Stack();
    JButton hit;
	Boolean isDone;
    JButton stand;
    JButton doubleDown;
    Blackjack blackjack;
    Stack<Card> cards;
	GUI gui;

	public Hand(Stack<Card> cards, Blackjack blackjack, GUI g) {
		
		this.cards = cards;
        this.blackjack = blackjack;
        this.gui = g;

		// create hit and stand buttons
		blackjack = new Blackjack();
		hit = new JButton("Hit");
		stand = new JButton("Stand");
		doubleDown = new JButton("Double Down");
	}

    public Stack<Card> getCards() {
        return cards;
    }

    public void addCard(Card c) {
        cards.push(c);
    }

	// returns the layerd pane containing the buttons and cards for the hand module
    public JLayeredPane returnPane(){

        JLayeredPane pane = new JLayeredPane();
        pane.setPreferredSize(new Dimension(400, 180));

        // place cards
        Object cardsArray[] = cards.toArray();
        for (int i = 0; i < cardsArray.length; i++) {
            Card card = (Card) cardsArray[i];
            Dimension pd = new Dimension(100, 145);
            card.setBounds(i * 30, i * 10, pd.width, pd.height);
            pane.add(card, i);
        }

        // position buttons
        hit.setBounds(10, 150, 80, 24);
        stand.setBounds(100, 150, 80, 24);
        doubleDown.setBounds(190, 150, 110, 24);
        pane.add(hit);
        pane.add(stand);
        pane.add(doubleDown);

        // listeners call the shared Blackjack and then ask GUI to refresh
        hit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                blackjack.hit(cards); 
                if (blackjack.gameOver) {
					disableButtons();
				}
					gui.onHandUpdated();
            }
        });

		// ends play for player, gives dealer cards and then determines win/loss and updates screen
        stand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                blackjack.stand(cards);
                disableButtons();

					gui.onHandUpdated();
            }
        });

		// doubles possible score
        doubleDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                blackjack.doubleDown(cards);
                disableButtons();
					gui.onHandUpdated();
            }
        });

        return pane;
    }

	// makes buttons not work
	private void disableButtons() {
        hit.setEnabled(false);
        stand.setEnabled(false);
        doubleDown.setEnabled(false);
        isDone = true;
	}

}
   