package Puppets;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.*;
import javax.swing.*;

public class CowPuppet extends Puppet{

	public CowPuppet(int x, int y, int player) {
		super(x, y, player);
	}
	
	@Override
	public void render(JButton targetSquare) {
		// TODO Auto-generated method stub
		targetSquare.setText("●");
		targetSquare.setForeground(Color.BLACK);
		targetSquare.setFont(new Font("Arial", Font.BOLD, 80));
		targetSquare.setHorizontalAlignment(SwingConstants.CENTER);
		targetSquare.setVerticalAlignment(SwingConstants.CENTER);	
		targetSquare.repaint();
	}
}
