//Megan Chun, Aaron Su

package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import Model.AIPlayer;
import Model.Card;
import Model.HumanPlayer;
import Model.Player;
import View.GameEnd;
import View.GameFrame;
import View.ModeSelectFrame;
import View.PlayerPanel;

public class BohnanzaController implements ActionListener {

	private static Player playerOne;
	private static Player playerTwo;

	private static GameFrame gameFrame;
	GUIController gui = new GUIController();
	DeckController deck = new DeckController();
	AIController ai;

	private Player currentPlayer;

	private int numOfCardsDrawed = 0;
	private int[] numCardsInSlot = new int[3];
	private int numOfPlants = 0;

	private boolean firstTurn = true;

	Queue<Card> q = new LinkedList<>();
	Queue<Card> q1 = new LinkedList<>();

	public static final String[] STAGES = { "Plant/Discard Offered Cards", "Plant and Discard", "Extended Turn",
			"Pick Up Two Cards" };

	Card cardDiscard;

	public BohnanzaController(String mode) {

		playMusic("Audio/backgroundMusic.wav");

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

		Card[] b1 = { deck.getNumCardMap().get(1), deck.getNumCardMap().get(2), deck.getNumCardMap().get(3) };
		Card[] b2 = { deck.getNumCardMap().get(1), deck.getNumCardMap().get(2), deck.getNumCardMap().get(3) };

		int[] numB1 = { 0, 0, 0 };
		int[] numB2 = { 0, 0, 0 };

		// -------------------------------------------------
		// create two players
		if (mode.equals("pvp")) {
			playerOne = new HumanPlayer(ModeSelectFrame.strNameOne, q, b1, numB1, false, 0, 1);
			playerTwo = new HumanPlayer(ModeSelectFrame.strNameTwo, q1, b2, numB2, false, 0, 1);
		} else if (mode.equals("easy")) {
			playerOne = new HumanPlayer(ModeSelectFrame.strNameOneAI, q, b1, numB1, false, 0, 1);
			playerTwo = new AIPlayer("AI Player", q1, b2, numB2, false, 0, 1, "easy");

		} else {
			playerOne = new HumanPlayer(ModeSelectFrame.strNameOneAI, q, b1, numB1, false, 0, 1);
			playerTwo = new AIPlayer("AI Player", q1, b2, numB2, false, 0, 1, "hard");

		}

		// create a new game frame
		gameFrame = new GameFrame();

		// initalize the AI controller
		if (mode.equals("easy"))
			ai = new AIController("easy", gameFrame, gui, deck, this);
		else
			ai = new AIController("hard", gameFrame, gui, deck, this);

		// set the panels to the corresponding players
		playerOne.setPanel(gameFrame.getPlayerOnePanel());
		playerTwo.setPanel(gameFrame.getPlayerTwoPanel());

		// add action listeners to common panel
		// action listeners for the common panel
		gameFrame.getCommonPanel().getDeckCounter().setText(String.valueOf(deck.getCardList().size()));
		gameFrame.getCommonPanel().getDeck().addActionListener(this);
		gameFrame.getCommonPanel().getDiscardButton().addActionListener(this);
		gameFrame.getCommonPanel().getEndExtendBtn().addActionListener(this);
		gameFrame.getCommonPanel().getDiscardDeck().addActionListener(this);

		// Create action listeners for each of the 3 cards drawn from the deck
		for (int i = 0; i < 3; i++) {
			int index = i;
			gameFrame.getCommonPanel().getSlots()[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// add the cards to hand and disable them so they can't be added twice

					Card cardSelected = checkCardTypeSelected(
							gameFrame.getCommonPanel().getSlots()[index].getIcon().toString());

					System.out.println(cardSelected.getBeanType());

					HumanPlayer player = (HumanPlayer) currentPlayer;

					// if the plant is a valid move on the field
					if (player.plant(cardSelected)) {
						// update the field
						gui.updateField(currentPlayer, cardSelected, numCardsInSlot[index]);

						numCardsInSlot[index] = 0;
						gameFrame.getCommonPanel().getNumCardLabel()[index].setText("0");
						gameFrame.getCommonPanel().getSlots()[index].setEnabled(false);
						gameFrame.getCommonPanel().getSlots()[index].setIcon(new ImageIcon("Images/slotsBtn.png"));

						JOptionPane.showMessageDialog(gameFrame, currentPlayer.getName() + " Planted", "Bean Planted!",
								JOptionPane.INFORMATION_MESSAGE, currentPlayer.getPanel().getHand().getPlantIcon());
					} else {
						JOptionPane.showMessageDialog(gameFrame,
								"You can not plant this bean! Ensure your field has room.", "Unable To Process Request",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			});
		}
		/// ----

		// if player versus player, add action listeners to both panels
		if (mode.equals("pvp")) {

			addActionListeners(playerOne);
			addActionListeners(playerTwo);
		}
		// if it is player versus computer, only add action listeners to the first
		// player panel
		else {
			addActionListeners(playerOne);
		}

		// set the current player to player one
		currentPlayer = playerOne;

		// activate and disable components for the two players
		gui.enablePlayersHand(playerOne);
		gui.disableInactiveComponents(playerTwo);

		currentPlayer.setCurrentStage(1);

	}

	public void setUpPlayer(Player player) {

	}

	public static Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(HumanPlayer playerOne) {
		this.playerOne = playerOne;
	}

	public static Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(HumanPlayer playerTwo) {
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

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void addActionListeners(Player player) {

		// add action listeners for both panels

		PlayerPanel p = player.getPanel();

		p.getHand().getPlantBtn().addActionListener(this);
		p.getHand().getDiscardBtn().addActionListener(this);
		p.getHand().getDoneBtn().addActionListener(this);

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

		// -----------
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		playMusic("Audio/buttonSound.wav");

		// STEP 1: PLANT OR DISCARD LAST PLAYERS REMAINING DRAWED CARDS
		// ------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 1) {

			// if it is the first turn of the entire game, skip this stage
			if (firstTurn) {

				firstTurn = false;
				currentPlayer.setCurrentStage(2);
				gameFrame.getCommonPanel().getCurrentStage().setText(STAGES[1]);

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
							cardDiscard = checkCardTypeSelected(
									gameFrame.getCommonPanel().getSlots()[i].getIcon().toString());
							deck.addToDiscard(cardDiscard);
							gameFrame.getCommonPanel().getDiscardDeck()
									.setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getBeanType() + ".png"));
						}

						gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));
						gameFrame.getCommonPanel().getSlots()[i].setEnabled(false);
					}

					// reset the slots counter
					for (int i = 0; i < 3; i++) {
						gameFrame.getCommonPanel().getNumCardLabel()[i].setText("0");
						numCardsInSlot[i] = 0;
					}

					currentPlayer.setCurrentStage(2);

					// unlock plant button for step 2
					currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(true);
					currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(true);
					currentPlayer.getPanel().getHand().getDoneBtn().setEnabled(true);

					for (JButton b : currentPlayer.getPanel().getField().getActionBtns()) {
						b.setEnabled(true);
					}

					gameFrame.getCommonPanel().getDiscardButton().setEnabled(false);
					gui.enablePlayersHand(currentPlayer);

					gameFrame.getCommonPanel().getCurrentStage().setText(STAGES[1]);
				}
			}
		}

		// STEP 2: PLANT OR DISCARD OWN CARDS
		// ------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 2) {

			// if the player presses the plant button
			if (e.getSource() == currentPlayer.getPanel().getHand().getPlantBtn()) {

				// if they have planted 2 or less times
				if (numOfPlants < 2) {

					if (currentPlayer.getPanel().getHand().getCardsInHand().get(0).isSelected()) {

						HumanPlayer player = (HumanPlayer) currentPlayer;

						// if the plant is a valid move on the field
						if (player.plant(currentPlayer.getHand().peek())) {

							// update the hand
							numOfPlants++;

							// update the field
							gui.updateField(currentPlayer, currentPlayer.getHand().peek(), 1);

							currentPlayer.getHand().remove();

							gui.updateHand(currentPlayer);

							JOptionPane.showMessageDialog(gameFrame, currentPlayer.getName() + " Planted",
									"Bean Planted!", JOptionPane.INFORMATION_MESSAGE,
									currentPlayer.getPanel().getHand().getPlantIcon());

							if (numOfPlants == 2)
								currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(false);

						} else {
							JOptionPane.showMessageDialog(gameFrame,
									"You can not plant this bean! Ensure your field has room.",
									"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
						}

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

			// if the player clicks the DISCARD button
			if (e.getSource() == currentPlayer.getPanel().getHand().getDiscardBtn()) {

				Queue<Card> tempHand = new LinkedList();

				int index = 0;
				int indexOfDiscard = 0;

				// check through each button in the players hand
				for (JRadioButton b : currentPlayer.getPanel().getHand().getCardsInHand()) {
					// if a card is selected call the checkCardTypeSelected method
					if (b.isSelected()) {
						indexOfDiscard = index;
						cardDiscard = checkCardTypeSelected(b.getIcon().toString());
						deck.addToDiscard(cardDiscard);

					}
					++index;
				}

				gameFrame.getCommonPanel().getDiscardDeck()
						.setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getFileName()));

				index = 0;

				for (Card c : currentPlayer.getHand()) {
					if (index != indexOfDiscard) {
						tempHand.add(c);
					}
					++index;
				}

				currentPlayer.setHand(tempHand);

				// update the hand with the removed card
				gui.updateHand(currentPlayer);

				currentPlayer.setCurrentStage(3);

				// disable components for step 2
				currentPlayer.getPanel().getHand().getDoneBtn().setEnabled(false);
				currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(false);
				currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(false);

				gui.enablePlayersHand(currentPlayer);

				currentPlayer.setCurrentStage(3);
				gameFrame.getCommonPanel().getCurrentStage().setText(STAGES[2]);

				JOptionPane.showMessageDialog(gameFrame,
						currentPlayer.getName() + " Discarded " + cardDiscard.getBeanType() + " Bean");

				currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(false);
			}

			if (e.getSource() == currentPlayer.getPanel().getHand().getDoneBtn()) {

				currentPlayer.setCurrentStage(3);

				// disable components for step 2
				currentPlayer.getPanel().getHand().getDoneBtn().setEnabled(false);
				currentPlayer.getPanel().getHand().getDiscardBtn().setEnabled(false);
				currentPlayer.getPanel().getHand().getPlantBtn().setEnabled(false);

				gui.enablePlayersHand(currentPlayer);

				currentPlayer.setCurrentStage(3);
				gameFrame.getCommonPanel().getCurrentStage().setText(STAGES[2]);

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

					boolean stacked = false;

					if (deck.endOfGame()) {

						// sell all remaining beans on field
						for (int j = 0; j < 3; j++) {

							if (j == 1 || j == 2) {
								gui.updateSell(playerOne, j);
								gui.updateSell(playerTwo, j);
							}

							if (j == 3 && playerOne.isThirdFieldOwned())
								gui.updateSell(playerOne, j);
							if (j == 3 && playerTwo.isThirdFieldOwned())
								gui.updateSell(playerTwo, j);
						}

						gameFrame.dispose();

						// if player one has more or equal number of points player one is the winner
						if (playerOne.getScore() >= playerTwo.getScore()) {
							gameFrame.dispose();
							new GameEnd(playerOne);
						} else {
							gameFrame.dispose();
							new GameEnd(playerTwo);
						}
					} else {
						gameFrame.getCommonPanel().getSlots()[i]
								.setIcon(new ImageIcon("Images/Beans/" + deck.pop().getFileName()));

						Card cardSelected = checkCardTypeSelected(
								gameFrame.getCommonPanel().getSlots()[i].getIcon().toString());

						// validate the card
						for (int slot = 0; slot < 3; slot++) {

							if (("Images/Beans/" + cardSelected.getFileName()).equals(
									gameFrame.getCommonPanel().getSlots()[slot].getIcon().toString()) && slot != i) {
								numCardsInSlot[slot] += 1;
								gameFrame.getCommonPanel().getNumCardLabel()[slot]
										.setText(String.valueOf(numCardsInSlot[slot]));
								stacked = true;
							}
						}

						if (!stacked) {

							numCardsInSlot[i] += 1;
							gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
						}

						gameFrame.getCommonPanel().getDeckCounter().setText(String.valueOf(deck.getCardList().size()));

					}

					if (stacked) {
						i--;
					}

					gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(true);
				}

				for (int i = 0; i < 3; i++) {
					// check if the top card on the discard pile is the same as one of the cards in
					// the slots...
					// will result in disabling the options, and waiting until the deck is called
					if (gameFrame.getCommonPanel().getDiscardDeck().getIcon().toString()
							.equals(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString())) {

						setEnabled(false);

						gameFrame.getCommonPanel().getDiscardDeck().setEnabled(true);
						gameFrame.getCommonPanel().getDiscardButton().setEnabled(false);
						gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(false);
						break;
					}

					else {
						gameFrame.getCommonPanel().getSlots()[i].setEnabled(true);
						gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(true);
					}
				}

			}

			// signify the end of their extended turn
			if (e.getSource() == gameFrame.getCommonPanel().getEndExtendBtn()) {

				// disable the ui
				gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(false);
				gameFrame.getCommonPanel().getDiscardDeck().setEnabled(false);
				gameFrame.getCommonPanel().getDiscardButton().setEnabled(false);

				// disable all the cards
				for (int i = 0; i < 3; i++) {
					gameFrame.getCommonPanel().getSlots()[i].setEnabled(false);
				}

				JOptionPane.showMessageDialog(gameFrame,
						currentPlayer.getName() + " finished their extended turn, draw 2 cards!");
				currentPlayer.setCurrentStage(4);
				gameFrame.getCommonPanel().getCurrentStage().setText(STAGES[3]);
			}

		}

		// STEP 4: PICKING UP TWO CARDS
		// ------------------------------------------------------------
		if (currentPlayer.getCurrentStage() == 4) {

			if (e.getSource() == gameFrame.getCommonPanel().getDeck()) {

				if (deck.endOfGame()) {

					// sell all remaining beans on field
					for (int i = 0; i < 3; i++) {

						if (i == 1 || i == 2) {
							gui.updateSell(playerOne, i);
							gui.updateSell(playerTwo, i);
						}

						if (i == 3 && playerOne.isThirdFieldOwned())
							gui.updateSell(playerOne, i);
						if (i == 3 && playerTwo.isThirdFieldOwned())
							gui.updateSell(playerTwo, i);
					}

					gameFrame.dispose();

					// if player one has more or equal number of points player one is the winner
					if (playerOne.getScore() >= playerTwo.getScore()) {
						gameFrame.dispose();
						new GameEnd(playerOne);
					} else {
						gameFrame.dispose();
						new GameEnd(playerTwo);
					}
				} else {

					// pop the card from the deck stack, and add it to the players' hand
					Card newCard = deck.pop();

					gameFrame.getCommonPanel().getDeckCounter().setText(String.valueOf(deck.getCardList().size()));

					currentPlayer.getHand().add(newCard);

					// update the gui
					gui.updateHand(currentPlayer);

					numOfCardsDrawed++;

					// if the player picks up 2 cards, it signaled the end of their turn
					if (numOfCardsDrawed == 2) {

						// reset all the common panels
						gameFrame.getCommonPanel().getDiscardDeck().setEnabled(false);
						gameFrame.getCommonPanel().getDeck().setEnabled(false);
						gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(false);
						gameFrame.getCommonPanel().getDiscardButton().setEnabled(true);

						// return the current player to the first stage
						currentPlayer.setCurrentStage(1);

						gui.disableInactiveComponents(currentPlayer);
						BohnanzaController.getGameFrame().getCommonPanel().getDiscardButton().setEnabled(true);

						// determine who's turn it is now
						if (currentPlayer == playerOne) {
							currentPlayer = playerTwo;
						} else {
							currentPlayer = playerOne;
						}

						gui.enablePlayersHand(currentPlayer);

						numOfCardsDrawed = 0; // reset number of cards drawn
						numOfPlants = 0; // reset number of plants counter

						gameFrame.getCommonPanel().getCurrentStage().setText(STAGES[0]);
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

						if (currentPlayer instanceof AIPlayer) {

							for (int i = 0; i < 3; i++) {
								gameFrame.getCommonPanel().getSlots()[i].removeActionListener(this);
							}

							try {
								ai.setNumCardsInSlot(numCardsInSlot);
								ai.plantOrDiscardOffered(currentPlayer);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							currentPlayer = playerOne;
							numCardsInSlot = ai.getNumCardsInSlot();
							gui.enablePlayersHand(currentPlayer);

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
			}
		}

		// EVENTS FOR
		// SELLING--------------------------------------------------------------------
		if (e.getSource() == currentPlayer.getPanel().getField().getActionBtns()[0]
				|| e.getSource() == currentPlayer.getPanel().getField().getActionBtns()[1]
				|| e.getSource() == currentPlayer.getPanel().getField().getActionBtns()[2]) {

			int fieldClicked;

			// determine which field was clicked
			if ((e.getSource() == currentPlayer.getPanel().getField().getActionBtns()[0]))
				fieldClicked = 0;
			else if ((e.getSource() == currentPlayer.getPanel().getField().getActionBtns()[1]))
				fieldClicked = 1;
			else
				fieldClicked = 2;

			if (currentPlayer.getCurrentStage() == 3)
				gameFrame.getCommonPanel().getDeck().setEnabled(false);

			HumanPlayer player = (HumanPlayer) currentPlayer;

			// if they clicked the third field
			if (fieldClicked == 2 && currentPlayer.isThirdFieldOwned() == false) {

				// if the player can't afford the field
				if (currentPlayer.getScore() < 3) {
					JOptionPane.showMessageDialog(gameFrame, "You Can Not Afford This Field",
							"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
				}
				// unlock the field
				else {

					currentPlayer.setScore(currentPlayer.getScore() - 3);
					gui.updateScore(currentPlayer);
					gui.unlockField(currentPlayer);
					JOptionPane.showMessageDialog(gameFrame, currentPlayer.getName() + " Bought A Field");
				}

			}
			// if they clicked the first or second field
			else {
				if (player.sell(fieldClicked)) {

					// decrease the amount of type of cards in the overall deck
					int keyValue = deck.findKeyNum(currentPlayer.getBeansInField()[fieldClicked].getBeanType());

					int newNumOfBean = deck.getNumCardMap().get(keyValue).getNumOfBean()
							- currentPlayer.getNumBeansInField()[fieldClicked];

					deck.getNumCardMap().get(keyValue).setNumOfBean(newNumOfBean);

					// update the gui
					gui.updateSell(currentPlayer, fieldClicked);
					JOptionPane.showMessageDialog(gameFrame, currentPlayer.getName() + " Sold Their Beans");
				}

			}

		}

		// If the player clicks the discard deck
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
					gameFrame.getCommonPanel().getDeck().setEnabled(false);
					gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(true);

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
				gameFrame.getCommonPanel().getDeckCounter().setText(String.valueOf(deck.getCardList().size()));
				numCardsInSlot[i] += 1;
				gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
			}

			gameFrame.getCommonPanel().getSlots()[i].setEnabled(condition);
		}
	}

	public static void playMusic(String filepath) {

		// https://www.youtube.com/watch?v=wJO_cq5XeSA
		try {
			// create a file
			File musicPath = new File(filepath);

			// if the file exists
			if (musicPath.exists()) {

				// create an audio input stream
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				// create a clip of the audio
				Clip clip = AudioSystem.getClip();
				// open the audio input stream
				clip.open(audioInput);

				// if the file is the background music
				if (filepath.equals("Audio/backgroundMusic.wav")) {
					// loop the audio clip
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}

				if (filepath.equals("Audio/buttonSound.wav")) {
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					// lower the sound by 10 decibel
					gainControl.setValue(-20.0f);
				}

				// start to the audio
				clip.start();
			}
			// if the file is not found, print an error message
			else {
				System.out.println("can't find file");
			}
		}

		catch (Exception e) {
			System.out.println("error");
		}
	}

}