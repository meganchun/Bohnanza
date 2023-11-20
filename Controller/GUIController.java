//Megan Chun
package Controller;

import java.awt.Component;
import java.util.Queue;

import Model.Card;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import Model.Player;
import View.PlayerFieldPanel;
import View.PlayerHandPanel;
import View.PlayerPanel;

public class GUIController {
	
	public GUIController() {
		
	}
	
	public void updateScore(Player player) {
	
		player.getPanel().getScoreNum().setText(Integer.toString(player.getScore()));
	}
	
	public static JRadioButton[] updateHand(Player player) {
		
		String[] typeOfBeans = {"Garden","Red","Black-eyed","Soy","Green","Stink","Chili","Blue"};
		
		Queue<Card> tempHand = player.getHand();
		
	
		//store the number of each type of bean is in the hand 
		int[] typeOfBeansInHand = new int[8];
		
		for (Card name : tempHand) {
			
			//CHANGE TO REFLECT A CARD 
			if (name.getBeanType().equals("Garden")) {
				typeOfBeansInHand[0]++;
			}
			else if (name.getBeanType().equals("Red")) {
				typeOfBeansInHand[1]++;
			}
			else if (name.getBeanType().equals("Black-eyed")) {
				typeOfBeansInHand[2]++;
			}
			else if (name.getBeanType().equals("Soy")) {
				typeOfBeansInHand[3]++;
			}
			else if (name.getBeanType().equals("Green")) {
				typeOfBeansInHand[4]++;
			}
			else if (name.getBeanType().equals("Stink")) {
				typeOfBeansInHand[5]++;
			}
			else if (name.getBeanType().equals("Chili")) {
				typeOfBeansInHand[6]++;
			}
			else if (name.getBeanType().equals("Blue")) {
				typeOfBeansInHand[7]++;
			}
		}
		
		//get number of types of bean
		int numTypesOfBeans = 0;
		
		for (int i = 0; i < 8; i++) {
			if (typeOfBeansInHand[i] > 0) {
				numTypesOfBeans++;
			}
		}
		
		JRadioButton[] btns = new JRadioButton[numTypesOfBeans];
		
		int index = 0;

		for (int i = 0; i < typeOfBeansInHand.length; i++) {
			
			if (typeOfBeansInHand[i] > 0) {
				String name = typeOfBeans[i] + " x" + typeOfBeansInHand[i];  //typeOfBeans[i].getName()
				
				ImageIcon cardImage = new ImageIcon("Images/Beans/"+typeOfBeans[i]+".png"); //
				btns[index++]= new JRadioButton(name, cardImage);
			}
		}
		
		return btns;
		
	}
	
	public void updateField(Player player) {
		
	
	}
	
	public void discardCardStep(Player player) {
		
	
		if (!player.getHand().isEmpty()) {
			for (int i = 0; i < player.getPanel().getHand().getCardsInHand().length; i++) {
				if (i == 0) {
					player.getPanel().getHand().getCardsInHand()[i].addActionListener(null);
					player.getPanel().getHand().getCardsInHand()[i].setEnabled(true);
				}
				else {
					player.getPanel().getHand().getCardsInHand()[i].setEnabled(false);
				}
			}
		}
	}
 
	
	public void unlockField(Player player) {
		
		player.getPanel().getField().getCardImages()[2].setIcon(new ImageIcon("Images/tempCard.png"));
		player.getPanel().getField().getActionBtns()[2].setIcon(new ImageIcon("Images/sellBtn.png"));
	
	}

	public void disableInactivePlayerComponents(Player player) {
		
		//disable ALL components for the inactive player
		//disable plant and discard button
		player.getPanel().getHand().getDiscardBtn().setEnabled(false);
		player.getPanel().getHand().getPlantBtn().setEnabled(false);
		
		//disable cards in hand
		for (JRadioButton b : player.getPanel().getHand().getCardsInHand()) {
			b.setEnabled(false);
		}
		
		for (JButton b : player.getPanel().getField().getActionBtns()) {
			b.setEnabled(false);
		}
		
	}
	
	public void disableActivePlayerComponents(Player player) {
		
		player.getPanel().getHand().getDiscardBtn().setEnabled(false);
		player.getPanel().getHand().getPlantBtn().setEnabled(false);
		
		//disable cards in hand
		for (JRadioButton b : player.getPanel().getHand().getCardsInHand()) {
			b.setEnabled(false);
		}
		
		for (JButton b : player.getPanel().getField().getActionBtns()) {
			b.setEnabled(false);
		}
		
	}

	//this method will open up all the cards in the hand so the player can discard any card
	public void enablePlayersHand(Player player) {

		//disable cards in hand
		for (JRadioButton b : player.getPanel().getHand().getCardsInHand()) {
			b.setEnabled(true);
		}
	}
	
	
}
