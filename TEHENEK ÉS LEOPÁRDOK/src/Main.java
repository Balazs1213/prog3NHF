

import Menü.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;  //Puppet teszthez

import Board.Cell;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;

import Game.*;
import Game.EndOfTheGame;
import Results.ResultsSoFar; //csak teszthez
import Puppets.*; //csak teszthez
import Board.*;

public class Main {
	public static void main(String args[]) {
		BeforeMenu bf = new BeforeMenu();
		List<PlayersAndResult> pr = bf.getResults();
		Menu menu = new Menu(pr);
	}
}
