package Game;

import javax.swing.*;

import GUI.Button_GUI;


import java.awt.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Side_choice {

	 public Side_choice(List<PlayersAndResult> results) {
		 	
	        JFrame f = new JFrame("Choose Your Side");
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setSize(800, 400);
	        f.setLayout(new BorderLayout());
	        f.setResizable(true);

	        // Cím hozzáadása
	        JPanel titlePanel = new JPanel();
	        titlePanel.setBackground(Color.BLACK);
	        JLabel titleLabel = new JLabel("Choose Your Side", SwingConstants.CENTER);
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
	        titleLabel.setForeground(Color.WHITE);
	        titlePanel.add(titleLabel);
	        f.add(titlePanel, BorderLayout.NORTH);

	        // Gombok panelje
	        JPanel p = new JPanel();
	        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
	        p.setAlignmentX(Component.CENTER_ALIGNMENT);
	        p.setBackground(Color.BLACK);

	        JButton option_cows = new JButton("Play with cows");
	        JButton option_leopard = new JButton("Play with the leopard");
	        JButton option_back = new JButton("Back");
	        JButton option_exit = new JButton("Exit");

	        // Gombok stílusának beállítása
	        Button_GUI buttonGUI = new Button_GUI();
	        buttonGUI.button_options(40, 180, Color.LIGHT_GRAY, Component.CENTER_ALIGNMENT, option_cows, option_leopard, option_back, option_exit);
	        
	        // Gombok eseménykezelése
	        Map<JButton, Runnable> actions = new HashMap<JButton, Runnable>();
	        actions.put(option_cows, () -> {
	        	f.dispose();
	            Game game = new Game(results);
	            game.startCowSingleplayerGame("");
	        });
	        
	        actions.put(option_leopard, () -> {
	        	f.dispose();
	            Game game = new Game(results);
	            game.startLeopardSingleplayerGame("");
	        });
	        
	        actions.put(option_back, () -> {
	            f.dispose();
	            new GameChoice(results);; 
	        });
	        
	        actions.put(option_exit, () -> {
	            System.exit(0); 
	        });

	        buttonGUI.setup_buttons(f, actions);

	        p.add(Box.createVerticalStrut(20));
	        p.add(option_cows);
	        p.add(Box.createVerticalStrut(10));
	        p.add(option_leopard);
	        p.add(Box.createVerticalStrut(10));
	        p.add(option_back);
	        p.add(Box.createVerticalStrut(10));
	        p.add(option_exit);
	        p.add(Box.createVerticalStrut(20));

	        f.add(p, BorderLayout.CENTER);

	        f.setVisible(true);
	    }

}
