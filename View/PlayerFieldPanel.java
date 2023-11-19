//Megan Chun

package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Card;
import Model.Player;

public class PlayerFieldPanel extends JPanel {

	private JPanel numCounterPanel;
	private JPanel cardPanel;
	private JPanel btnPanel;
	
	private JLabel[] cardImages = new JLabel[3];
	private JLabel[] cardCounter = new JLabel[3];
	private JButton[] actionBtns = new JButton[3];
	
	private Icon[] actionBtnImages;
	
	public PlayerFieldPanel(Player player) {
	
		setLayout(BorderLayout.CENTER);
		
		addField(player);
		
		//create the panel sections
		numCounterPanel = new JPanel();
		numCounterPanel.setPreferredSize(new Dimension(600,25));
		numCounterPanel.setOpaque(false);
		
		cardPanel = new JPanel();
		cardPanel.setPreferredSize(new Dimension(600,160));
		cardPanel.setOpaque(false);
		
		btnPanel = new JPanel();
		btnPanel.setPreferredSize(new Dimension(600,60));
		btnPanel.setOpaque(false);
		
		//add the components to each panel
		for (int i = 0; i < 3; i++) {
			
			//add the card numbers
			numCounterPanel.add(Box.createHorizontalStrut(45)); //add padding between the numbers
			numCounterPanel.add(cardCounter[i]);
			numCounterPanel.add(Box.createHorizontalStrut(45)); //add padding between the numbers
			
			cardPanel.add(cardImages[i]);
			
			btnPanel.add(Box.createHorizontalStrut(4)); //add padding between the buttons
			btnPanel.add(actionBtns[i]);
			btnPanel.add(Box.createHorizontalStrut(4)); //add padding between the buttons
		}
		
		//add the panels to the frame
		add(numCounterPanel);
		add(cardPanel);
		add(btnPanel);
		
	}

	private void setLayout(String center) {
		// TODO Auto-generated method stub
		
	}

	//GETTERS AND SETTERS
	public JLabel[] getCardImages() {
		return cardImages;
	}

	public void setCardImages(JLabel[] cardImages) {
		this.cardImages = cardImages;
	}

	public JLabel[] getCardCounter() {
		return cardCounter;
	}

	public void setCardCounter(JLabel[] cardCounter) {
		this.cardCounter = cardCounter;
	}

	public JButton[] getActionBtns() {
		return actionBtns;
	}

	public void setActionBtns(JButton[] actionBtns) {
		this.actionBtns = actionBtns;
	}

	public Icon[] getActionBtnImages() {
		return actionBtnImages;
	}

	public void setActionBtnImages(Icon[] actionBtnImages) {
		this.actionBtnImages = actionBtnImages;
	}
	
	//UTILITY METHOD
	public void changeFont() {
		for (JLabel num : cardCounter) {
			num.setFont(new Font("Helvetica", Font.BOLD, 20));
			num.setForeground(Color.white);
		}
	}
	
	public void addField(Player player) {
		
		//TEMP INFO
	
		
		for (int i = 0; i < 3; i++) {
			
			if (i == 2) {
				cardImages[2] = new JLabel(new ImageIcon("Images/lockedField.png"));
				cardCounter[i] = new JLabel("0");
			}
			else {
				cardImages[i] = new JLabel(new ImageIcon("Images/slotsBtn.png"));
				cardCounter[i] = new JLabel("0");
 			}
		}
		
		actionBtnImages = new Icon[3];
		Icon btn1 = new ImageIcon("Images/sellBtn.png");
		Icon btn2 = new ImageIcon("Images/sellBtn.png");
		Icon btn3 = new ImageIcon("Images/buyBtn.png");
		
		actionBtnImages[0] = btn1;
		actionBtnImages[1] = btn2;
		actionBtnImages[2] = btn3;
		
		actionBtns = new JButton[3];
		
		for (int i = 0; i < 3 ; i++) {
			actionBtns[i] = new JButton(actionBtnImages[i]);
			actionBtns[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //remove backgrounds
		}
		
		changeFont();
				
	}
}
