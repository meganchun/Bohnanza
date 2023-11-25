//Megan Chun
package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

import Model.Card;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import Model.Player;
import View.GameFrame;
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

		
		player.getPanel().getHand().getHandPanel().removeAll();
		
		JPanel newHandPanel = new JPanel();
		
		newHandPanel = new JPanel();
		newHandPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		newHandPanel.setBackground(new Color(116,65,35));
		newHandPanel.setPreferredSize(new Dimension(1500, 160));
		
		int x = 0; 
		ArrayList<JRadioButton> cardsInHand;
		
		if (!player.getHand().isEmpty()) {
			
			//add the buttons to the panel and change its colour/font
			
			Queue<Card> tempHand = player.getHand();
			
			cardsInHand = new ArrayList<JRadioButton>();

			int index = 0;
			
			for (Card c : tempHand ) {
				String name = c.getBeanType();
				ImageIcon cardImage = new ImageIcon("Images/Beans/"+name+".png"); //
				cardsInHand.add(new JRadioButton(name, cardImage));
			
			}
			
			for (JRadioButton b : cardsInHand) {
				b.setForeground(Color.WHITE);
				b.setFont(new Font("Helvetica", Font.BOLD, 12));
				b.setBounds(x, 50, 111, 154);
				newHandPanel.add(b);
				x+= 125;
			}
			
			player.getPanel().getHand().setCardsInHand(cardsInHand);
			
			player.getPanel().getHand().setHandPanel(newHandPanel);
			
			player.getPanel().getHand().getHandScrollPane().setViewportView(newHandPanel);
			
		}
		else {
			player.getPanel().getHand().getHandPanel().removeAll();
		}
		
	}
	
	public void updateField(Player player, Card bean, int numBean) {
		
		boolean beanTypePlanted = false;
		int numFieldAvailable;
		
		if (player.isThirdFieldOwned()) 
			numFieldAvailable = 3;
		else 
			numFieldAvailable = 2;
			

		for (int i = 0; i < numFieldAvailable; i++) {
			
			//if the bean already exists in the field
			if (player.getBeansInField()[i] == bean && player.getNumBeansInField()[i] != 0) {
				player.getPanel().getField().getCardCounter()[i].setText(
						Integer.toString(player.getNumBeansInField()[i]+numBean));
				player.getNumBeansInField()[i] += numBean;
				beanTypePlanted = true;
				break;
			}
		}
		
		if (!beanTypePlanted) {
			for (int i = 0; i < numFieldAvailable; i++) {
				
				//if there is an empty field
				if (player.getNumBeansInField()[i] == 0) {

					player.getPanel().getField().getCardCounter()[i].setText(
							Integer.toString(player.getNumBeansInField()[i]+numBean));
					player.getPanel().getField().getCardImages()[i].setIcon(new ImageIcon(
							"Images/Beans/"+bean.getFileName()));
					player.getNumBeansInField()[i] += numBean;
					player.getBeansInField()[i] = bean;
					break;
				}
			}
		}
	}
	
	public void discardCardStep(Player player) {
		
	
		if (!player.getHand().isEmpty()) {
			for (int i = 0; i < player.getPanel().getHand().getCardsInHand().size(); i++) {
				if (i == 0) {
					player.getPanel().getHand().getCardsInHand().get(i).addActionListener(null);
					player.getPanel().getHand().getCardsInHand().get(i).setEnabled(true);
				}
				else {
					player.getPanel().getHand().getCardsInHand().get(i).setEnabled(false);
				}
			}
		}
	}
 
	
	public void unlockField(Player player) {
		
		player.setThirdFieldOwned(true);
		player.getPanel().getField().getCardImages()[2].setIcon(new ImageIcon("Images/slotsBtn.png"));
		player.getPanel().getField().getActionBtns()[2].setIcon(new ImageIcon("Images/sellBtn.png"));
	
	}

	public void disableInactiveComponents(Player player) {
		
		//disable ALL components for the inactive player
		//disable plant and discard button
		player.getPanel().getHand().getDiscardBtn().setEnabled(false);
		player.getPanel().getHand().getPlantBtn().setEnabled(false);
		player.getPanel().getHand().getDoneBtn().setEnabled(false);
		
		//disable cards in hand
		for (JRadioButton b : player.getPanel().getHand().getCardsInHand()) {
			b.setEnabled(false);
		}
		
		for (JButton b : player.getPanel().getField().getActionBtns()) {
			b.setEnabled(false);
		}
		
		BohnanzaController.getGameFrame().getCommonPanel().getDiscardButton().setEnabled(false);
		BohnanzaController.getGameFrame().getCommonPanel().getDeck().setEnabled(false);
	}

	
	public void updateSell(Player player, int fieldNumber) {
		
		int newScore = player.getScore() + player.getBeansInField()[fieldNumber].getCoinsEarned(player.getNumBeansInField()[fieldNumber]);
		//update player's score 
		player.setScore(newScore);
		
		//update gui score
		player.getPanel().getScoreNum().setText(Integer.toString(newScore));
	
		//update the field
		player.getPanel().getField().getCardImages()[fieldNumber].setIcon(new ImageIcon("Images/slotsBtn.png"));
		player.getPanel().getField().getCardCounter()[fieldNumber].setText("0");
		
		//update the user's backend field
		player.getNumBeansInField()[fieldNumber] = 0;
		
		
	}
	
 
	//this method will open up all the cards in the hand so the player can discard any card
	public void enablePlayersHand(Player player) {

		//disable cards in hand
		for (JRadioButton b : player.getPanel().getHand().getCardsInHand()) {
			b.setEnabled(true);
		}
	}
	
	
}
