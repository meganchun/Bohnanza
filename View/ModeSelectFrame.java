//Megan Chun

package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Controller.BohnanzaController;
public class ModeSelectFrame extends JFrame implements ActionListener{
	
	//instance variables
	private JButton startBtn;
	private JButton exitBtn;
	private JButton easyBtn;
	private JButton difficultBtn;
	
	private JLabel modeBackgroundOne;
	private JLabel modeBackgroundTwo;
	
	private JTextField nameOne;
	private JTextField nameTwo;
	
	public static String strNameOne;
	public static String strNameTwo;
	
	public ModeSelectFrame() {
		
		this.setTitle("Mode Select"); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLocationRelativeTo(null);
		this.setResizable(false); 
		this.setSize(1000, 700); 
	
		//Megan - Update needed, Benjamin's version did not work
		JLabel background = new JLabel(new ImageIcon("Images/modeSelectBackground.png"));
		background.setBounds(0, 0, 1000, 700);
		add(background);
		
		
		modeBackgroundOne = new JLabel(new ImageIcon("Images/modeBackground.png"));
		modeBackgroundOne.setBounds(114, 170, 333, 429);
		background.add(modeBackgroundOne);
		
		modeBackgroundTwo = new JLabel(new ImageIcon("Images/modeBackground.png"));
		modeBackgroundTwo.setBounds(552, 170, 333, 429);
		background.add(modeBackgroundTwo);
		
		JLabel selectImage = new JLabel(new ImageIcon("Images/Select Mode.png"));
		selectImage.setBounds(325, 75, 385, 50);
		background.add(selectImage);
		
		nameOne = new JTextField("Player One's Name");
		nameOne.addActionListener(this);
		nameOne.setFont(new Font("Helvetica", Font.PLAIN, 16));
		nameOne.setBounds(58, 200, 217, 48);
		modeBackgroundOne.add(nameOne);
		
		nameTwo = new JTextField("Player Two's Name");
		nameTwo.addActionListener(this);
		nameTwo.setFont(new Font("Helvetica", Font.PLAIN, 16));
		nameTwo.setBounds(58, 250, 217, 48);
		modeBackgroundOne.add(nameTwo);
		
		Icon startImage = new ImageIcon("Images/startBtn.png");
		startBtn = new JButton(startImage);
		startBtn.setBounds(58, 325, 217, 48);
		startBtn.addActionListener(this);
		modeBackgroundOne.add(startBtn);
		
		Icon exitImage = new ImageIcon("Images/backBtn.png");
		exitBtn = new JButton(exitImage);
		exitBtn.setBounds(25,25,50, 50);
		exitBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		exitBtn.addActionListener(this);
		background.add(exitBtn);

		Icon easyImage = new ImageIcon("Images/easyBtn.png");
		easyBtn = new JButton(easyImage);
		easyBtn.setBounds(58, 225, 217, 48);
		easyBtn.addActionListener(this);
		modeBackgroundTwo.add(easyBtn);
		
		Icon difficultImage = new ImageIcon("Images/difficultBtn.png");
		difficultBtn = new JButton(difficultImage);
		difficultBtn.setBounds(58, 300, 217, 48);
		difficultBtn.addActionListener(this);
		modeBackgroundTwo.add(difficultBtn);
		
		this.setVisible(true);
		
		
	}
	//Action performed for when clicking buttons
    public void actionPerformed(ActionEvent e) {
    	
		if (e.getSource() == startBtn) {
			
			strNameOne = nameOne.getText();
			strNameTwo = nameTwo.getText();
			
			BohnanzaController bc = new BohnanzaController();
			this.dispose(); //gets rid of the previous window
			
		}
		
		else if (e.getSource() == exitBtn) {
			this.dispose();//gets rid of the previous window
		
		}	
		
		else if (e.getSource() == easyBtn) {
			this.dispose();//gets rid of the previous window
		
		}	
	
		else if (e.getSource() == difficultBtn) {
			this.dispose();//gets rid of the previous window
			//SurveyMenu surveyWindow = new SurveyMenu();
		}	
	}
 
}
