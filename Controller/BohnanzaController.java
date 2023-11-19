/*Megan Chun

 * 
 * 
 * 
 */
package Controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import Model.Card;
import Model.HumanPlayer;
import Model.Player;
import View.GameFrame;
import View.PlayerHandPanel;
import View.PlayerPanel;

public class BohnanzaController implements ActionListener {
	
	private static Player playerOne;
	private static Player playerTwo;
	
	private static GameFrame gameFrame;
	GUIController gui = new GUIController();
	DeckController deck = new DeckController();
	
	private Player currentPlayer;

	private int numOfCardsDrawed = 0;
	private int numOfDeckClicks = 0;
	private int numOfPlants = 0;
	
	Card cardDiscard;
	
	public BohnanzaController() {
		
		Card bean1 = deck.getNumCardMap().get(1);
		Card bean2 = deck.getNumCardMap().get(2);
		Card bean3 = deck.getNumCardMap().get(3);
		Card bean4 = deck.getNumCardMap().get(4);
		
		//give player cards in their hand
		Queue<Card> q = new LinkedList<>();
		q.add(bean1);
		q.add(bean1);
		q.add(bean4);

		Queue<Card> q1 = new LinkedList<>();
		q1.add(bean3);
		q1.add(bean4);
		
		Card[] b = {bean1,bean2,bean3};
		int[] numB = {0,0,0};
		
		//-------------------------------------------------
		//create two players
		playerOne = new HumanPlayer("Megan",q,b,numB,false,0,-1);
		playerTwo = new Player("Katherine",q1,b,numB,false,0,-1);
		
		gameFrame = new GameFrame();

		playerOne.setPanel(gameFrame.getPlayerOnePanel());
		playerTwo.setPanel(gameFrame.getPlayerTwoPanel());
		
		addActionListeners();
		
		currentPlayer = playerOne;
		gui.disableActivePlayerComponents(playerOne);
		gui.disableInactivePlayerComponents(playerTwo);
		
		currentPlayer.setCurrentStage(1);
		
		
	}
	public void setUpPlayer(Player player) {
		
	}
	public static Player getPlayerOne() {
		return playerOne;
	}
	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}
	public static Player getPlayerTwo() {
		return playerTwo;
	}
	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public static GameFrame getGameFrame() {
		return gameFrame;
	}
	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
	public GUIController getGui() {
		return gui;
	}
	public void setGui(GUIController gui) {
		this.gui = gui;
	}
	
	public void addActionListeners() {
		
		//add action listeners for both panels 
		
		PlayerPanel[] panels = {gameFrame.getPlayerOnePanel(),gameFrame.getPlayerTwoPanel()};
		
		for (PlayerPanel p : panels) {
			
			p.getHand().getPlantBtn().addActionListener(this);
			p.getHand().getDiscardBtn().addActionListener(this);
			
			if (!playerOne.getHand().isEmpty()) {
				for (JRadioButton b : playerOne.getPanel().getHand().getCardsInHand()) {
					b.addActionListener(this);
				}
			}
			if (!playerTwo.getHand().isEmpty()) {
				for (JRadioButton b : playerTwo.getPanel().getHand().getCardsInHand()) {
					b.addActionListener(this);
				}
			}
			
			for (int i = 0; i < 3; i++) {
				p.getField().getActionBtns()[i].addActionListener(this);
			}
		}
		
		//action listeners for the common panel
		gameFrame.getCommonPanel().getDeck().addActionListener(this);
		gameFrame.getCommonPanel().getGarbageDeck().addActionListener(this);
		gameFrame.getCommonPanel().getDiscardButton().addActionListener(this);
		
		//Aaron ----
		for (int i = 0; i < 3; i++) {
			int index = i;
			gameFrame.getCommonPanel().getSlots()[0][i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Option " + (index + 1) + " Added To Hand");
					gameFrame.getCommonPanel().getSlots()[0][index].setEnabled(false);
				}
			});
		}
		//-----------
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
		Player playerAction;
		
		//check which panel the button was clicked in 
		if (((Component) e.getSource()).getParent().getParent().getParent().getY() == 0) {
			playerAction = playerOne;
		}
		else {
			playerAction = playerTwo;
		}
		
		
		
		//STEP 1: PLANT OR DISCARD LAST PLAYERS REMAINING DRAWED CARDS
		//------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 1) {
			if (e.getSource() == gameFrame.getCommonPanel().getDiscardButton()) {
				currentPlayer.setCurrentStage(2);
				
				//unlock plant button for step 2
				currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(true);
				currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(true);
				for (JButton b : currentPlayer.getPanel().getField().getActionBtns()) {
					b.setEnabled(true);
				}
				gameFrame.getCommonPanel().getDiscardButton().setEnabled(false);
				gui.enablePlayersHand(currentPlayer);
			}
		}
		

		//STEP 2: PLANT OR DISCARD LAST PLAYERS REMAINING DRAWED CARDS
		//------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 2) {
			
			//if the player presses the plant button
			if (e.getSource() == currentPlayer.getPanel().getHand().getPlantBtn()) {
				
				//if they have planted 2 or less times
				if (numOfPlants < 2) {
						
					if (currentPlayer.getPanel().getHand().getCardsInHand()[0].isSelected()){
						
						HumanPlayer player = (HumanPlayer) currentPlayer;
				
						//if the plant is a valid move on the field
						if (player.plant(currentPlayer.getHand().peek())) {
							
							//update the hand
							numOfPlants++;
							
							//update the field
							gui.updateField(player, currentPlayer.getHand().peek());
							
							currentPlayer.getHand().remove();
							
							gui.updateHand(currentPlayer);
							
							JOptionPane.showMessageDialog(gameFrame, currentPlayer.getName() + " Planted","Bean Planted!",
									    JOptionPane.INFORMATION_MESSAGE, currentPlayer.getPanel().getHand().getPlantIcon());

						}
						else {
							JOptionPane.showMessageDialog(gameFrame, 
									"You can not plant this bean! Ensure your field has room.",
									"Unable To Process Request",JOptionPane.ERROR_MESSAGE);
						}
					
						
					}
					//else show an error message
					else {
						JOptionPane.showMessageDialog(gameFrame, 
								"You can not plant this bean! Choose the bean at the front of your hand",
								"Unable To Process Request",JOptionPane.ERROR_MESSAGE);
					}
					
					
				}
				else {
					JOptionPane.showMessageDialog(gameFrame, 
							"You can only plant a maximum of two beans!",
							"Unable To Process Request",JOptionPane.ERROR_MESSAGE);
				}
					
		}
	
			//if the player clicks the discard button
			if (e.getSource() == playerAction.getPanel().getHand().getDiscardBtn()) {
				
				Queue<Card> tempHand = new LinkedList();
				
				//check through each button in the players hand
				for (JRadioButton b : playerAction.getPanel().getHand().getCardsInHand()) {
					//if a card is selected call the checkCardTypeSelected method
					if (b.isSelected()) {
						cardDiscard = checkCardTypeSelected(b.getIcon().toString());
						deck.addToDiscard(cardDiscard);
					}
					
				}
				
				for (Card c : currentPlayer.getHand()) {
					if (c != cardDiscard) {
						tempHand.add(c);
					}
				}
				
				System.out.println("cards after discard:");
				for (Card c : currentPlayer.getHand()) {
					System.out.println(c.getBeanType());
				}
				
				currentPlayer.setCurrentStage(3);
				
				JOptionPane.showMessageDialog(gameFrame,
					   currentPlayer.getName() + " Discarded " + cardDiscard.getBeanType() +" Bean");
				
				//disable components for step 2
				currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(false);
				currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(false);
				gui.enablePlayersHand(currentPlayer);
				numOfPlants = 0; //reset number of plants counter
				currentPlayer.setCurrentStage(3);
			}
			
			
		}
		
		//STEP 3: EXTENDED TURN
		//------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 3) {
			if (e.getSource() == gameFrame.getCommonPanel().getDeck() && currentPlayer.getCurrentStage() == 3) {
				
				displayCards(true);
				gameFrame.getCommonPanel().getDiscardButton().setEnabled(true);
			}
			if (e.getSource() == gameFrame.getCommonPanel().getDiscardButton()) { //CHANGE TO DONE BUTTON
				JOptionPane.showMessageDialog(gameFrame,
						   currentPlayer.getName() + currentPlayer + " finished their extended turn, draw 2 cards!");
				currentPlayer.setCurrentStage(4);
			}
		}
		
		//STEP 4: PICKING UP TWO CARDS
		//------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 4) {
			
			if (e.getSource() == gameFrame.getCommonPanel().getDeck()) {
				
				numOfCardsDrawed++;
				
				//if the player picks up 2 cards, it signaled the end of their turn
				if (numOfCardsDrawed == 2) {
					
					//return the current player to the first stage
					currentPlayer.setCurrentStage(1);
					
					gui.disableActivePlayerComponents(currentPlayer);
					
					//determine who's turn it is now
					if (currentPlayer == playerOne)	 {
						currentPlayer = playerTwo;
					}
					else {
						currentPlayer = playerOne;
					}
					
					currentPlayer.setCurrentStage(1);
					
					numOfCardsDrawed = 0; //reset number of cards drawed
					
					JOptionPane.showMessageDialog(gameFrame, currentPlayer.getName() +"'s Turn. You can choose to plant or discard the cards in the middle to procced.");
					
				}
			}
		}
		
		
		//EVENTS FOR SELLING--------------------------------------------------------------------
		if (e.getSource() == playerAction.getPanel().getField().getActionBtns()[0] ||
				e.getSource() == playerAction.getPanel().getField().getActionBtns()[1]) {
			JOptionPane.showMessageDialog(gameFrame, playerAction.getName() + " Sold Their Beans");
			
			if (e.getSource() == playerAction.getPanel().getField().getActionBtns()[0]) 
				gui.updateSell(playerAction, 0);
			else 
				gui.updateSell(playerAction, 1);
		}
		
		//if the player clicks the third field
		if (e.getSource() == playerAction.getPanel().getField().getActionBtns()[2]) {
		
			//if the third field is not owned
			if (playerAction.isThirdFieldOwned() == false) {
				//if the player can't afford the field
				if (playerAction.getScore() < -1) {
					JOptionPane.showMessageDialog(gameFrame, 
							"You Can Not Afford This Field","Unable To Process Request",JOptionPane.ERROR_MESSAGE);
				
				}
				else {
					gui.unlockField(playerAction);
					JOptionPane.showMessageDialog(gameFrame, playerAction.getName() + " Bought A Field");
				}
			}
			//if the third field is owned
			else {
				JOptionPane.showMessageDialog(gameFrame, playerAction.getName() + " Sold Their Beans");
			}
		}
		
	}
	
	public Card checkCardTypeSelected(String fileName) {
		
		for (Map.Entry<Integer,Card> entry : deck.getNumCardMap().entrySet())   {
			
			String file = "Images/Beans/"+entry.getValue().getFileName();
			
			if (file.equals(fileName)) {
				return entry.getValue();
			}
		}
		return null;
		
	}
	
	//Aaron Su
	public void displayCards(boolean condition) {
		// loop through the slots JButtons
		for (int i = 0; i < 3; i++) {
			gameFrame.getCommonPanel().getSlots()[0][i].setEnabled(condition);
		}
	}

	
	
}