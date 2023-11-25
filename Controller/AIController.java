/*Megan Chun, Aaron Su
 * 
 * 
 * External Sources:
 * https://www.digitalocean.com/community/tutorials/thread-sleep-java#
 */
package Controller;

import java.awt.Color;
import java.util.Arrays;
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
	
	private GameFrame gameFrame;
	private GUIController gui;
	private DeckController deck;
	private BohnanzaController controller;
	
	private int[] numCardsInSlot = new int[3];
	
	public AIController(String mode, GameFrame gameFrame, GUIController gui, DeckController deck, BohnanzaController controller) {

		this.mode = mode;
		this.gameFrame = gameFrame;
		this.gui = gui;
		this.deck = deck;
		this.controller = controller;
	}
	
	public void plantOrDiscardOffered(Player player) throws InterruptedException {
		
		//add a delay 
		Thread.sleep(1000);
		
		boolean hasPlanted = false;
		
		//CASE 1: There are plants that directly match from the offered to the players hand
		//check the types of beans offered
		for (int i = 0; i < 3; i++) {
	
			String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[i].getIcon().toString();
			
			//for each bean offered check if there are beans in field
			for (int j = 0; j < 3; j++) {
				
				String fileNameInField = "Images/Beans/"+player.getBeansInField()[j].getFileName();
				
				//if the offered and the field match 
				if (fileNameInOffered.equals(fileNameInField) && player.getNumBeansInField()[j] != 0) {
					
					Card cardSelected = controller.checkCardTypeSelected(fileNameInOffered);
				
					AIPlayer tempPlayer = (AIPlayer) player;
					
					//if the plant is a valid move on the field
					if (tempPlayer.plant(cardSelected)) {
						
						hasPlanted = true;
				
						plantUpdate(i, cardSelected, player);

					}
						
				}
			}
		
		}
		
		//EASY AI CONTROLLER
		if (mode.equals("easy")) {
		
		}
		
		//HARD AI CONTROLLER
		else {
		
			if (!hasPlanted) {
			
				int[] potentialHandAndOffered = {0,0,0};
	
				int[] potentialOffered = {0,0,0};
				
				AIPlayer tempPlayer = (AIPlayer) player;
			
				int[] numOfBeanInHand = {0,0,0};
				//CASE 2: The offered cards + similar cards found in hand players hands v
				//...calculate potential total
				for (int beanOffered = 0; beanOffered < 3; beanOffered++) {
					
					int highestNum = 0;
					
					String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[beanOffered].getIcon().toString();
					Card cardSelected = controller.checkCardTypeSelected(fileNameInOffered);
					
					
					for (Card c : player.getHand()) {
	
						if (c == cardSelected) {
							
							numOfBeanInHand[beanOffered]++;
						}
					}
					
					
					if (numOfBeanInHand[beanOffered] > highestNum) {
						numOfBeanInHand[beanOffered] = highestNum;
					}
				
					potentialHandAndOffered[beanOffered] = cardSelected.getCoinsEarned(numOfBeanInHand[beanOffered] + numCardsInSlot[beanOffered]);
					potentialOffered[beanOffered] = cardSelected.getCoinsEarned(numCardsInSlot[beanOffered]);
				}
				
				//check if they are all zero 
				boolean allZero = true;
				
				for (int i = 0; i < 3; i++) {
					if (potentialHandAndOffered[i] != 0 || potentialOffered[i] != 0)  {
						allZero = false;
						break;
					}
				}
				
				if (allZero) {
					
					int indexOfNextHighest = 0;
					
					//check which has the most cards
					Arrays.sort(numOfBeanInHand);
					
					for (int i = 0; i < 3; i++) {
						
						String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[i].getIcon().toString();
						Card cardSelected = controller.checkCardTypeSelected(fileNameInOffered);
						
						if (numCardsInSlot[i] == indexOfNextHighest) {
							
							//if the plant is a valid move on the field
							if (tempPlayer.plant(cardSelected)) {
								
							hasPlanted = true;
					
							plantUpdate(i, cardSelected, player);
							
							indexOfNextHighest++;
							}
						}
					}
				}
				else {
	
					for (int j = 0; j < 3; j++) {
						
						int highestPotentialEarning = -1;
						int highestPotentialEarningIndex = 0;
						
						for (int i = 0; i < 3; i++) {
							
							if (potentialHandAndOffered[i] > highestPotentialEarning) {
								highestPotentialEarning = potentialHandAndOffered[i];
								highestPotentialEarningIndex = i;
							}
							else if (potentialOffered[i] > highestPotentialEarning) {
								highestPotentialEarning = potentialOffered[i];
								highestPotentialEarningIndex = i;
							}
						}
						
						System.out.println("Highest Earning Index" + highestPotentialEarningIndex);
						
						//cross check to see if the amount earned already in the field is less than...
						//the potential earning if you were to plant the offered 
						
						int leastFieldEarning = highestPotentialEarning;
						int leastFieldEarningIndex = -1;
						
						int fieldSize;
						
						if (player.isThirdFieldOwned()) 
							fieldSize = 3;
						else 
							fieldSize = 2;
						
						for (int field = 0; field < fieldSize; field++) {
							
							if (player.getBeansInField()[field].getCoinsEarned(player.getNumBeansInField()[field]) <
									leastFieldEarning || player.getNumBeansInField()[field] == 0) {
								leastFieldEarningIndex = field;
								leastFieldEarning = player.getBeansInField()[field].getCoinsEarned(player.getNumBeansInField()[field]);
							}
						}
					
						
						System.out.println("Leasts Earning Index" + leastFieldEarningIndex);
						
							//if there is a field earning less than the offered cards
							if (leastFieldEarningIndex != -1) {
								
								String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[highestPotentialEarningIndex].getIcon().toString();
								Card cardSelected = controller.checkCardTypeSelected(fileNameInOffered);
								
								System.out.println("Going to Plant: " + cardSelected.getBeanType());
								//if the plant is a valid move on the field
								if (tempPlayer.plant(cardSelected)) {
									
									hasPlanted = true;
							
									plantUpdate(highestPotentialEarningIndex, cardSelected, player);
							}
								
						}
						
						System.out.println("before");
						for (int i = 0; i < 3; i++) {
							System.out.print(potentialHandAndOffered[i]);
						}
						System.out.println();
						for (int i = 0; i < 3; i++) {
							System.out.print(potentialOffered[i]);
						}
						
						System.out.println(highestPotentialEarning);
						//reset the highest potential earnings
						for (int i = 0; i < 3; i++) {
							
							if (i == highestPotentialEarningIndex) {
								potentialHandAndOffered[i] = -1;
								potentialOffered[i] = -1;
							}
						} 
						
						System.out.println("after resetting");
						for (int i = 0; i < 3; i++) {
							System.out.print(potentialHandAndOffered[i]);
						}
						System.out.println();
						for (int i = 0; i < 3; i++) {
							System.out.print(potentialOffered[i]);
						}
						System.out.println();
						
						
					}
				}
				
				//Case 3:  there are any remaining slots and compare empty slots
				for (int j = 0; j < 3; j++) {
					int currentHighest = 0;
					
					for (int i = 0; i < 3; i++) {
						if (numCardsInSlot[i] > currentHighest) {
							currentHighest = i;
						}
					}
			
					String fileNameInOffered = gameFrame.getCommonPanel().getSlots()[currentHighest].getIcon().toString();	
					Card cardSelected = controller.checkCardTypeSelected(fileNameInOffered);
					
					//if the plant is a valid move on the field
					if (tempPlayer.plant(cardSelected)) {
						
						hasPlanted = true;
						
						plantUpdate(currentHighest, cardSelected, player);

					}
			
				}
				
			}
			
		}
		
		if (!hasPlanted) {
			JOptionPane.showMessageDialog(gameFrame, player.getName() + " Has Not Planted Any Card From The Offered Card.","Bean Planted!",
				    JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
		}
		
		for (int i = 0; i < 3; i++) {
			if (!(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString().equals("Images/slotsBtn.png"))) {
				Card cardDiscard = controller.checkCardTypeSelected(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString());
				deck.addToDiscard(cardDiscard);
				gameFrame.getCommonPanel().getDiscardDeck().setIcon(new ImageIcon("Images/Beans/" + cardDiscard.getBeanType() + ".png"));
			}

			gameFrame.getCommonPanel().getSlots()[i].setIcon(new ImageIcon("Images/slotsBtn.png"));
		}
		
		//reset the slots counter	
		for (int i = 0; i < 3; i++) {
			gameFrame.getCommonPanel().getNumCardLabel()[i].setText("0");
			numCardsInSlot[i] = 0;
		}
		
		//add a delay 
		Thread.sleep(2000);
		
		sell(player);	
		
		if (player.getCurrentStage() != 3) {
			player.setCurrentStage(2);
			gameFrame.getCommonPanel().getCurrentStage().setText(BohnanzaController.STAGES[1]);
			plantFromHand(player);
		}
		
	}
	
	public void plantFromHand(Player player) throws InterruptedException {
		
		boolean hasPlanted = false;
	
		AIPlayer temp = (AIPlayer) player;
		
		//plant up to two times
		for (int i = 0; i < 2; i++) {
			
			// add a delay
			Thread.sleep(1000);
			
			
			//if the player can plant the top card
			if (temp.plant(player.getHand().peek())) {
				
				//update the field
				gui.updateField(player, player.getHand().peek(), 1);
				
				gui.updateHand(player);
			
				JOptionPane.showMessageDialog(gameFrame, player.getName() + " Planted A " + player.getHand().peek().getBeanType() +" Bean.","Bean Planted!",
					    JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
				
				player.getHand().remove();
			
				hasPlanted = true;

			}
		}
		
		if (!hasPlanted) {
			JOptionPane.showMessageDialog(gameFrame, player.getName() + " Has Not Planted Any Card From Their Hand.","Bean Planted!",
				    JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
		}
		
		//add a delay 
		Thread.sleep(2000);
		
		sell(player);	
		
		discard(player);
		
	
	}
	
	public void discard(Player player) throws InterruptedException {
		
		
		Queue<Card> tempHand = new LinkedList<Card>();
		
		Card discardCard = null;
		
		int indexOfDiscard = 0;
		
		//easy AI Controllers 
		if (mode.equals("easy")) {
			
			//look through the whole hand to see if there is not a matching card
			for (Card i : player.getHand()) {
				
				for (Card j : player.getHand()) {
					
					if (i == j) {
						break;
					}
				}
				
				//if the card is not in the players field
				if (i != player.getBeansInField()[0] && i != player.getBeansInField()[1]
						&& i != player.getBeansInField()[2]) {
				discardCard = i;
				break;
				}
				
				++indexOfDiscard;
			}
		}
		
		//hard AI Controller 
		else {
		
			
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

		

			x = 0;
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

		// add a delay
		Thread.sleep(2000);
				
		player.setCurrentStage(3);
		gameFrame.getCommonPanel().getCurrentStage().setText(BohnanzaController.STAGES[2]);
					
		
		extendedDraw(player);
	
	}
	
	public void extendedDraw(Player player) throws InterruptedException {
		
		
		//DISPLAY THE CARDS
		// pop a card from the deck and visually display on one of the 3 slots for the
		// player to choose from
		for (int i = 0; i < 3; i++) {
			
			gameFrame.getCommonPanel().getSlots()[i]
					.setIcon(new ImageIcon("Images/Beans/" + deck.pop().getFileName()));
			numCardsInSlot[i] += 1;
			gameFrame.getCommonPanel().getNumCardLabel()[i].setText(String.valueOf(numCardsInSlot[i]));
			gameFrame.getCommonPanel().getDeckCounter().setText(String.valueOf(deck.getCardList().size()));

			Card cardSelected = controller.checkCardTypeSelected(gameFrame.getCommonPanel().getSlots()[i].getIcon().toString());

			controller.validateCards(i, cardSelected);
			

			// check if the top card on the discard pile is the same as one of the cards in
			// the slots...
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
		
		
		JOptionPane.showMessageDialog(gameFrame, player.getName() + " Has drawed the offered cards");
		
	
		if (mode.equals("easy")) {
			
			//reuse code from first offered cards
			plantOrDiscardOffered(player);
		
		}
		
		//hard AI Controller 
		else {

			// add a delay
			Thread.sleep(1000);

			int numFields = 2; // number of fields free

			// check if the third field is owned
			if (player.isThirdFieldOwned())
				numFields = 3;

			plantOrDiscardOffered(player);
						
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
		
		drawToEndTurn(player);
	
	}
	
	public void sell(Player player) throws InterruptedException {
		
		boolean hasSold = false;
		
		int numFields = 2; //number of fields free
		
		//check if the third field is owned
		if (player.isThirdFieldOwned()) 
			numFields = 3;
		
		
		if (mode.equals("easy")) { //only sells at before drawing two cards
			
			AIPlayer tempPlayer = (AIPlayer) player;
			
			int numCardsNeeded = 0;
			
			//iterate through each field to check if the player can sell their bean
			for (int i = 0; i < numFields; i++) {
				
				//check the beanometer 
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

		}
		
		//hard AI Controller 
		else {
			
			//check each field to check to sell 
			for (int field = 0; field < numFields; field++) {
			
				boolean match = false;
				int placeInHand = 1;
				
				int coinsEarned = player.getBeansInField()[field].getCoinsEarned(player.getNumBeansInField()[field]);
				
				//check if it is the max coins you can earn
				for (int i = player.getBeansInField()[field].getbeanometer().length-1; i >= 0; i--) {
					if (player.getBeansInField()[field].getbeanometer()[i] != 0) {
						if (coinsEarned == player.getBeansInField()[field].getbeanometer()[i]) {
						}	
						else {
							break;
						}
					}
				}
				
				//check each
				for (Card cardInHand : player.getHand()) {
					
					//Case 1: You have matching cards in your hand
					if (player.getBeansInField()[field] == cardInHand && player.getNumBeansInField()[field] > 0
							&& placeInHand <= 5) {
						
						//check if the player will have the chance to ...
						//sell this card before the end of the game
						if (placeInHand > (deck.getCardList().size()) - 10) { //if they can NOT plant in time
						
							//sell
							hasSold = true;
						}
						match = true;
					}
					placeInHand++;
				}
				
				//if the bean in the field doesn't have any similar cards in the hand
				if (!match && player.getNumBeansInField()[field] > 0) {
					
					//decrease the amount of type of cards in the overall deck
					int keyValue = deck.findKeyNum(player.getBeansInField()[field].getBeanType());
				
					double probablityOfPickUp = (double) deck.getNumCardMap().get(keyValue).getNumOfBean()/ deck.getCardList().size();
					
					//if there are no more of the bean left, sell
					if (probablityOfPickUp == 0) {
						gui.updateSell(player, field);
					}
					else {
						int numToNextBeanometer = 0;
						
						//iterate through beanometer
						for (int i = 0; i < player.getBeansInField()[field].getbeanometer().length; i++) {
							
							if (i == player.getNumBeansInField()[field] && player.getBeansInField()[field].getbeanometer()[i] == 0) {
								
								while (player.getBeansInField()[field].getbeanometer()[i] == 0) {
									
									numToNextBeanometer++;
									
									if (i != player.getBeansInField()[field].getbeanometer().length -1) {
										i++;
									}
									else {
										break;
									}
								}
							}
						}
						
						//if you can not get to the next beanometer level
						if (deck.getNumCardMap().get(keyValue).getNumOfBean() - numToNextBeanometer <= 0) { 
							gui.updateSell(player, field);
							hasSold = true;
						}
						//if you have to pick up two
						else if (probablityOfPickUp < 0.5 && numToNextBeanometer >= 2 && coinsEarned > 0) {
							gui.updateSell(player, field);
							hasSold = true;
						}
						else if (probablityOfPickUp < 0.4 && numToNextBeanometer == 1 && coinsEarned > 0) {
							System.out.println("Should sell");
							gui.updateSell(player, field);
							hasSold = true;
						}
						else if (probablityOfPickUp < 0.05) {
							gui.updateSell(player, field);
							hasSold = true;
						}
					}
				}
			}

		}
		
		if (!hasSold) 
			JOptionPane.showMessageDialog(gameFrame, player.getName() + " Has Not Sold Their Beans.");
		else {
			JOptionPane.showMessageDialog(gameFrame, player.getName() + " Sold Their Beans.");
		}
			
		
		//add a delay 
		Thread.sleep(3000);

		gameFrame.getCommonPanel().getCurrentStage().setText("End Turn");
	

	}
	
	public void drawToEndTurn(Player player) throws InterruptedException {
	
		//pick up twice
		for (int i = 0; i < 2; i++) {
			
			// pop the card from the deck stack, and add it to the players' hand
			Card newCard = deck.pop();
			gameFrame.getCommonPanel().getDeckCounter().setText(String.valueOf(deck.getCardList().size()));
			player.getHand().add(newCard);
			
			//update the gui
			gui.updateHand(player);
			
			JOptionPane.showMessageDialog(gameFrame, player.getName() + " Picked Up A " + newCard.getBeanType() + " Bean.", "Pick Up Cards",
				    JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());
		
			//add a delay 
			Thread.sleep(1000);
		}
		
		// reset all the common panels 
		gameFrame.getCommonPanel().getDeck().setEnabled(false);
		gameFrame.getCommonPanel().getEndExtendBtn().setEnabled(false);
		gameFrame.getCommonPanel().getDiscardButton().setEnabled(true);
		
		//return the current player to the first stage
		player.setCurrentStage(1);
		gameFrame.getCommonPanel().getCurrentStage().setText(BohnanzaController.STAGES[0]);
		
		gui.disableInactiveComponents(player);
		BohnanzaController.getGameFrame().getCommonPanel().getDiscardButton().setEnabled(true);
	}
	
	public void buyField() {
		
		if (player.getScore() >= 3) {
			
			//if the player can buy a field and still maintain their lead, but if there 
			//is less than 15 cards, you may not make the coins back
			if (player.getScore() - 4 >= controller.getPlayerOne().getScore() && deck.getCardList().size() >= 16) {
				//buy field
			}

			//if there are less than 16 cards in the deck around 20% of the deck remaining
			//the odds of selling enough beans to earn 3 coins back  does not make up the difference
			//min num cards to get three coins: 4
			//max num cards to get three coins: 10
			else if (deck.getCardList().size() >= 20) { 
				
				if (controller.getPlayerOne().getScore() - player.getScore() <= 3 && deck.getCardList().size() > 60) {
					//buy
				}
				if (controller.getPlayerOne().isThirdFieldOwned() == true && deck.getCardList().size() > 80 && 
						controller.getPlayerOne().getScore() - player.getScore() <= 6) {
					//buy
				}
				else if (controller.getPlayerOne().isThirdFieldOwned() == true && deck.getCardList().size() > 60 &&
						controller.getPlayerOne().getScore() - player.getScore() <= 3) {
					//
				}
					 
			}
			
		}
	}
	
	public void plantUpdate(int fieldNum, Card beanType, Player player) {
		

		JOptionPane.showMessageDialog(gameFrame, (player.getName() + " Planted A "+ beanType.getBeanType() + " Bean"),"Bean Planted!",
			    JOptionPane.INFORMATION_MESSAGE, player.getPanel().getHand().getPlantIcon());

		//update the field
		gui.updateField(player, beanType, numCardsInSlot[fieldNum]) ;
		
		gameFrame.getCommonPanel().getNumCardLabel()[fieldNum].setText("0");
		gameFrame.getCommonPanel().getSlots()[fieldNum].setEnabled(false);
		gameFrame.getCommonPanel().getSlots()[fieldNum].setIcon(new ImageIcon("Images/slotsBtn.png"));
		numCardsInSlot[fieldNum] = 0;

		
	}
	
	public int[] getNumCardsInSlot() {
		return numCardsInSlot;
	}

	public void setNumCardsInSlot(int[] numCardsInSlot) {
		this.numCardsInSlot = numCardsInSlot;
	}
	
}
