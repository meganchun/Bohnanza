/* Aaron Su
 * 
 */

// // sourced from https://www.javatpoint.com/java-jscrollpane

package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class InstructionFrame extends JFrame implements ActionListener {

	private JButton exitBtn = new JButton();
	private JLabel imageLabel = new JLabel();

	public InstructionFrame() {
		
		JLabel background = new JLabel(new ImageIcon("Images/instructionBackground.png"));
		background.setBounds(0, 0, 1000, 700);
		add(background);

		exitBtn.setIcon(new ImageIcon("Images/backBtn.png"));
		exitBtn.setBounds(25, 25, 50, 50);
		exitBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		exitBtn.addActionListener(this);
		background.add(exitBtn);

		// sourced from https://www.javatpoint.com/java-jscrollpane
		JScrollPane scrollBar = new JScrollPane(imageLabel); // create our scroll pane, and stick our rule book to
																	// it
		scrollBar.setBounds(265, 150, 475, 500); // set the size and position of our scroll pane
		scrollBar.getViewport().setOpaque(false); // Sets the opacity of the viewport component of scrollBar to false
		// scrollBar.setOpaque(false); // Sets the opacity of the scrollBar to false
		scrollBar.setBorder(null); // Disables the outline of the border
		
		background.add(scrollBar);

		this.setTitle("Mode Select");
		this.setResizable(false);
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitBtn) {
			new HomeFrame(); // open the previous frame
			this.dispose(); // get rid of the current frame
		}

	}

}
