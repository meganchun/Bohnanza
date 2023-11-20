/* Aaron Su
 * 
 */

// instruction frame if we ever choose to do so...

package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class InstructionFrame extends JFrame implements ActionListener {

	private JButton backBtn = new JButton();
	private JTextArea textBox = new JTextArea();
	
	public InstructionFrame() {
		JLabel background = new JLabel(new ImageIcon("Images/modeSelectBackground.png"));
		background.setBounds(0, 0, 1000, 700);
		add(background);
		
		Icon exitImage = new ImageIcon("Images/backBtn.png");
		backBtn = new JButton(exitImage);
		backBtn.setBounds(25,25,50, 50);
		backBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		backBtn.addActionListener(this);
		background.add(backBtn);
		
		this.setTitle("Mode Select"); 
		this.setResizable(false); 
		this.setSize(1000, 700); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			new ModeSelectFrame();
			this.dispose();
		}
		
	}
	
}
