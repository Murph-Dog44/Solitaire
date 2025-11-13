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

public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	Blackjack game;
	JPanel northPanel;
	JPanel southPanel;
	JPanel eastPanel;
	JPanel westPanel;
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
	// hit.addActionListener(new CustomActionListener());
	// stand.addActionListener(new CustomActionListener());

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

			hit = new JButton("Hit");
			eastPanel.add(hit);

			doubleDown = new JButton("Double Down");
			eastPanel.add(doubleDown);

			stand = new JButton("Stand");
			eastPanel.add(stand);
			
			split = new JButton("Split");
			eastPanel.add(split);

			newGame = new JButton("New Game");
			eastPanel.add(newGame);

			panel.setLayout(new BorderLayout());
			panel.add(northPanel, BorderLayout.NORTH);
			panel.add(eastPanel, BorderLayout.EAST);
			panel.add(southPanel, BorderLayout.SOUTH);
			panel.add(westPanel, BorderLayout.WEST);

			playerPane = drawPile(game.playerHand);
			dealerPane = drawPile(game.dealerHand);

			playerPane.setPreferredSize(new Dimension(200, 200));
			dealerPane.setPreferredSize(new Dimension(200, 200));

			// testing, mr m showed how we could make code work
			// Card c = new Card(2, Suit.Spades);
			// c.addAncestorListener(this);
			// playerPane.add(c);

			// southPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
			southPanel.add(playerPane);
			northPanel.add(dealerPane);

			setContentPane(panel);

		} catch (IOException e) {
			e.printStackTrace();
		}

		hit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.hit();
				if (game.gameOver) {
					hit.setEnabled(false);
					stand.setEnabled(false);
					doubleDown.setEnabled(false);
					split.setEnabled(false);
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
				}
				southPanel.removeAll();
				playerPane = drawPile(game.playerHand);
				playerPane.setPreferredSize(new Dimension(200, 200));
				southPanel.add(playerPane);
				northPanel.removeAll();
				dealerPane = drawPile(game.dealerHand);
				dealerPane.setPreferredSize(new Dimension(200, 200));
				northPanel.add(dealerPane);
				
				
				update();

			}
		});

		stand.addActionListener(new ActionListener() {
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
		});






		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remakeScreen();
			}
		});


		split.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.playerHand.get(0).getTrueValue() == game.playerHand.get(1).getTrueValue()){
				Hand hand1 = new Hand(game.dealerHand);
				hand1.addCard(game.playerHand.pop());
				Hand hand2 = new Hand(game.dealerHand);
				hand1.addCard(game.playerHand.pop());
				southPanel.add(hand1.returnPane());
				southPanel.add(hand2.returnPane());
				hit.setEnabled(false);
				stand.setEnabled(false);
				split.setEnabled(false);
				doubleDown.setEnabled(false);
				update();
				}
			}
		});

		doubleDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.doubleDown();
				stand.setEnabled(false);
				hit.setEnabled(false);
				doubleDown.setEnabled(false);
				northPanel.removeAll();
				dealerPane = drawPile(game.dealerHand);
				dealerPane.setPreferredSize(new Dimension(200, 200));
				northPanel.add(dealerPane);
				southPanel.removeAll();
				playerPane = drawPile(game.playerHand);
				playerPane.setPreferredSize(new Dimension(200, 200));
				southPanel.add(playerPane);

				Font boldFont = new Font(pane.getFont().getName(), Font.BOLD, pane.getFont().getSize());
				scorePane = new JTextPane();
				scorePane.setText(""+game.score);
				scorePane.setFont(boldFont);
				scorePane.setOpaque(false);
				scorePane.setEditable(false);
				westPanel.add(scorePane);

				update();
			}
		});

		/*******
		 * This is just a test to make sure images are being read correctly on your
		 * machine. Please replace
		 * once you have confirmed that the card shows up properly. The code below
		 * should allow you to play the solitare
		 * game once it's fully created.
		 */

		// Card card = new Card(2, Card.Suit.Diamonds);
		// System.out.println(card);
		// this.add(card);

		this.setVisible(true);
	}


	public void remakeScreen(){
				northPanel.removeAll();
				southPanel.removeAll();
				eastPanel.removeAll();
				westPanel.removeAll();
				playerPane.removeAll();
				dealerPane.removeAll();
				game.shuffle();
				game.setGameOver(false);
				hit.setEnabled(true);
				stand.setEnabled(true);
				playerPane = drawPile(game.playerHand);
				dealerPane = drawPile(game.dealerHand);
				playerPane.setPreferredSize(new Dimension(200, 200));
				dealerPane.setPreferredSize(new Dimension(200, 200));
				southPanel.add(playerPane);
				northPanel.add(dealerPane);
				eastPanel.add(hit);
				eastPanel.add(stand);
				eastPanel.add(newGame);

				update();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("something happens");// involves logic from the blackjack
		// class
		// repaint();

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public JLayeredPane drawPile(Stack<Card> stackIn) {

		Object cards[];
		JLayeredPane cardPane = new JLayeredPane();
		cards = stackIn.toArray(); // please note we convert this stack to an array so that we can iterate through
									// it backwards while drawing. You’ll need to cast each element inside cards to
									// a <Card> in order to use the methods to adjust their position
		for (int i = 0; i < cards.length; i++) {
			Card card = (Card) cards[i];
			Dimension pd = new Dimension(100, 145);

			card.setBounds(i * 30, i * 10, pd.width, pd.height);
			cardPane.add(card, i);
		}
		return cardPane;

	}

	// action listeners for buttons, call hit and stand methods in blackjack and
	// then repaint the game after each one.

	private void update() {
		this.revalidate();
		this.repaint();
	}

}
