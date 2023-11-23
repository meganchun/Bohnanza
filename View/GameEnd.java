package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Model.Player;

public class GameEnd extends JFrame implements ActionListener {

	private JLabel background = new JLabel(new ImageIcon("Images/gameEndBackground.png"));
	
	private Icon homeBtnIcon;
	private JButton homeBtn;
	
	private JLabel winnerLabel;
	private JLabel winner;
	
	public GameEnd(Player player) {
		
		this.setTitle("Bohnanza"); 
		this.setResizable(false); 
		this.setSize(1000, 700); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLocationRelativeTo(null);
		
		add(background);
		
		winnerLabel = new JLabel(new ImageIcon("Images/winnerLabel.png"));
		winnerLabel.setBounds(352, 213, 350, 100);
		background.add(winnerLabel);
		
		winner = new JLabel(player.getName(),SwingConstants.CENTER);
		winner.setFont(new Font("Helvetica", Font.BOLD, 20));
		background.add(winner);
		
		
		homeBtnIcon = new ImageIcon("Images/homeBtn.png");
		homeBtn = new JButton(homeBtnIcon);
		homeBtn.setBounds(384, 442, 217, 48);
		homeBtn.addActionListener(this);
		background.add(homeBtn);
		
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
		
		if (e.getSource() == homeBtn) {
			
			ModeSelectFrame modeSelect = new ModeSelectFrame();
			this.dispose();
		}
		
	}

}
