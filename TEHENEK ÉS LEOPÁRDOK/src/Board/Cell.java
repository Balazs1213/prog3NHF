package Board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import Puppets.*;

public class Cell {
	private int x;
	private int y;
	private JButton button;
	private Puppet occupiedBy;
	private Color color;
	
	public Cell(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.button = createButton();
		this.occupiedBy = null;
	}
	
	//Gomb konfigurálása
	private JButton createButton() {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(100, 100));
        btn.setFont(new Font("Arial", Font.BOLD, 40));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setBackground(Color.LIGHT_GRAY);
        return btn;
    }
	
	//foglaltásg ellenőrzése
	public boolean isOccupied() {
		return occupiedBy != null;
	}
	
	//Szín ellenőrzése
	public boolean isDark() {
		return this.color.equals(Color.DARK_GRAY);
	}
	
	//getterek
	public Puppet getOccupiedBy() { return occupiedBy; }
	public JButton getButton() { return button; }
	public int getX() { return x; }
	public int getY() { return y; }
	public Color getColor() { return color; }
	
	//setterek
	public void setPuppet(Puppet puppet) { this.occupiedBy = puppet; }
	public void setButton(JButton button) { this.button = button; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setColor(Color color) { this.color = color; }
	
	//bábú törlése
	public void clearPuppet() {
		this.occupiedBy = null;
	}
	
	//renderelés
	public void render() {
		button.setBackground(color);
        if (occupiedBy != null) {
            occupiedBy.render(button);
        }
	}
	
	//kiemelés a lépéshez
	public void highlight() {
		button.setBackground(Color.yellow);
	}
	
	//kiemelés eltávolítása
	public void clearHighlight() {
		button.setBackground(Color.LIGHT_GRAY);
	}
}
