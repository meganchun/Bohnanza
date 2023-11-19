//Megan Chun

package View;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Controller.BohnanzaController;
public class ModeSelectFrame extends JFrame implements ActionListener{
	
	JButton startBtn;
	JButton exitBtn;
	JButton easyBtn;
	JButton difficultBtn;
	
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
		    
		JLabel selectImage = new JLabel(new ImageIcon("Images/Select Mode.png"));
		selectImage.setBounds(325, 75, 385, 50);
		background.add(selectImage);
		
		Icon startImage = new ImageIcon("Images/startBtn.png");
		startBtn = new JButton(startImage);
		startBtn.setBounds(200, 400, 217, 48);
		startBtn.addActionListener(this);
		background.add(startBtn);
		
		Icon exitImage = new ImageIcon("Images/backBtn.png");
		exitBtn = new JButton(exitImage);
		exitBtn.setBounds(25,25,50, 50);
		exitBtn.addActionListener(this);
		background.add(exitBtn);

		Icon easyImage = new ImageIcon("Images/easyBtn.png");
		easyBtn = new JButton(easyImage);
		easyBtn.setBounds(600, 400, 217, 48);
		easyBtn.addActionListener(this);
		background.add(easyBtn);
		
		Icon difficultImage = new ImageIcon("Images/difficultBtn.png");
		difficultBtn = new JButton(difficultImage);
		difficultBtn.setBounds(600, 475, 217, 48);
		difficultBtn.addActionListener(this);
		background.add(difficultBtn);
		
		this.setVisible(true);
		
		
	}
	//Action performed for when clicking buttons
    public void actionPerformed(ActionEvent e) {
    	
		if (e.getSource() == startBtn) {
			
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
