package Puppets;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.*;
import javax.swing.*;


public abstract class Puppet{
	protected int x;
	protected int y;
	protected int player; //cow esetén 1, leopard esetén 2
	
	//Konstruktor
	public Puppet(int x, int y, int player) {
		this.x = x;
		this.y = y;
		this.player = player;
	}
	
	//Getterek
	public int getX() { return x; }
	public int getY() { return y; }
	public int getPlayer() { return player; }
	
	//Setterek
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setPlayer(int player) { this.player = player; }

	//Megjelenítséhez
	public abstract void render(JButton button);
	
}
