// creates the visual elements of the program and updates the screen according to player actions

package resources;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;

public class GUI extends JFrame implements ActionListener {

	Blackjack game;
	JPanel northPanel;
	JPanel southPanel;
	JPanel eastPanel;
	JPanel westPanel;
	int gameScore;

	JButton hit;
	JButton stand;
	JButton newGame;
	JButton doubleDown;
	JButton split;

	JTextPane pane;
	JTextPane scorePane;
	JLayeredPane playerPane;
	JLayeredPane playerPane2;
	JLayeredPane dealerPane;
	Hand hand1;
	Hand hand2;

	public GUI(Blackjack game) {
		this.game = game;
		// Create and set up the window.
		setTitle("Solitaire");
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// this supplies the background
		try {
			System.out.println(getClass().toString());
			Image blackImg = ImageIO.read(getClass().getResource("background.jpg"));
			ImagePanel panel = new ImagePanel(blackImg);
			panel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

			northPanel = new JPanel();
			northPanel.setOpaque(false);
			northPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
			northPanel.setPreferredSize(new Dimension(0, 200));

			southPanel = new JPanel();
			southPanel.setOpaque(false);
			southPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			southPanel.setPreferredSize(new Dimension(0, 200));

			eastPanel = new JPanel();
			eastPanel.setOpaque(false);
			eastPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
			eastPanel.setPreferredSize(new Dimension(150, 0));

			westPanel = new JPanel();
			westPanel.setOpaque(false);
			westPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
			westPanel.setPreferredSize(new Dimension(150, 0));

			// adds new game and split buttons to right side
			newGame = new JButton("New Game");
			eastPanel.add(newGame);

			split = new JButton("Split");
			eastPanel.add(split);

			panel.setLayout(new BorderLayout());
			panel.add(northPanel, BorderLayout.NORTH);
			panel.add(eastPanel, BorderLayout.EAST);
			panel.add(southPanel, BorderLayout.SOUTH);
			panel.add(westPanel, BorderLayout.WEST);


			// adds player and dealer hands to screen
			hand1 = new Hand(game.playerHand, game, this);
			southPanel.add(hand1.returnPane());

			dealerPane = drawPile(game.dealerHand);
			dealerPane.setPreferredSize(new Dimension(200, 200));
			northPanel.add(dealerPane);

			setContentPane(panel);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// creates a new game
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remakeScreen();
			}
		});

		split.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// create second hand using shared game and this GUI
				if (game.playerHand.size() == 2
						&& game.playerHand.get(0).getTrueValue() == game.playerHand.get(1).getTrueValue()) {
					game.split();
					// recreate Hand UIs
					southPanel.removeAll();
					hand1 = new Hand(game.playerHand, game, GUI.this);
					hand2 = new Hand(game.playerHand2, game, GUI.this);
					southPanel.add(hand1.returnPane());
					southPanel.add(hand2.returnPane());
					split.setEnabled(false);
					update();
				}
			}
		});

		this.setVisible(true);
	}

	// PreCondition: None
	// PostCondition: resets the screen for a new game
	public void remakeScreen() {
		northPanel.removeAll();
		southPanel.removeAll();
		eastPanel.removeAll();
		westPanel.removeAll();
		eastPanel.add(newGame);
		eastPanel.add(split);
		game = new Blackjack();
		hand1 = new Hand(game.playerHand, game, this);
		hand2 = null;
		southPanel.add(hand1.returnPane());
		dealerPane = drawPile(game.dealerHand);
		dealerPane.setPreferredSize(new Dimension(200, 200));
		northPanel.add(dealerPane);

		update();
	}

	//PreCondition: stack of cards is given(either the playerhand or the dealerhand)
	//PostCondition: layered pane is created after receiving a stack of cards
	public JLayeredPane drawPile(Stack<Card> stackIn) {

		Object cards[];
		JLayeredPane cardPane = new JLayeredPane();
		cards = stackIn.toArray();
		for (int i = 0; i < cards.length; i++) {
			Card card = (Card) cards[i];
			Dimension pd = new Dimension(100, 145);

			card.setBounds(i * 30, 0, pd.width, pd.height);
			cardPane.add(card, i);
		}
		return cardPane;

	}

	// PreCondition: None
	// Postcondition: updates the hand contents on screen (used in Hand.java to update the screen when actionlisteners are called in Hand.java)
	public void onHandUpdated() {
		// rebuild south and north panels
		southPanel.removeAll();
		if (hand1 != null)
			southPanel.add(hand1.returnPane());
		if (hand2 != null)
			southPanel.add(hand2.returnPane());

		northPanel.removeAll();
		dealerPane = drawPile(game.dealerHand);
		dealerPane.setPreferredSize(new Dimension(200, 200));
		northPanel.add(dealerPane);

		// show end-of-round stuff post-game
		if (game.gameOver) {
			pane = new JTextPane();
			pane.setText(game.backgroundText);
			Font boldFont = new Font(pane.getFont().getName(), Font.BOLD, 16);
			pane.setFont(boldFont);
			pane.setOpaque(false);
			pane.setEditable(false);
			eastPanel.add(pane);

			scorePane = new JTextPane();
			gameScore += game.score;
			scorePane.setText("" + game.score);
			scorePane.setFont(boldFont);
			scorePane.setOpaque(false);
			scorePane.setEditable(false);
			westPanel.add(scorePane);
		}

		update();
	}


	// updates the visuals on screen
	private void update() {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
	}

}
