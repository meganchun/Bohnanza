//Megan Chun
package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Map;
import java.util.Queue;

import Model.Card;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	public void updateHand(Player player) {
		
		int index = 0;
		
		player.getPanel().getHand().getCardsInHand()[player.getHand().size()-1].setVisible(false);
		
		for (Card c : player.getHand()) {

			String name = c.getBeanType();
			ImageIcon cardImage = new ImageIcon("Images/Beans/"+name+".png"); 
			
			player.getPanel().getHand().getCardsInHand()[index].setText(name);
			player.getPanel().getHand().getCardsInHand()[index].setIcon(cardImage);
			
			index++;
		}
		
	}
	
	public void updateField(Player player, Card bean) {
		
		boolean beanTypePlanted = false;

		
		for (int i = 0; i < 3; i++) {
			
			//if the bean already exists in the field
			if (player.getBeansInField()[i] == bean && player.getNumBeansInField()[i] != 0) {
				player.getPanel().getField().getCardCounter()[i].setText(
						Integer.toString(player.getNumBeansInField()[i]+1));
				player.getNumBeansInField()[i] += 1;
				beanTypePlanted = true;
				break;
			}
		}
		
		if (!beanTypePlanted) {
			for (int i = 0; i < 3; i++) {
				
				//if there is an empty field
				if (player.getNumBeansInField()[i] == 0) {
					System.out.println(i);
					player.getPanel().getField().getCardCounter()[i].setText(
							Integer.toString(player.getNumBeansInField()[i]+1));
					player.getPanel().getField().getCardImages()[i].setIcon(new ImageIcon(
							"Images/Beans/"+bean.getFileName()));
					player.getNumBeansInField()[i] += 1;
					player.getBeansInField()[i] = bean;
					break;
				}
			}
		}
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
