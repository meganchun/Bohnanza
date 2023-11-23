package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HomeFrame extends JFrame implements ActionListener{

	private JLabel background = new JLabel(new ImageIcon("Images/homeBackground.png"));
	
	private Icon startBtnIcon = new ImageIcon("Images/startGameBtn.png");
	private Icon instructionBtnIcon = new ImageIcon("Images/instructionBtn.png");
	private Icon exitBtnIcon = new ImageIcon("Images/exitBtn.png");
	
	private JButton startBtn;
	private JButton instructionBtn;
	private JButton exitBtn;
	
	public HomeFrame() {
		
		this.setTitle("Bohnanza"); 
		this.setResizable(false); 
		this.setSize(1000, 700); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLocationRelativeTo(null);
		
		add(background);
		
		startBtn = new JButton(startBtnIcon);
		startBtn.setBounds(383, 350, 217, 48);
		startBtn.addActionListener(this);
		background.add(startBtn);
		
		instructionBtn = new JButton(instructionBtnIcon);
		instructionBtn.setBounds(383, 415, 217, 48);
		instructionBtn.addActionListener(this);
		background.add(instructionBtn);
		
		exitBtn = new JButton(exitBtnIcon);
		exitBtn.setBounds(383, 480, 217, 48);
		exitBtn.addActionListener(this);
		background.add(exitBtn);
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == startBtn) {
			new ModeSelectFrame();
			this.dispose();
		}
		else if (e.getSource() == instructionBtn) {
			
		}
		else if (e.getSource() == exitBtn) {
			this.dispose();
		}
			
	
	}

}
