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


public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	Blackjack game;
   	public GUI(Blackjack game){
	   this.game= game;
        //Create and set up the window.
       setTitle("Solitaire");
       setSize(900,700);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       // this supplies the background
       try {
		System.out.println(getClass().toString());
		Image blackImg = ImageIO.read(getClass().getResource("background.jpg"));
		ImagePanel panel = new ImagePanel(blackImg);
		panel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

		JPanel northPanel = new JPanel();
		northPanel.setOpaque(false);
		northPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
		northPanel.setPreferredSize(new Dimension(0, 150));

		JPanel southPanel = new JPanel();
		southPanel.setOpaque(false);
		southPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		southPanel.setPreferredSize(new Dimension(0, 150));

		JPanel eastPanel = new JPanel();
		eastPanel.setOpaque(false);
		eastPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
		eastPanel.setPreferredSize(new Dimension(150, 0));

		panel.setLayout(new BorderLayout());
		panel.add(northPanel, BorderLayout.NORTH);
		panel.add(eastPanel, BorderLayout.EAST);
		panel.add(southPanel, BorderLayout.SOUTH);

			Stack<Card> testStack = new Stack<Card>();
			testStack.push(new Card(2, Card.Suit.Diamonds));
			testStack.push(new Card(8, Card.Suit.Spades));
			

			JLayeredPane drawPane = drawPile(testStack);

			drawPane.setPreferredSize(new Dimension(200, 150));
			// southPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
			southPanel.add(drawPane);
			
			setContentPane(panel);

       }catch(IOException e) {
    	   e.printStackTrace();
       }
       
       /*******
        * This is just a test to make sure images are being read correctly on your machine. Please replace
        * once you have confirmed that the card shows up properly. The code below should allow you to play the solitare
        * game once it's fully created.
        */

    //    Card card = new Card(2, Card.Suit.Diamonds);
    //    System.out.println(card);
    //    this.add(card);    

        this.setVisible(true);
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
    	cards = stackIn.toArray(); //please note we convert this stack to an array so that we can iterate through it backwards while drawing. You’ll need to cast each element inside cards to a <Card> in order to use the methods to adjust their position
		for (int i = 0; i<2; i++) {
			Card card = (Card) cards[i];
			Dimension pd = new Dimension(100, 145);
			
			card.setBounds(i*30, 0, pd.width, pd.height);
			cardPane.add(card, i);
		}
	return cardPane;

	}
}
