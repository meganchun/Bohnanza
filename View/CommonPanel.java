//Aaron Su

package View;

import javax.swing.*;

@SuppressWarnings("serial")
public class CommonPanel extends JPanel {

	// instance variables
	private JButton garbageDeck = new JButton();
	private JButton deck = new JButton();
	private JButton[][] slots = new JButton[1][3];
	private JButton discardButton = new JButton();

	// constructor
	public CommonPanel() {
		setLayout(null);

		// set images for the JButtons
		garbageDeck.setIcon(new ImageIcon("Images/garbageDeck.png"));
		deck.setIcon(new ImageIcon("Images/cardDeck.png"));
		discardButton.setIcon(new ImageIcon("Images/discardBtn.png"));

		// remove the background from every button
		garbageDeck.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		deck.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		discardButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// pack the JButtons by settingBounds
		garbageDeck.setBounds(86, 35, 110, 153);
		add(garbageDeck);
		deck.setBounds(283, 35, 110, 153);
		add(deck);

		// initialize the JButtons for the slots
		int xCord = 433;
		for (int i = 0; i < 3; i++) {
			slots[0][i] = new JButton();
			slots[0][i].setEnabled(false);
			slots[0][i].setIcon(new ImageIcon("Images/slotsBtn.png"));
			slots[0][i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			slots[0][i].setBounds(xCord, 35, 110, 153);
			add(slots[0][i]);

			xCord += 145;
		}

		// pack after the slots JButtons are packed
		discardButton.setBounds(869, 50, 60, 60);
		add(discardButton);

		setOpaque(false);
	}

	// getters and setters
	public JButton getGarbageDeck() {
		return garbageDeck;
	}

	public void setGarbageDeck(JButton garbageDeck) {
		this.garbageDeck = garbageDeck;
	}

	public JButton getDeck() {
		return deck;
	}

	public void setDeck(JButton deck) {
		this.deck = deck;
	}

	public JButton[][] getSlots() {
		return slots;
	}

	public void setSlots(JButton[][] slots) {
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

}