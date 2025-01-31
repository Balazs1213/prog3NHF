package Menü;

import javax.swing.*;

import GUI.Button_GUI;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import Game.*;
import Results.ResultsSoFar;



public class Menu extends JFrame {
	private List<PlayersAndResult> results;
	
	public Menu(List<PlayersAndResult> results) {
		
		this.results = results;
		JFrame f = new JFrame("Main Menu");
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setSize(800,400);
		f.setLayout(new BorderLayout());
		f.setResizable(true);
		
			
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.BLACK);
		JLabel titleLabel = new JLabel("Main Menu", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titlePanel.add(titleLabel); 
		f.add(titlePanel, BorderLayout.NORTH);
		

        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setBackground(Color.BLACK);
		
        JButton option_game = new JButton("GAME");
        JButton option_results = new JButton("RESULTS SO FAR");
        JButton option_exit = new JButton("EXIT");
        
        Button_GUI buttonGUI = new Button_GUI();
        buttonGUI.button_options(40, 150, Color.LIGHT_GRAY, Component.CENTER_ALIGNMENT, option_game, option_results, option_exit);
        
        
        //Eseménykezelés a gombokhoz
        Map<JButton, Runnable> actions = new HashMap<JButton, Runnable>();
        actions.put(option_game, () -> {
            f.dispose();
            new GameChoice(results);
        });
        
        actions.put(option_results, () -> {
            f.dispose();
            ResultsSoFar r = new ResultsSoFar(results);
        });
        
        actions.put(option_exit, () -> {
            System.exit(0); 
        });
        
        buttonGUI.setup_buttons(f, actions);
          
        p.add(Box.createVerticalStrut(20));
        p.add(option_game);
        p.add(Box.createVerticalStrut(10));
        p.add(option_results);
        p.add(Box.createVerticalStrut(10));
        p.add(option_exit);
        p.add(Box.createVerticalStrut(20));

        f.add(p, BorderLayout.CENTER);
		
		f.setVisible(true);
		
	}
	
	
}


