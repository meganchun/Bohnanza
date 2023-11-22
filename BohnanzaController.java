/*Megan Chun, Aaron Su

 * 
 * 
 * 
 */
package Controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import Model.Card;
import Model.HumanPlayer;
import Model.Player;
import View.GameFrame;
import View.PlayerPanel;

public class BohnanzaController implements ActionListener {

	private static Player playerOne;
	private static Player playerTwo;

	private static GameFrame gameFrame;
	GUIController gui = new GUIController();
	DeckController deck = new DeckController();

	private Player currentPlayer;

	private int numOfCardsDrawed = 0;
	private int[] numCardsInSlot = new int[3];
	private int numOfPlants = 0;

	private boolean firstTurn = true;

	Card cardDiscard;

	Queue<Card> q = new LinkedList<>();
	Queue<Card> q1 = new LinkedList<>();

	public BohnanzaController() {

		// give player cards in their hand
		// alternate dealing by checking the even / odd iteration of the loop
		for (int i = 1; i <= 10; i++) {
			if (i % 2 != 0) {
				q.add(deck.pop());
			}

			else {
				q1.add(deck.pop());
			}
		}

		// set up the players field
		Map<Card, Integer> m = new HashMap<Card, Integer>();

//		m.put(bean1, 2);
//		m.put(bean3, 5);

		// -------------------------------------------------
		// create two players
		playerOne = new HumanPlayer("Megan", q, m, false, 0, -1);
		playerTwo = new Player("Katherine", q1, m, false, 0, -1);

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

		// add action listeners for both panels

		PlayerPanel[] panels = { gameFrame.getPlayerOnePanel(), gameFrame.getPlayerTwoPanel() };

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

		// action listeners for the common panel
		gameFrame.getCommonPanel().getDeck().addActionListener(this);
		gameFrame.getCommonPanel().getDiscardDeck().addActionListener(this);
		gameFrame.getCommonPanel().getDiscardButton().addActionListener(this);
		gameFrame.getCommonPanel().getEndExtendBtn().addActionListener(this);

		// Create action listeners for each of the 3 cards drawn from the deck
		for (int i = 0; i < 3; i++) {
			int index = i;
			gameFrame.getCommonPanel().getSlots()[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// add the cards to hand and disable them so they can't be added twice
					currentPlayer.getHand().add(
							checkCardTypeSelected(gameFrame.getCommonPanel().getSlots()[index].getIcon().toString()));
					numCardsInSlot[index] = 0;
					gameFrame.getCommonPanel().getNumCardLabel()[index].setText(String.valueOf(numCardsInSlot[index]));

					currentPlayer.getPanel().getHand().addCards(currentPlayer);
					gameFrame.getCommonPanel().getSlots()[index].setEnabled(false);
				}
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Player playerAction;

		// check which panel the button was clicked in
		if (((Component) e.getSource()).getParent().getParent().getParent().getY() == 0) {
			playerAction = playerOne;
		}

		else {
			playerAction = playerTwo;
		}

		// STEP 1: PLANT OR DISCARD LAST PLAYERS REMAINING DRAWN CARDS
		// ------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 1) {

			// if it is the first turn of the entire game, skip this stage
			if (firstTurn == true) {
				firstTurn = false;
				currentPlayer.setCurrentStage(2);

				// unlock plant button for step 2
				currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(true);
				currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(true);
				for (JButton b : currentPlayer.getPanel().getField().getActionBtns()) {
					b.setEnabled(true);
				}

				gameFrame.getCommonPanel().getDiscardButton().setEnabled(false);
				gameFrame.getCommonPanel().getDeck().setEnabled(false);
				gui.enablePlayersHand(currentPlayer);
			}

			// otherwise, perform the following
			else {

				// if the player decides to discard the remaining cards in the common panel
				if (e.getSource() == gameFrame.getCommonPanel().getDiscardButton()) {
					// change all the card icons to a temp card, and then disable them...
					// make sure to check if it is not a temp card because we do not want to fill
					// our discard pile with useless cards
					for (int i = 0; i < 3; i++) {
						if (!(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString()
								.equals("Images/slotsBtn.png"))) {

							// check if there are multiple of the same type of card in the slots
							if (numCardsInSlot[i] > 1) {
								for (int x = 1; x <= numCardsInSlot[i]; x++) {
									cardDiscard = checkCardTypeSelected(
											gameFrame.getCommonPanel().getSlots()[i].getIcon().toString());
									deck.addToDiscard(cardDiscard);
								}
							}
							
							else {
								cardDiscard = checkCardTypeSelected(
										gameFrame.getCommonPanel().getSlots()[i].getIcon().toString());
								deck.addToDiscard(cardDiscard);
							}

							// reset the images for the slots
							gameFrame.getCommonPanel().getDiscardDeck()
									.setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getBeanType() + ".png"));
						}

						numCardsInSlot[i] = 0;
						gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
						gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));
						gameFrame.getCommonPanel().getSlots()[i].setEnabled(false);
					}

					currentPlayer.setCurrentStage(2);

					// unlock plant button for step 2
					currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(true);
					currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(true);
					for (JButton b : currentPlayer.getPanel().getField().getActionBtns()) {
						b.setEnabled(true);
					}

					gameFrame.getCommonPanel().getDiscardButton().setEnabled(false);
					gui.enablePlayersHand(currentPlayer);
				}
			}
		}

		// STEP 2: PLANT OR DISCARD LAST PLAYERS REMAINING DRAWED CARDS
		// ------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 2) {

			// if the player presses the plant button
			if (e.getSource() == currentPlayer.getPanel().getHand().getPlantBtn()) {

				// if they have planted 2 or less times
				if (numOfPlants < 2) {

					// if the player selects the first card in their hand
					if (currentPlayer.getPanel().getHand().getCardsInHand()[0].isSelected()) {
						System.out.println(currentPlayer.getPanel().getHand().getCardsInHand()[0].getIcon());
						JOptionPane.showMessageDialog(gameFrame, currentPlayer.getName() + " Planted", "Bean Planted!",
								JOptionPane.INFORMATION_MESSAGE, currentPlayer.getPanel().getHand().getPlantIcon());

						numOfPlants++;
					}
					// else show an error message
					else {
						JOptionPane.showMessageDialog(gameFrame,
								"You can not plant this bean! Choose the bean at the front of your hand",
								"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(gameFrame, "You can only plant a maximum of two beans!",
							"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
				}

			}

			// if the player clicks the discard button in the player frame
			if (e.getSource() == currentPlayer.getPanel().getHand().getDiscardBtn()) {

				// check through each button in the players hand
				for (JRadioButton b : playerAction.getPanel().getHand().getCardsInHand()) {
					// if a card is selected call the checkCardTypeSelected method
					if (b.isSelected()) {
						cardDiscard = checkCardTypeSelected(b.getIcon().toString());
						deck.addToDiscard(cardDiscard);
						gameFrame.getCommonPanel().getDiscardDeck()
								.setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getBeanType() + ".png"));
					}

				}

				currentPlayer.setCurrentStage(3);

//				JOptionPane.showMessageDialog(gameFrame,
//						currentPlayer.getName() + " Discarded " + cardDiscard.getBeanType() + " Bean");

				// disable components for step 2
				currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(false);
				currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(false);
				gui.enablePlayersHand(currentPlayer);
				numOfPlants = 0; // reset number of plants counter
				currentPlayer.setCurrentStage(3);
			}

		}

		// STEP 3: EXTENDED TURN
		// ------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 3) {
			gameFrame.getCommonPanel().getDeck().setEnabled(true);

			if (e.getSource() == gameFrame.getCommonPanel().getDeck() && currentPlayer.getCurrentStage() == 3) {
				gameFrame.getCommonPanel().getDeck().setEnabled(false);

				// pop a card from the deck and visually display on one of the 3 slots for the
				// player to choose from
				for (int i = 0; i < 3; i++) {
					gameFrame.getCommonPanel().getSlots()[i]
							.setIcon(new ImageIcon("Images/Beans/" + deck.pop().getFileName()));
					numCardsInSlot[i] += 1;
					gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));

					validateCards(i);

					// check if the top card on the discard pile is the same as one of the cards in
					// the slots...
					// will result in disabling the options, and waiting until the deck is called
					if (gameFrame.getCommonPanel().getDiscardDeck().getIcon().toString()
							.equals(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString())) {
						gameFrame.getCommonPanel().getDiscardDeck().setEnabled(true);

						setEnabled(false);
						break;
					}

					else {
						gameFrame.getCommonPanel().getSlots()[i].setEnabled(true);
					}
				}

				gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(true);
			}

			// signify the end of their extended turn
			if (e.getSource() == gameFrame.getCommonPanel().getEndExtendBtn()) {
				gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(false);

				// loop through all the slots and revert the cards chosen to the original
				// image...
				// for cards that were not chosen, disable them for the next player to use
				for (int i = 0; i < 3; i++) {
					if (gameFrame.getCommonPanel().getSlots()[i].isEnabled() == false) {
						gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));
					}

					else {
						gameFrame.getCommonPanel().getSlots()[i].setEnabled(false);
					}
				}

				JOptionPane.showMessageDialog(gameFrame,
						currentPlayer.getName() + currentPlayer + " finished their extended turn, draw 2 cards!");
				currentPlayer.setCurrentStage(4);
			}
		}

		// STEP 4: PICKING UP TWO CARDS
		// ------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 4) {

			if (e.getSource() == gameFrame.getCommonPanel().getDeck()) {

				// pop the card from the deck stack, and add it to the players' hand
				Card newCard = deck.pop();
				currentPlayer.getHand().add(newCard);
				numOfCardsDrawed++;

				// if the player picks up 2 cards, it signaled the end of their turn
				if (numOfCardsDrawed == 2) {

					// return the current player to the first stage
					currentPlayer.setCurrentStage(1);

					// reset all the common panels
					gameFrame.getCommonPanel().getDeck().setEnabled(false);
					gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(false);
					gameFrame.getCommonPanel().getDiscardButton().setEnabled(true);

					gui.disableActivePlayerComponents(currentPlayer);

					// determine who's turn it is now
					if (currentPlayer == playerOne) {
						currentPlayer = playerTwo;
					} else {
						currentPlayer = playerOne;
					}

					currentPlayer.setCurrentStage(1);
					gui.enablePlayersHand(currentPlayer);

					numOfCardsDrawed = 0; // reset number of cards drawn

					JOptionPane.showMessageDialog(gameFrame, currentPlayer.getName()
							+ "'s Turn. You can choose to plant or discard the cards in the middle to procced.");

					// loop through the remaining cards and enable them for the next player to pick
					for (int i = 0; i < 3; i++) {
						// if the image of the card does not equal to a temporary image, as in it was
						// not picked during the previous turn,
						// enable the card

						if (!(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString()
								.equals("Images/slotsBtn.png"))) {
							gameFrame.getCommonPanel().getSlots()[i].setEnabled(true);
						}

					}
				}
			}
		}

		// EVENTS FOR SELLING
		if (e.getSource() == playerAction.getPanel().getField().getActionBtns()
				|| e.getSource() == playerAction.getPanel().getField().getActionBtns()[1]) {
			JOptionPane.showMessageDialog(gameFrame, playerAction.getName() + " Sold Their Beans");
		}

		// if the player clicks the third field
		if (e.getSource() == playerAction.getPanel().getField().getActionBtns()[2]) {

			// if the third field is not owned
			if (playerAction.isThirdFieldOwned() == false) {
				// if the player can't afford the field
				if (playerAction.getScore() < -1) {
					JOptionPane.showMessageDialog(gameFrame, "You Can Not Afford This Field",
							"Unable To Process Request", JOptionPane.ERROR_MESSAGE);

				} else {
					gui.unlockField(playerAction);
					JOptionPane.showMessageDialog(gameFrame, playerAction.getName() + " Bought A Field");
				}
			}

			// if the third field is owned
			else {
				JOptionPane.showMessageDialog(gameFrame, playerAction.getName() + " Sold Their Beans");
			}
		}

		// EVENTS FOR STACKING CARDS FROM THE DISCARD PILE

		// check if the discard deck is being clicked...
		// this is used to signify when a card from the discard pile is the same as one
		// of the drawn cards
		if (e.getSource() == gameFrame.getCommonPanel().getDiscardDeck()) {
			// previous instance of the discarded card
			cardDiscard = deck.popFromDiscard();

			// increment the number of cards in that slot if it equals to the previous
			// discarded card object
			for (int i = 0; i < 3; i++) {
				if (gameFrame.getCommonPanel().getSlots()[i].getIcon().toString()
						.equals("Images/Beans/" + cardDiscard.getFileName())) {
					numCardsInSlot[i] += 1;
					gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
				}
			}

			// new instance of the discarded card
			if (deck.getDiscardList().isEmpty()) {
				gameFrame.getCommonPanel().getDiscardDeck().setIcon(new ImageIcon("Images/discardDeck.png"));
			}

			else {
				cardDiscard = deck.popFromDiscard();
				gameFrame.getCommonPanel().getDiscardDeck()
						.setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getFileName()));
			}

			// loop to double check no cards match that of the top card in the discarded
			// pile before moving on
			for (int i = 0; i < 3; i++) {
				if (!(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString()
						.equals(gameFrame.getCommonPanel().getDiscardDeck().getIcon().toString()))) {
					gameFrame.getCommonPanel().getDiscardDeck().setEnabled(false);
					setEnabled(true);
				}
			}
		}
	}

	public Card checkCardTypeSelected(String fileName) {

		for (Map.Entry<Integer, Card> entry : deck.getNumCardMap().entrySet()) {

			String file = "Images/Beans/" + entry.getValue().getFileName();

			if (file.equals(fileName)) {
				return entry.getValue();
			}
		}

		return null;
	}

	public void setEnabled(boolean condition) {
		for (int i = 0; i < 3; i++) {
			if (gameFrame.getCommonPanel().getSlots()[i].getIcon().toString().equals("Images/slotsBtn.png")) {
				gameFrame.getCommonPanel().getSlots()[i]
						.setIcon(new ImageIcon("Images/Beans/" + deck.pop().getFileName()));
				numCardsInSlot[i] += 1;
				gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));

				validateCards(i);
			}

			gameFrame.getCommonPanel().getSlots()[i].setEnabled(condition);
			;
		}
	}

	private void validateCards(int i) {
		for (int x = 0; x < 3; x++) {

			// check if there are duplicates of cards in the slots
			if (gameFrame.getCommonPanel().getSlots()[x].getIcon().toString()
					.equals(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString()) && x != i) {

				numCardsInSlot[i] += 1;
				gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
				gameFrame.getCommonPanel().getSlots()[x]
						.setIcon(new ImageIcon("Images/Beans/" + deck.pop().getFileName()));
			}

			// if it is still equal, recursively call the method again to perform the
			// actions
			if ((gameFrame.getCommonPanel().getSlots()[x].getIcon().toString()
					.equals(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString())) && numCardsInSlot[i] > 1
					&& x != i) {
				validateCards(i);
			}
		}
	}

}