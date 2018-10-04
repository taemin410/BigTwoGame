import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * BigTwoTable Class
 * 
 * BigTwoTable class implements the Card Game Table Interface. 
 * 
 * It is used to build GUI for the Big Two Card Game. 
 * Handles all user actions. 
 * 
 * Implements CardGameTable interface and its methods.
 * 
 * Has several Inner Classes to use action listeners and JPanel to show images. 
 * 
 * @author hataemin
 * 
 */
public class BigTwoTable implements CardGameTable {
	/**
	 * Constructor for BigTwoTable Class.
	 * 
	 * Stores given parameter to its private instance variable.
	 * Sets max-size of selected boolean and initialize.
	 * 
	 * Creates JMenuItem, JFrame, JTextArea, JScrollPane, JMenuBar, JPanel, JTextfield, etc..
	 *  Adds items to JFrame.
	 *  
	 *  Set visibility.
	 * 
	 * set playername with given name asked by joptionpane
	 * set default IP and serverport address 
	 * @param game
	 * 			BigTwo CardGame.
	 */
	public BigTwoTable(CardGame game) {
		
		this.game=game;
		selected=new boolean[13];
		
		Arrays.fill(selected, false);
		
		//making a JFrame
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("BIG TWO");
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setResizable(true);
		frame.setLayout(new GridLayout(1,2,0,0));

		//Adding panel
		bigTwoPanel= new BigTwoPanel();
		bigTwoPanel.setLayout(new BorderLayout());
		bigTwoPanel.addMouseListener((BigTwoPanel)bigTwoPanel);
		
		//JTextarea
		JPanel textarea = new JPanel();
		textarea.setLayout(new GridLayout(2,1,0,0));
		JPanel panel = new JPanel(new BorderLayout());
		msgArea=new JTextArea();
		msgArea.setEditable(false);
		//msgArea.setLineWrap(true);
		scroll = new JScrollPane(msgArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scroll, BorderLayout.CENTER);
		textarea.add(panel);
		
		//TestArea for message
		JPanel messagePanel = new JPanel(new BorderLayout());
		JPanel sendPanel = new JPanel();
		sendPanel.add(new JLabel("Message"));
		chatInput = new JTextField(20);
		chatInput.addKeyListener(new EnterKeyListener());
		sendPanel.add(chatInput);
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitButtonListener());
		sendPanel.add(submit);
		messagePanel.add(sendPanel, BorderLayout.SOUTH);
		chatBox = new JTextArea();
		chatBox.setEditable(false);
		JScrollPane scrollchat = new JScrollPane(chatBox);
		scrollchat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollchat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		messagePanel.add(scrollchat, BorderLayout.CENTER);
		textarea.add(messagePanel);

		
		//Addingmenu item
		JMenuBar bar = new JMenuBar();
		Connect = new JMenuItem("Connect");
		JMenuItem Quit = new JMenuItem("Quit");
		Connect.addActionListener(new ConnectMenuItemListener());
		Quit.addActionListener(new QuitMenuItemListener());
	    bar.add(Connect);
	    bar.add(Quit);
	    bar.setVisible(true);
		frame.setJMenuBar(bar);

		bigTwoPanel.setLayout(new BorderLayout());
		JPanel buttonpanel = new JPanel();
		
		playButton= new JButton("Play");
		passButton= new JButton("Pass");
		//add action listener to the buttons and mouse
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		
		buttonpanel.add(playButton);
		buttonpanel.add(passButton);
		bigTwoPanel.add(buttonpanel, BorderLayout.SOUTH);
		
		frame.add(bigTwoPanel);
		frame.add(textarea);
	    frame.setVisible(true);
		frame.pack();
	    frame.repaint();

	    //handles input for string name.
	    String playername = new String();
	    while(playername.equals("")){
     		playername = JOptionPane.showInputDialog("Enter Name: ");
     		if(playername == null){
     			System.exit(0);
     		}
       	}
	    
	    //default value 
     	((BigTwoClient)game).setPlayerName(playername);
     	((BigTwoClient)game).setServerIP("127.0.0.1");
 		((BigTwoClient)game).setServerPort(2396);	  
	    
	}
	private JTextField chatInput;
	private JTextArea chatBox; 
	private JMenuItem Connect;
	private JScrollPane scroll;
	private CardGame game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
	
	/**
	 * Sets the activePlayer with given parameter.
	 * 
	 * @param activePlayer - Integer value signifying player index.
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer=activePlayer;
	}
	/**
	 * Returns an array of indices of the cards selected.
	 * 
	 * @return an array of indices of the cards selected, or null if no valid
	 *         cards have been selected
	 */
	public int[] getSelected() {

		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		return cardIdx;
		
	}
	/**
	 * A method for resetting the list of selected cards.
	 * 
	 * Resets the selected array, by giving them all false values.
	 * 
	 */
	public void resetSelected() {
		for(int i=0; i<selected.length;i++) {
			selected[i]=false;
		}
	}
	/**
	 * Redraws the GUI.
	 */
	public void repaint() {
		bigTwoPanel.repaint();
	}
	/**
	 * A method for printing specified string to the message area of GUI.
	 * 
	 * Adds Text messages to the msgArea(JTextArea).
	 * 
	 * @param msg - string message.
	 */
	public void printMsg(String msg) {
		msgArea.append(msg+"\n");	
	}
	/**
	 * A method for printing specified string to the chat message area of GUI.
	 * 
	 * Adds Text messages to the chatBox(JTextArea).
	 * 
	 * @param msg -string message
	 */
	public void printChatMsg(String msg) {
		chatBox.append(msg);
	}

	/**
	 * A method for clearing the message area of GUI.
	 * 
	 * Clears the msgArea, by removing all msgArea texts, and
	 * by giving new empty string.
	 * 
	 */
	public void clearMsgArea() {
		msgArea.removeAll();
		msgArea.setText(" ");
	}

	/**
	 * a method for resetting the GUI. 
	 * reset the list of selected cards using resetSelected() method from the CardGameTable interface; 
	 * Clear the message area using the clearMsgArea() method from the CardGameTable interface;
	 * Enable user interactions using the enable() method from the CardGameTable interface.
	 * 
	 */
	public void reset() {
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
	}
	/**
	 * A method for enabling user interactions with the GUI. 
	 * Enable the “Play” button and “Pass” button
	 * Enable the BigTwoPanel for selection of cards through mouse clicks.
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}
	/**
	 * A method for enabling user interactions with the GUI. 
	 * disable the “Play” button and “Pass” button 
	 * disable the BigTwoPanel for selection of cards through mouse clicks.
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);	
		}
	/**
	 * Inner class of BigTwoTable that extends Jpanel and implements MouseListener
	 * 
	 * Overrides the paintComponent() method inherited from the JPanel class to draw the card game table.
	 * Implements the mouseClicked() method from the MouseListener interface to handle mouse click events.
	 * 
	 * @author hataemin
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		
		private static final long serialVersionUID = 1L;
		
		private int firsttry=0;
		/**
		 * A method to paint components onto GUI.
		 * 
		 * Draw player Icon and player name.
		 * 
		 * Creates JButton for play and pass, add action Listeners.
		 * 
		 * Puts Message area into grid.
		 * 
		 * Use GridBagConstraints to set layouts of GUI.
		 * 
		 * Store each card image into cardImage array.
		 * Store cardback image.
		 * 
		 * Print Player's cards on table.
		 * 
		 * Print cards on Table.
		 * 
		 * 
		 */
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			
			//Store avatars for player images
			avatars = new Image[4];
			for(int i=0; i<4; i++) {
				String s = i+1 + ".png";
				avatars[i]=new ImageIcon(s).getImage();
			}

			for(int i=0;i<((BigTwoClient)game).getPlayerList().size();i++) {
				if (((BigTwoClient)game).getPlayerList().get(i).getName() != null){
				g.drawImage(avatars[i], 10, 125*i+30, this);
				}
			}
			
			//store cardBack image
			cardBackImage = new ImageIcon("src/cards/b.gif").getImage();
			
			//store card images in array
			cardImages= new Image[4][13];
			
			String temp;
			String suit=null;
			String rank=null;
			for(int i=0;i<4;i++) {
				for(int j=0;j<13;j++) {
					if(i==0) {
						suit="d";
					}
					if(i==1) {
						suit="c";
					}
					if(i==2) {
						suit="h";
					}
					if(i==3) {
						suit="s";
					}
					temp="src/cards/"+rank+suit+".gif";
					if(j==0) {
						rank="a";
						temp="src/cards/"+rank+suit+".gif";
					}
					else if(j==9) {
						rank="t";
						temp="src/cards/"+rank+suit+".gif";
					}
					else if(j==10) {
						rank="j";
						temp="src/cards/"+rank+suit+".gif";
					}
					else if(j==11) {
						rank="q";
						temp="src/cards/"+rank+suit+".gif";
					}
					else if(j==12) {
						rank="k";
						temp="src/cards/"+rank+suit+".gif";
					}
					else {
						temp= "src/cards/"+(j+1)+suit+".gif";
					}
					cardImages[i][j]=new ImageIcon(temp).getImage();
				}
			}
			for(int i=0; i<((BigTwoClient)game).getPlayerList().size(); i++) {
			g.drawLine(0, 150+i*120, frame.getWidth(), 150+i*120);
			if (((BigTwoClient)game).getPlayerList().get(i).getName() != null){
			g.drawString(((BigTwoClient)game).getPlayerList().get(i).getName(), 15, 110+i*125);
			}
			}
			
			//table message
			if(game.getHandsOnTable().size()>0) {
			g.drawString("Played by "+game.getHandsOnTable().get(0).getPlayer().getName(),15,525);
			}
			//Draws cards that players hold, open those activeplayer has. 
			int suitofcard;
			int rankofcard;
			for(int i=0; i<4; i++) {
				if(i==activePlayer) {
					for(int j=0; j<game.getPlayerList().get(activePlayer).getNumOfCards();j++) {
					Card showcard=game.getPlayerList().get(activePlayer).getCardsInHand().getCard(j);
					suitofcard=showcard.getSuit();
					rankofcard=showcard.getRank();
					 
					if(selected[j]==false) {//unselected cards
					g.drawImage(cardImages[suitofcard][rankofcard], 100+j*40, 35+i*120, this);
					}
					else if(selected[j]==true) {//selected cards drawn upper 
						g.drawImage(cardImages[suitofcard][rankofcard], 100+j*40, 15+i*120, this);
					}
					}
				}
				else {
					for(int j=0; j<game.getPlayerList().get(i).getNumOfCards();j++) {
						g.drawImage(cardBackImage, 100+j*40, 35+i*120, this);
					}
				}
			}
			
			//Draws hand played on the table
			if(game.getHandsOnTable().size()>0) {
			for(int i=0; i<game.getHandsOnTable().get(0).size(); i++) {
				game.getHandsOnTable().get(0).getCard(i).getRank();
			suitofcard=game.getHandsOnTable().get(0).getCard(i).getSuit();
			rankofcard=game.getHandsOnTable().get(0).getCard(i).getRank();
			g.drawImage(cardImages[suitofcard][rankofcard], 100+i*40,530, this);
			}
			}
			
			
		}
		/**
		 * Implement mouse event when clicked.
		 * 
		 * Specify the regions of GUI, that is clicked and 
		 * with given data, change selected array to show which is selected.
		 * 
		 * @param e - mouse event
		 */
		public void mouseClicked(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();
			int playeridx=activePlayer;
			
			int ycor=35+playeridx*120;
			
			int ycorend=132+playeridx*120;

			int numcard=game.getPlayerList().get(activePlayer).getNumOfCards();
			//73x97 size of cards
			
			for (int k = 0; k < game.getPlayerList().size(); k++){
			if(k == ((BigTwoClient)game).getPlayerID() && k == game.getCurrentIdx()){
			if(firsttry==0) {
			for(int i=numcard; i>0; i--) {

				if(selected[numcard-1]!=true) {//sets area of selection
				if(my>=ycor && my<=ycorend && mx>=40*(numcard-1)+100 && mx<=40*(numcard-1)+173 ) {
					selected[numcard-1]=true;
					break;
				}
				}
				if(selected[i-1]==true) {
					if(my>=ycorend-30 && my<= ycorend && mx>=40*(i-1)+100 && mx<= 40*(i-1)+173) {
						if(i>1) {
						selected[i-2]=true;}
						break;
					}
				}
				if(i!=numcard) {
					if(my>=ycor && my<=ycorend && mx>=40*(i-1)+100 && mx<=40*(i-1)+140) {
								selected[i-1]=true;
								break;
							}
						}
					
					}
				}
			}
			}
			frame.repaint();
		
		}
		/**
		 * overridden method not used in this program. 
		 * Need to be Overriden because class is implemented
		 */
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		/**
		 * overridden method not used in this program. 
		 * Need to be Overriden because class is implemented
		 */
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * overridden method not used in this program. 
		 * Need to be Overriden because class is implemented
		 */
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * overridden method not used in this program. 
		 * Need to be Overriden because class is implemented
		 */
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub	
		}
	}
	 /**
	  * Class Play Button Listener
	  * 
	  * Inner class for BigTwoTable
	  * 
	  * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Play” button.
	  *  When the “Play” button is clicked, you should call the makeMove() method of your CardGame object to make a move.
	  *  
	  * @author hataemin
	  *
	  */
	class PlayButtonListener implements ActionListener{
		/**
		 * action performed method 
		 * 
		 * when event is listened run makeMove method. then repaint.
		 * 
		 * @param Action event event.
		 */
		public void actionPerformed(ActionEvent event) {
			if(game.getCurrentIdx() == ((BigTwoClient)game).getPlayerID()){

					game.makeMove(game.getCurrentIdx(), getSelected());
			}
			
		}
		
	}
	/**
	 * an inner class that implements the ActionListener
interface. Implements the actionPerformed() method from the ActionListener interface
to handle button-click events for the “Pass” button. When the “Pass” button is clicked,
you should call the makeMove() method of your CardGame object to make a move.

	 * @author hataemin
	 *
	 */
	class PassButtonListener implements ActionListener{
		/**
		 * action performed method 
		 * 
		 * when event is listened run makeMove method with null cardidx.
		 * 
		 * @param Action event event.
		 */
		public void actionPerformed(ActionEvent event) {
			if(game.getCurrentIdx() == ((BigTwoClient)game).getPlayerID()){
			game.makeMove(game.getCurrentIdx(), null);
			}
		}
	}
	/**
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Restart” menu item. 
	 * When the “Restart” menu item is selected, create a new BigTwoDeck object and call its shuffle() method; 
	 * and call the start() method of your CardGame object with the BigTwoDeck object as an argument.
	 * 
	 * @author hataemin
	 *
	 */
	class ConnectMenuItemListener implements ActionListener{
		/**
		 * action performed method 
		 * 
		 * when event is listened, restarts the game.
		 * 
		 * @param Action event event.
		 */
		public void actionPerformed(ActionEvent event) {	

			String playername = new String();
			
			while(playername.equals("")){
	     		playername = JOptionPane.showInputDialog("Enter Name: ");
	     		if(playername == null){
	     			System.exit(0);
	     		}
	       	}
	     	((BigTwoClient)game).setPlayerName(playername);

	     	((BigTwoClient)game).setServerIP("127.0.0.1");
	 		((BigTwoClient)game).setServerPort(2396);
	 		((BigTwoClient)game).makeConnection();
		}	
		
	}
	/**
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item. 
	 * When the “Quit” menu item is selected, terminate your application. 
	 * 
	 * @author hataemin
	 *
	 */
	class QuitMenuItemListener implements ActionListener{
		/**
		 * action performed method 
		 * 
		 * when event is listened, quit the program..
		 * 
		 * @param Action event event.
		 */
		public void actionPerformed(ActionEvent event) {	
			System.exit(0);
		}	
	}
	/**
	 * an inner class that implements the keylistener interface. 
	 * Implements the keyPressed() method from the keylistener interface to handle events for the “enter” key.
	 * When the enter key is pressed, sends message type msg to server and empty the chat input area. 
	 * 
	 * @author hataemin
	 *
	 */
	class EnterKeyListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				String s = chatInput.getText();
				CardGameMessage msg = new CardGameMessage(CardGameMessage.MSG, -1, s);
				((BigTwoClient)game).sendMessage(msg);
				chatInput.setText("");
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
	
	}
	/**
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle click events for the “Submit” button. 
	 * When the “Submit” button item is clicked, sends message on the chat input area to server. 
	 * Make chat input empty after sending
	 * 
	 * @author hataemin
	 *
	 */
	class SubmitButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			String s = chatInput.getText();
			CardGameMessage msg = new CardGameMessage(CardGameMessage.MSG, -1, s);
			((BigTwoClient)game).sendMessage(msg);
			chatInput.setText("");
		}
	}
	
}
