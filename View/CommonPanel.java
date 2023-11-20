/* Aaron Su
 * 
 */
package View;

import javax.swing.*;

@SuppressWarnings("serial")
public class CommonPanel extends JPanel {

	// instance variables
	private JButton discardDeck = new JButton();
	private JButton deck = new JButton();
	private JButton[][] slots = new JButton[1][3];
	private JButton discardButton = new JButton();
	private JButton endExtendBtn = new JButton();

	// constructor
	public CommonPanel() {
		setLayout(null);

		// set images for the JButtons
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
		discardDeck.setBounds(86, 35, 110, 153);
		add(discardDeck);
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
		discardButton.setBounds(860, 70, 116, 35);
		add(discardButton);
		endExtendBtn.setBounds(860, 120, 116, 35);
		add(endExtendBtn);

		setOpaque(false);
	}

	// getters and setters
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

}