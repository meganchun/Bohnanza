/*Megan Chun
 * 
 * 
 * External Sources:
 * https://www.digitalocean.com/community/tutorials/thread-sleep-java#
 */
package Controller;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import Model.AIPlayer;
import Model.Card;
import Model.HumanPlayer;
import Model.Player;
import View.GameFrame;

public class AIController {

	private String mode;
	private Player player;

	private BohnanzaController bController;
	private GameFrame gameFrame;
	private GUIController gui;
	private DeckController deck;

	private int[] numCardsInSlot = new int[3];

	public AIController(String mode, GameFrame gameFrame, GUIController gui, DeckController deck) {

		this.mode = mode;
		this.gameFrame = gameFrame;
		this.gui = gui;
		this.deck = deck;
	}

	public void plantOrDiscardOffered(Player player) throws InterruptedException {

		// add a delay
		Thread.sleep(1000);

		boolean hasPlanted = false;
		// EASY AI CONTROLLER
		if (mode.equals("easy")) {

			// check the types of beans offered
			for (int i = 0; i < 3; i++) {

				String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[i].getIcon().toString();

				// for each bean offered check if there are beans in field
				for (int j = 0; j < 3; j++) {

					String fileNameInField = "Images/Beans/" + player.getBeansInField()[j].getFileName();

					// if the offered and the field match
					if (fileNameInOffered.equals(fileNameInField) && player.getNumBeansInField()[j] != 0) {

						Card cardSelected = checkCardTypeSelected(fileNameInOffered);

						System.out.println(cardSelected.getBeanType());

						AIPlayer tempPlayer = (AIPlayer) player;

						// if the plant is a valid move on the field
						if (tempPlayer.plant(cardSelected)) {

							hasPlanted = true;

							JOptionPane.showMessageDialog(gameFrame,
									(player.getName() + " Planted A " + player.getBeansInField()[i].getBeanType()
											+ " Bean"),
									"Bean Planted!", JOptionPane.INFORMATION_MESSAGE,
									player.getPanel().getHand().getPlantIcon());

							// update the field
							gui.updateField(player, cardSelected, numCardsInSlot[i]);

							gameFrame.getCommonPanel().getNumCardLabel()[i].setText("0");
							gameFrame.getCommonPanel().getSlots()[i].setEnabled(false);
							gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));

						}

					}
				}

			}

			if (!hasPlanted) {
				JOptionPane.showMessageDialog(gameFrame,
						player.getName() + " Has Not Planted Any Card From The Offered Card.", "Bean Planted!",
						JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
			}

			for (int i = 0; i < 3; i++) {
				if (!(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString().equals("Images/slotsBtn.png"))) {
					Card cardDiscard = checkCardTypeSelected(
							gameFrame.getCommonPanel().getSlots()[i].getIcon().toString());
					deck.addToDiscard(cardDiscard);
					gameFrame.getCommonPanel().getDiscardDeck()
							.setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getBeanType() + ".png"));
				}

				gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));
			}

			// reset the slots counter
			for (int i = 0; i < 3; i++) {
				gameFrame.getCommonPanel().getNumCardLabel()[i].setText("0");
				numCardsInSlot[i] = 0;
			}

			// add a delay
			Thread.sleep(2000);

			player.setCurrentStage(2);
			gameFrame.getCommonPanel().getCurrentStage().setText(BohnanzaController.STAGES[1]);
			plant(player);

		}

		// HARD AI CONTROLLER
		else {
			System.out.println("Hard");

			// checks if there are any matching beans in the field and the
			// check the types of beans offered
			for (int i = 0; i < 3; i++) {

				String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[i].getIcon().toString();
				// for each bean offered check if there are beans in field
				for (int j = 0; j < 3; j++) {

					String fileNameInField = "Images/Beans/" + player.getBeansInField()[j].getFileName();

					// if the offered and the field match
					if (fileNameInOffered.equals(fileNameInField) && player.getNumBeansInField()[j] != 0) {

						hasPlanted = true;
						JOptionPane.showMessageDialog(gameFrame,
								(player.getName() + " Planted A " + player.getBeansInField()[j].getBeanType()
										+ " Bean"),
								"Bean Planted!", JOptionPane.INFORMATION_MESSAGE,
								player.getPanel().getHand().getPlantIcon());
						// Plant the card in their field
					}
				}

			}

			// check if there is an empty field and there are no
			plant(player);
		}
	}

	public void plant(Player player) throws InterruptedException {

		boolean hasPlanted = false;

		// easy AI Controller
		if (mode.equals("easy")) {

			AIPlayer temp = (AIPlayer) player;

			// plant up to two times
			for (int i = 0; i < 2; i++) {

				// if the player can plant the top card
				if (temp.plant(player.getHand().peek())) {

					// update the field
					gui.updateField(player, player.getHand().peek(), 1);

					gui.updateHand(player);

					JOptionPane.showMessageDialog(gameFrame,
							player.getName() + " Planted A " + player.getHand().peek().getBeanType() + " Bean.",
							"Bean Planted!", JOptionPane.INFORMATION_MESSAGE,
							player.getPanel().getHand().getPlantIcon());

					player.getHand().remove();

					hasPlanted = true;

				}

				// add a delay
				Thread.sleep(1000);
			}

			if (!hasPlanted) {
				JOptionPane.showMessageDialog(gameFrame,
						player.getName() + " Has Not Planted Any Card From Their Hand.", "Bean Planted!",
						JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
			}

			// add a delay
			Thread.sleep(2000);

			discard(player);

		}

		// hard AI Controller
		else {
			AIPlayer temp = (AIPlayer) player;

			// plant up to two times
			for (int i = 0; i < 2; i++) {

				// if the player can plant the top card
				if (temp.plant(player.getHand().peek())) {

					// update the field
					gui.updateField(player, player.getHand().peek(), 1);

					gui.updateHand(player);

					JOptionPane.showMessageDialog(gameFrame,
							player.getName() + " Planted A " + player.getHand().peek().getBeanType() + " Bean.",
							"Bean Planted!", JOptionPane.INFORMATION_MESSAGE,
							player.getPanel().getHand().getPlantIcon());

					player.getHand().remove();

					hasPlanted = true;

				}

				// add a delay
				Thread.sleep(1000);
			}

			if (!hasPlanted) {
				JOptionPane.showMessageDialog(gameFrame,
						player.getName() + " Has Not Planted Any Card From Their Hand.", "Bean Planted!",
						JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
			}

			// add a delay
			Thread.sleep(2000);

			discard(player);
		}
	}

	public void discard(Player player) throws InterruptedException {

		// easy AI Controller
		if (mode.equals("easy")) {

			Queue<Card> tempHand = new LinkedList<Card>();

			Card discardCard = null;

			int indexOfDiscard = 0;

			// look through the whole hand to see if there is not a matching card
			for (Card i : player.getHand()) {

				for (Card j : player.getHand()) {

					if (i == j) {
						break;
					}

				}

				// if the card is not in the players field
				if (i != player.getBeansInField()[0] && i != player.getBeansInField()[1]
						&& i != player.getBeansInField()[2]) {
					discardCard = i;
					break;
				}

				++indexOfDiscard;
			}

			int index = 0;

			for (Card c : player.getHand()) {
				if (index != indexOfDiscard) {
					tempHand.add(c);
				}
				++index;
			}

			player.setHand(tempHand);

			// update the hand with the removed card
			gui.updateHand(player);

			player.setCurrentStage(3);

			// if there is a card to discard
			if (discardCard != null) {
				gameFrame.getCommonPanel().getDiscardDeck()
						.setIcon(new ImageIcon("Images/Beans/" + discardCard.getFileName()));
				JOptionPane.showMessageDialog(gameFrame,
						player.getName() + " Discarded A " + discardCard.getBeanType() + " Bean.", "Bean Discarded!",
						JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
			} else
				JOptionPane.showMessageDialog(gameFrame, player.getName() + " Did Not Discard A Card ",
						"Bean Not Discarded!", JOptionPane.INFORMATION_MESSAGE,
						player.getPanel().getHand().getPlantIcon());

			player.setCurrentStage(3);
			gameFrame.getCommonPanel().getCurrentStage().setText(BohnanzaController.STAGES[2]);

			// add a delay
			Thread.sleep(2000);

			extendedDraw(player);
		}

		// hard AI Controller
		else {
			Queue<Card> tempHand = new LinkedList<Card>();

			Card discardCard = null;

			int indexOfDiscard = 0;
			int x = 0;

			// look through the players hand
			for (Card i : player.getHand()) {

				// Priority 1: if the player has the same card in its hand (compare with the
				// next card in hand)
				for (Card j : player.getHand()) {
					if (x > 0) {
						if (i == j) {
							break;
						}
					}

					x++;
				}

				// Priority 2: if the card is not in the players field
				if (i != player.getBeansInField()[0] && i != player.getBeansInField()[1]
						&& i != player.getBeansInField()[2]) {
					discardCard = i;
					break;
				}

				// Priority 3: if the card is less likely to show up
				int keyValue = deck.findKeyNum(i.getBeanType());

				// 0.3 because if the ratio between new number of beans over the total number of
				// beans is less than 1/3 of the total number of beans, holding onto beans of
				// that type is almost pointless
				if (deck.getNumCardMap().get(keyValue).getNumOfBean() < 0.3) {
					discardCard = i;
					break;
				}

				++indexOfDiscard;
			}

			int index = 0;

			// update the hand with the removed card
			for (Card c : player.getHand()) {
				if (index != indexOfDiscard) {
					tempHand.add(c);
				}
				++index;
			}

			// update the ui
			player.setHand(tempHand);
			gui.updateHand(player);
			player.setCurrentStage(3);

			// if there is a card to discard
			if (discardCard != null) {
				gameFrame.getCommonPanel().getDiscardDeck()
						.setIcon(new ImageIcon("Images/Beans/" + discardCard.getFileName()));
				JOptionPane.showMessageDialog(gameFrame,
						player.getName() + " Discarded A " + discardCard.getBeanType() + " Bean.", "Bean Discarded!",
						JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
			} else
				JOptionPane.showMessageDialog(gameFrame, player.getName() + " Did Not Discard A Card ",
						"Bean Not Discarded!", JOptionPane.INFORMATION_MESSAGE,
						player.getPanel().getHand().getPlantIcon());

			gameFrame.getCommonPanel().getCurrentStage().setText(BohnanzaController.STAGES[2]);

			// add a delay
			Thread.sleep(2000);

			x = 0;
			extendedDraw(player);
		}
	}

	public void extendedDraw(Player player) throws InterruptedException {

		if (mode.equals("easy")) {

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
				while (gameFrame.getCommonPanel().getDiscardDeck().getIcon().toString()
						.equals(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString())) {

					// previous instance of the discarded card
					Card cardDiscard = deck.popFromDiscard();

					// increment the number of cards in that slot if it equals to the previous
					// discarded card object
					for (int j = 0; j < 3; j++) {
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

				}

			}

			for (int i = 0; i < 3; i++) {

				System.out.println(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString());
			}

			// add a delay s
			Thread.sleep(2000);

			boolean hasPlanted = true;
			// check the types of beans offered
			for (int i = 0; i < 3; i++) {

				String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[i].getIcon().toString();
				// for each bean offered check if there are beans in field
				for (int j = 0; j < 3; j++) {

					String fileNameInField = "Images/Beans/" + player.getBeansInField()[j].getFileName();

					// if the offered and the field match
					if (fileNameInOffered.equals(fileNameInField) && player.getNumBeansInField()[j] != 0) {

						Card cardSelected = checkCardTypeSelected(fileNameInOffered);

						System.out.println(cardSelected.getBeanType());

						AIPlayer tempPlayer = (AIPlayer) player;

						// if the plant is a valid move on the field
						if (tempPlayer.plant(cardSelected)) {

							hasPlanted = true;
							JOptionPane.showMessageDialog(gameFrame,
									(player.getName() + " Planted A " + player.getBeansInField()[i].getBeanType()
											+ " Bean"),
									"Bean Planted!", JOptionPane.INFORMATION_MESSAGE,
									player.getPanel().getHand().getPlantIcon());

							// update the field
							gui.updateField(player, cardSelected, numCardsInSlot[i]);

							numCardsInSlot[i] = 0;
							gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
							gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));
						}

						// Plant the card in their field
					}
				}

			}

			if (!hasPlanted) {
				JOptionPane.showMessageDialog(gameFrame,
						player.getName() + " Has Not Planted Any Card From The Offered Card.", "No Bean Planted!",
						JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
			}

			// loop through all the slots and revert the cards chosen to the original
			// image...
			// for cards that were not chosen, disable them for the next player to use
			for (int i = 0; i < 3; i++) {
				if (gameFrame.getCommonPanel().getNumCardLabel()[i].getText().equals("0"))
					gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));

			}

			// add a delay
			Thread.sleep(2000);

			sell(player);

			gameFrame.getCommonPanel().getCurrentStage().setText("Selling");
		}

		// hard AI Controller
		else {

			// pop a card from the deck and visually display on one of the 3 slots for the
			// player to choose from
			for (int i = 0; i < 3; i++) {
				Card cardDiscard = deck.pop();

				gameFrame.getCommonPanel().getSlots()[i]
						.setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getFileName()));
				numCardsInSlot[i] += 1;
				gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));

				validateCards(i);

				// while the top card on the discard pile is the same as one of the cards in
				// the slots...
				// will result in disabling the options, and waiting until the deck is called
				while (gameFrame.getCommonPanel().getDiscardDeck().getIcon().toString()
						.equals(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString())) {

					// if the next card underneath is empty, revert back to the original png
					if (deck.getDiscardList().isEmpty()) {
						gameFrame.getCommonPanel().getDiscardDeck().setIcon(new ImageIcon("Images/discardDeck.png"));
					}

					else {

						// new instance
						cardDiscard = deck.popFromDiscard();
						gameFrame.getCommonPanel().getDiscardDeck()
								.setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getFileName()));
					}

					// increment the number of cards in that slot if it equals to the previous
					// discarded card object
					for (int j = 0; j < 3; j++) {
						if (gameFrame.getCommonPanel().getSlots()[i].getIcon().toString()
								.equals("Images/Beans/" + cardDiscard.getFileName())) {
							numCardsInSlot[i] += 1;
							gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
						}
					}

				}

			}

			// add a delay
			Thread.sleep(2000);

			int numFields = 2; // number of fields free

			// check if the third field is owned
			if (player.isThirdFieldOwned())
				numFields = 3;

			// check the types of beans offered
			for (int i = 0; i < 3; i++) {

				String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[i].getIcon().toString();

				// for each bean offered check if there are beans in field
				for (int j = 0; j < numFields; j++) {
					AIPlayer tempPlayer = (AIPlayer) player;

					String fileNameInField = "Images/Beans/" + tempPlayer.getBeansInField()[j].getFileName().toString();

					// if the offered and the field match
					if (fileNameInOffered.equals(fileNameInField) && player.getNumBeansInField()[j] != 0) {
						System.out.println("Opt 1 Extended Turn");

						Card cardSelected = checkCardTypeSelected(fileNameInOffered);

						JOptionPane.showMessageDialog(gameFrame,
								(player.getName() + " Planted A " + player.getBeansInField()[i].getBeanType()
										+ " Bean"),
								"Bean Planted!", JOptionPane.INFORMATION_MESSAGE,
								player.getPanel().getHand().getPlantIcon());

						// update the field
						gui.updateField(player, cardSelected, numCardsInSlot[i]);

						numCardsInSlot[i] = 0;
						gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
						gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));

						break;
					}

					// if the offered and the field do not match, but there are multiple cards in
					// that slot
					else if (numCardsInSlot[i] > 1) {
						System.out.println("Opt 2 Extended Turn");

						// check if the beanometer of those cards allow for coins to be made
						Card cardSelected = checkCardTypeSelected(fileNameInOffered);

						if (cardSelected.getCoinsEarned(numCardsInSlot[i]) > 0) {
							int removeIndex = 0;

							// sell the field with the least amount of cards
							for (int y = j + 1; y < numFields; y++) {
								if (tempPlayer.getNumBeansInField()[j] > tempPlayer.getNumBeansInField()[y]) {
									removeIndex = y;
								}
							}

							// get the card to remove
							Card tempCard = checkCardTypeSelected(fileNameInField);

							// increment the AI's score
							tempPlayer.setScore(tempPlayer.getScore() + tempCard.getCoinsEarned(removeIndex));

							// update that field with the new cards from the slots
							JOptionPane.showMessageDialog(gameFrame,
									(player.getName() + " Planted A " + player.getBeansInField()[i].getBeanType()
											+ " Bean"),
									"Bean Planted!", JOptionPane.INFORMATION_MESSAGE,
									player.getPanel().getHand().getPlantIcon());

							// update the field
							gui.updateField(player, cardSelected, numCardsInSlot[i]);

							numCardsInSlot[i] = 0;
							gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
							gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));
						}
					}
				}
			}

			// loop through all the slots and revert the cards chosen to the original
			// image...
			// for cards that were not chosen, disable them for the next player to use
			for (int i = 0; i < 3; i++) {
				if (gameFrame.getCommonPanel().getNumCardLabel()[i].getText().equals("0"))
					gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));

			}

			// add a delay
			Thread.sleep(2000);

			sell(player);

			gameFrame.getCommonPanel().getCurrentStage().setText("Selling");
		}
	}

	public void sell(Player player) throws InterruptedException {

		if (mode.equals("easy")) {

			// only sells at before drawing two cards

			boolean hasSold = false;
			int numFields = 2; // number of fields free

			// check if the third field is owned
			if (player.isThirdFieldOwned())
				numFields = 3;

			AIPlayer tempPlayer = (AIPlayer) player;

			int numCardsNeeded = 0;

			// iterate through each field to check if the player can sell their bean
			for (int i = 0; i < numFields; i++) {

				// check the beanometer
				for (int j = 0; j < player.getBeansInField()[i].getbeanometer().length; j++) {

					if (player.getBeansInField()[i].getbeanometer()[j] > 0) {
						numCardsNeeded = j;
					}
				}

				if (tempPlayer.sell(i) && player.getNumBeansInField()[i] >= numCardsNeeded) {

					hasSold = true;
					gui.updateSell(player, i);
					JOptionPane.showMessageDialog(gameFrame, player.getName() + " Sold Their Beans.");

				}
			}

			if (!hasSold)
				JOptionPane.showMessageDialog(gameFrame, player.getName() + " Has Not Sold Their Beans.");

			// add a delay
			Thread.sleep(3000);

			drawToEndTurn(player);
			gameFrame.getCommonPanel().getCurrentStage().setText("End Turn");
		}

		// hard AI Controller
		else {

		}
	}

	public void drawToEndTurn(Player player) throws InterruptedException {

		// pick up twice
		for (int i = 0; i < 2; i++) {

			// pop the card from the deck stack, and add it to the players' hand
			Card newCard = deck.pop();
			player.getHand().add(newCard);

			// update the gui
			gui.updateHand(player);

			JOptionPane.showMessageDialog(gameFrame,
					player.getName() + " Picked Up A " + newCard.getBeanType() + " Bean.", "Pick Up Cards",
					JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());

			// add a delay
			Thread.sleep(1000);
		}

		// reset all the common panels
		gameFrame.getCommonPanel().getDeck().setEnabled(false);
		gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(false);
		gameFrame.getCommonPanel().getDiscardButton().setEnabled(true);

		// return the current player to the first stage
		player.setCurrentStage(1);
		gameFrame.getCommonPanel().getCurrentStage().setText(BohnanzaController.STAGES[0]);

		gui.disableInactiveComponents(player);
		BohnanzaController.getGameFrame().getCommonPanel().getDiscardButton().setEnabled(true);

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
					.equals(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString())) && x != i) {
				validateCards(i);
			}
		}
	}

	public int[] getNumCardsInSlot() {
		return numCardsInSlot;
	}

	public void setNumCardsInSlot(int[] numCardsInSlot) {
		this.numCardsInSlot = numCardsInSlot;
	}

}
