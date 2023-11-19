//Megan Chun

package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Scrollbar;
import java.awt.event.ItemListener;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controller.BohnanzaController;
import Controller.GUIController;
import Model.Card;
import Model.Player;

public class PlayerHandPanel extends JPanel {

	
	private JScrollPane handScrollPane;
	private JPanel handPanel;

	private JPanel btnPanel;
	
	private Icon discardIcon = new ImageIcon("Images/discardCardBtn.png");
	private JButton discardBtn = new JButton(discardIcon);
	
	private Icon plantIcon = new ImageIcon("Images/plantCardBtn.png");
	private JButton plantBtn = new JButton(plantIcon);
	
	private JRadioButton[] cardsInHand;
	private ButtonGroup cardsButtons;
	
	public PlayerHandPanel(Player player) {
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addCards(player);
		
		handScrollPane = new JScrollPane(handPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		handScrollPane.setOpaque(false);
		handScrollPane.setPreferredSize(new Dimension(250, 180));
		add(handScrollPane);
		
		//add the panel to the two buttons
		btnPanel = new JPanel();
		btnPanel.setOpaque(false);
		btnPanel.setPreferredSize(new Dimension(117,200));
		add(btnPanel);
		
		//add the discard button
		discardBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //remove border from button
		btnPanel.add(discardBtn);
		
		//add the discard button
		plantBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //remove border from button
		btnPanel.add(plantBtn);
		
		
	
	}
	
	//GETTERS AND SETTERS
	public JRadioButton[] getCardsInHand() {
		return cardsInHand;
	}

	public void setCardsInHand(JRadioButton[] cardsInHand) {
		this.cardsInHand = cardsInHand;
	}

	public ButtonGroup getCardsButtons() {
		return cardsButtons;
	}

	public void setCardsButtons(ButtonGroup cardsButtons) {
		this.cardsButtons = cardsButtons;
	}
	
	public JButton getDiscardBtn() {
		return discardBtn;
	}

	public void setDiscardBtn(JButton discardBtn) {
		this.discardBtn = discardBtn;
	}

	public JButton getPlantBtn() {
		return plantBtn;
	}

	public void setPlantBtn(JButton plantBtn) {
		this.plantBtn = plantBtn;
	}
	    
	public Icon getDiscardIcon() {
		return discardIcon;
	}

	public void setDiscardIcon(Icon discardIcon) {
		this.discardIcon = discardIcon;
	}

	public Icon getPlantIcon() {
		return plantIcon;
	}

	public void setPlantIcon(Icon plantIcon) {
		this.plantIcon = plantIcon;
	}
	public JPanel getHandPanel() {
		return handPanel;
	}

	public void setHandPanel(JPanel handPanel) {
		this.handPanel = handPanel;
	}
	
	//UTLITLY METHODS	


	

	public void addItemListeners(JRadioButton[] buttons) {
		for (JRadioButton b : buttons) {
			b.addItemListener((ItemListener) this);
		}
	}

	public void addToButtonGroup(JRadioButton[] buttons) {
		
		cardsButtons = new ButtonGroup();
		
		for (JRadioButton b : buttons) {
			cardsButtons.add(b);
		}
	}
	
	
	public void addCards(Player player) {

		handPanel = new JPanel();
		handPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		handPanel.setBackground(new Color(116,65,35));
		handPanel.setPreferredSize(new Dimension(1500, 160));
		
		int x = 0; 
		
		if (!player.getHand().isEmpty()) {
			
			//add the buttons to the panel and change its colour/font
			
			Queue<Card> tempHand = player.getHand();
			
			JRadioButton[] btns = new JRadioButton[tempHand.size()];

			int index = 0;
			
			for (Card c : tempHand ) {

				String name = c.getBeanType();
				ImageIcon cardImage = new ImageIcon("Images/Beans/"+name+".png"); //
				btns[index++] = new JRadioButton(name, cardImage);
			
			}
		
			setCardsInHand(btns);
			
			for (JRadioButton b : cardsInHand) {
				b.setForeground(Color.WHITE);
				b.setFont(new Font("Helvetica", Font.BOLD, 12));
				b.setBounds(x, 50, 111, 154);
				handPanel.add(b);
				x+= 125;
			}
			
		}
	}
}
