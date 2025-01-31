package Puppets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class LeopardPuppet extends Puppet {

    public LeopardPuppet(int x, int y, int player) {
        super(x, y, player);
    }

	@Override
	public void render(JButton targetSquare) {
		// TODO Auto-generated method stub
		targetSquare.setText("●"); 
        targetSquare.setForeground(Color.white);
        targetSquare.setFont(new Font("Arial", Font.BOLD, 80));
        targetSquare.setHorizontalAlignment(SwingConstants.CENTER);
        targetSquare.setVerticalAlignment(SwingConstants.CENTER);
        targetSquare.repaint();
	}
	


	
}