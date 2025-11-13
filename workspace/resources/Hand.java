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
	Stack<Card> dealerH;
    // constructor

    public Hand(Stack<Card> dealer) {
        
        // create hit and stand buttons
        blackjack = new Blackjack();
        hit = new JButton("Hit");
        stand = new JButton("Stand");
        doubleDown = new JButton("Double Down");
		dealerH = dealer;
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public void addCard(Card c) {
        cards.push(c);
    }
    public JLayeredPane returnPane(){
        JLayeredPane pane = new JLayeredPane();
        pane.add(hit);
        pane.add(stand);
        pane.add(doubleDown);

        Object cardsArray[];
        cardsArray = cards.toArray(); 
		for (int i = 0; i < cardsArray.length; i++) {
			Card card = (Card) cardsArray[i];
			Dimension pd = new Dimension(100, 145);
			card.setBounds(i * 30 + 100, i * 10, pd.width, pd.height);
			pane.add(card, i);
		}

        hit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blackjack.hit();
				if (blackjack.gameOver) {
					hit.setEnabled(false);
					stand.setEnabled(false);
					doubleDown.setEnabled(false);
					
					JTextPane textPane = new JTextPane();
					textPane.setText(blackjack.backgroundText);
					Font boldFont = new Font(textPane.getFont().getName(), Font.BOLD, textPane.getFont().getSize());
					textPane.setFont(boldFont);
					textPane.setOpaque(false);
					textPane.setEditable(false);
					pane.add(textPane);
					
					JTextPane scorePane = new JTextPane();
					scorePane.setText(""+blackjack.score);
					scorePane.setFont(boldFont);
					scorePane.setOpaque(false);
					scorePane.setEditable(false);
					pane.add(scorePane);
				}
/*				southPanel.removeAll();
				playerPane = drawPile(game.playerHand);
				playerPane.setPreferredSize(new Dimension(200, 200));
				southPanel.add(playerPane);
				northPanel.removeAll();
				dealerPane = drawPile(game.dealerHand);
				dealerPane.setPreferredSize(new Dimension(200, 200));
				northPanel.add(dealerPane);
				update();
                */

			}
		});


        stand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blackjack.stand();
				stand.setEnabled(false);
				hit.setEnabled(false);
				doubleDown.setEnabled(false);

				JTextPane textPane = new JTextPane();
				textPane.setText(blackjack.backgroundText);
				Font boldFont = new Font(textPane.getFont().getName(), Font.BOLD, textPane.getFont().getSize());
				textPane.setFont(boldFont);
				textPane.setOpaque(false);
				textPane.setEditable(false);
				pane.add(textPane);
					
				JTextPane scorePane = new JTextPane();
				scorePane.setText(""+blackjack.score);
				scorePane.setFont(boldFont);
				scorePane.setOpaque(false);
				scorePane.setEditable(false);
				pane.add(scorePane);

            }
        });

    // add all cards in "cards" to the pane with an offset(look at how its done in gui)
    // add all buttons
        return pane;



    }


//needs to be change so that the dealer's second card is only revealed when the last hand is stood or busted
/*stand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.stand();
				stand.setEnabled(false);
				hit.setEnabled(false);
				northPanel.removeAll();
				dealerPane = drawPile(game.dealerHand);
				dealerPane.setPreferredSize(new Dimension(200, 200));
				northPanel.add(dealerPane);
				pane = new JTextPane();
				pane.setText(game.backgroundText);
				Font boldFont = new Font(pane.getFont().getName(), Font.BOLD, pane.getFont().getSize());
				pane.setFont(boldFont);
				pane.setOpaque(false);
				pane.setEditable(false);
				eastPanel.add(pane);

				scorePane = new JTextPane();
				scorePane.setText(""+game.score);
				scorePane.setFont(boldFont);
				scorePane.setOpaque(false);
				scorePane.setEditable(false);
				westPanel.add(scorePane);
				update();
			}
		});*/


}
   