//Megan Chun

package View;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.BohnanzaController;
import View.PlayerPanel;

public class GameFrame extends JFrame {

	private JLabel background = new JLabel( new ImageIcon("images/background.png"));

	//JPanels (player & common area)
	private PlayerPanel playerOnePanel;
	private PlayerPanel playerTwoPanel;
	private CommonPanel commonPanel;
	
	GridBagConstraints layout;
	
	BufferedImage myImage;
	
	public GameFrame() {
		
		// specifications of the JFrame, to which will hold the JPanels
		setTitle("Bohnanza Game");
		setLayout(null);
		setSize(1000, 725);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		add(background);
		background.setBounds(0, 0, 1000, 700);
	
		playerOnePanel = new PlayerPanel(BohnanzaController.getPlayerOne());
		background.add(playerOnePanel);
		playerOnePanel.setBounds(0, 0, 1000, 257);
		playerOnePanel.setOpaque(false);
		
		commonPanel = new CommonPanel();
		background.add(commonPanel);
		commonPanel.setBounds(0, 240, 1000, 186);
		commonPanel.setOpaque(false);
		
		//Player two top panel
		playerTwoPanel = new PlayerPanel(BohnanzaController.getPlayerTwo());
		background.add(playerTwoPanel);
		playerTwoPanel.setBounds(0, 440, 1000, 257);
		playerTwoPanel.setOpaque(false);
		
		setVisible(true);
		
		
	}

	public PlayerPanel getPlayerOnePanel() {
		return playerOnePanel;
	}

	public void setPlayerOnePanel(PlayerPanel playerOnePanel) {
		this.playerOnePanel = playerOnePanel;
	}

	public PlayerPanel getPlayerTwoPanel() {
		return playerTwoPanel;
	}

	public void setPlayerTwoPanel(PlayerPanel playerTwoPanel) {
		this.playerTwoPanel = playerTwoPanel;
	}

	public CommonPanel getCommonPanel() {
		return commonPanel;
	}

	public void setCommonPanel(CommonPanel common) {
		this.commonPanel = common;
	}
	
	

}