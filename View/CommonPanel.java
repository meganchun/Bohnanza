//Aaron Su

package View;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

@SuppressWarnings("serial")
public class CommonPanel extends JPanel {

	// instance variables
	private JButton discardDeck = new JButton();
	private JButton deck = new JButton();
	private JButton[] slots = new JButton[3];
	private JLabel deckCounter = new JLabel();
	private JLabel [] numCardLabel = new JLabel[3];
	private JButton discardButton = new JButton();
	private JButton endExtendBtn = new JButton();
	private JLabel stage = new JLabel("Current Stage:");
	private JTextArea currentStage = new JTextArea("Plant/Discard", 10, 10);


	// constructor
	public CommonPanel() {
		
		setLayout(null);

		//current stage label
		stage.setBounds(10,50,150,50);
		stage.setForeground(Color.WHITE);
		stage.setFont(new Font("Helvetica", Font.BOLD, 16));
		add(stage);
		
		currentStage.setBounds(10,100,125,50);
		currentStage.setForeground(Color.WHITE);
		currentStage.setEditable(false);
		currentStage.setOpaque(false);
		currentStage.setFont(new Font("Helvetica", Font.BOLD, 16));
		currentStage.setLineWrap(true);
		currentStage.setWrapStyleWord(true);
		add(currentStage);
		
		// set images for the JButtons
		discardButton.setIcon(new ImageIcon("Images/discardBtn.png"));
		discardDeck.setIcon(new ImageIcon("Images/discardDeck.png"));
		deck.setIcon(new ImageIcon("Images/Beans/Back.png"));
		discardButton.setIcon(new ImageIcon("Images/discardCardBtn.png"));
		endExtendBtn.setIcon(new ImageIcon("Images/extendedTurnEnd.png"));

		// remove the background from every button
		discardDeck.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		deck.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		discardButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		endExtendBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		discardDeck.setEnabled(false);
		endExtendBtn.setEnabled(false);

		// pack the JButtons by settingBounds
		discardDeck.setBounds(150, 35, 110, 153);
		add(discardDeck);
		deck.setBounds(283, 35, 110, 153);
		add(deck);
		
		// pack the card counter
		deckCounter.setText("");
		deckCounter.setFont(new Font("Helvetica", Font.BOLD, 16));
		deckCounter.setForeground(Color.WHITE);
		deckCounter.setBounds(333, 15, 20, 20);
		add(deckCounter);

		// initialize the JButtons for the slots
		int xCord = 433;
		for (int i = 0; i < 3; i++) {
			slots[i] = new JButton();
			slots[i].setEnabled(false);
			slots[i].setIcon(new ImageIcon("Images/slotsBtn.png"));
			slots[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			slots[i].setBounds(xCord, 35, 110, 153);
			
			numCardLabel[i] = new JLabel();
			numCardLabel[i].setText("0");
			numCardLabel[i].setFont(new Font("Helvetica", Font.BOLD, 16));
			numCardLabel[i].setForeground(Color.WHITE);
			numCardLabel[i].setBounds(xCord + 51, 15, 20, 20);
			
			add(numCardLabel[i]);
			add(slots[i]);

			xCord += 145;
		}

		// pack after the slots JButtons are packed
		discardButton.setBounds(869, 50, 60, 60);
		discardButton.setBounds(860, 70, 116, 35);
		add(discardButton);
		endExtendBtn.setBounds(860, 120, 116, 35);
		add(endExtendBtn);

		setOpaque(false);
	}

	public JButton getEndExtendBtn() {
		return endExtendBtn;
	}

	public void setEndExtendBtn(JButton endExtendBtn) {
		this.endExtendBtn = endExtendBtn;
	}

	public JButton getDiscardDeck() {
		return discardDeck;
	}
	
	public void setDiscardDeck(JButton discardDeck) {
		this.discardDeck = discardDeck;
	}
	public JButton getDeck() {
		return deck;
	}

	public void setDeck(JButton deck) {
		this.deck = deck;
	}

	public JButton[] getSlots() {
		return slots;
	}

	public void setSlots(JButton[] slots) {
		this.slots = slots;
	}

	public JButton getDiscardButton() {
		return discardButton;
	}

	public void setDiscardButton(JButton discardButton) {
		this.discardButton = discardButton;
	}

	public void displayDrawnCards() {

	}
	
	public JLabel[] getNumCardLabel() {
		return numCardLabel;
	}

	public void setNumCardLabel(JLabel[] numCardLabel) {
		this.numCardLabel = numCardLabel;
	}

	public JTextArea getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(JTextArea currentStage) {
		this.currentStage = currentStage;
	}
	
	public JLabel getDeckCounter() {
		return deckCounter;
	}

	public void setDeckCounter(JLabel deckCounter) {
		this.deckCounter = deckCounter;
	}

}