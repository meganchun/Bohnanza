//Megan Chun

package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Card;
import Model.Player;

public class PlayerPanel extends JPanel {
	
	//Instance variables
	private String name;
	private int score;
	
	//GUI Attributes
	private JLabel nameLabel;
	private JLabel scoreImage;
	private JLabel scoreNum;
	
	//JPanels
	private PlayerHandPanel hand;
	private PlayerFieldPanel field;
	
	public PlayerPanel(Player player) {
		
		setLayout(null);
		setName(player.getName());
		
		//PANELS IN THE PLAYERS PORTION
		JPanel upper = new JPanel();
		upper.setOpaque(false);
		upper.setLayout(new FlowLayout(FlowLayout.LEFT));
		upper.setBounds(0, 0, 400, 40);
		add(upper);
		
		hand = new PlayerHandPanel(player);
		hand.setBounds(0,40,400,217);
		hand.setOpaque(false);
		add(hand);
	
		field = new PlayerFieldPanel();
		field.setBounds(400,0,600,257);
		field.setOpaque(false);
		add(field);

		//NAME TAG AND COIN COUNTER
		//name tag
		nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
		nameLabel.setForeground(Color.white);
		upper.add(nameLabel);
		
		//coin image label
		scoreImage = new JLabel(new ImageIcon("Images/scoreLabel.png"));
		upper.add(scoreImage);
		
		//coin counter
		scoreNum = new JLabel(Integer.toString(getScore()));
		scoreNum.setFont(new Font("Helvetica", Font.BOLD, 12));
		scoreNum.setForeground(new Color(98,98,98));
		scoreNum.setBounds(40, 13, 10, 12);
		scoreImage.add(scoreNum);
		
		
		setVisible(true);
	}

	public PlayerHandPanel getHand() {
		return hand;
	}

	public void setHand(PlayerHandPanel hand) {
		this.hand = hand;
	}

	public PlayerFieldPanel getField() {
		return field;
	}

	public void setField(PlayerFieldPanel field) {
		this.field = field;
	}

	public JLabel getNameLabel() {
		return nameLabel;
	}

	public void setNameLabel(JLabel nameLabel) {
		this.nameLabel = nameLabel;
	}

	public JLabel getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(JLabel scoreNum) {
		this.scoreNum = scoreNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}
