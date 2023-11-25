/* Aaron Su
 * 
 */
// // sourced from https://www.javatpoint.com/java-jscrollpane

package View;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class InstructionFrame extends JFrame implements ActionListener {

	private JPanel panel = new JPanel();
	private JButton exitBtn = new JButton();
	private JLabel[] imageLabel = new JLabel[7];

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
		JScrollPane scrollBar = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // create our scroll pane

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // use box layout on the Y-axis to make it pack
																	// vertically

		// loop through the images and add them to the JPanel
		for (int i = 0; i < imageLabel.length; i++) {
			imageLabel[i] = new JLabel();

			imageLabel[i].setIcon(new ImageIcon(new ImageIcon("Images/Instructions/Bohnanza Rules-" + (i + 1) + ".png")
					.getImage().getScaledInstance(800, 950, Image.SCALE_SMOOTH)));

			panel.add(imageLabel[i]); // add the image to the JPanel
		}

		scrollBar.getViewport().add(panel); // add the panel to the scroll bar
		scrollBar.setBounds(100, 150, 800, 500); // set the size and position of our scroll pane
		scrollBar.getViewport().setOpaque(false); // Sets the opacity of the viewport component of scrollBar to false
		// scrollBar.setOpaque(false); // Sets the opacity of the scrollBar to false
		scrollBar.getViewport().setBorder(null); // Disables the outline of the border

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
