package Game;

import javax.swing.*;

import Board.BoardRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Menü.*;
import Menü.Menu;
import Puppets.Puppet;
import GUI.*;

public class GameChoice extends JFrame {
	private List<PlayersAndResult> results;
	
	//Konstruktor
	public GameChoice(List<PlayersAndResult> results) {
		this.results = results;
        JFrame f = new JFrame("Choose a Game Type");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 400);
        f.setLayout(new BorderLayout());
        f.setResizable(true);

        // Cím hozzáadása
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        JLabel titleLabel = new JLabel("Choose a Game Type", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        f.add(titlePanel, BorderLayout.NORTH);

        // Gombok panelje
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setBackground(Color.BLACK);

        JButton option_singleplayer = new JButton("SINGLEPLAYER");
        JButton option_multiplayer = new JButton("MULTIPLAYER");
        JButton option_continue_saved_game = new JButton("CONTINUE SAVED GAME");
        JButton option_back = new JButton("BACK");
        JButton option_exit = new JButton("EXIT");

        //gombok beállítása
        Button_GUI buttonGUI = new Button_GUI();
        buttonGUI.button_options(40, 200, Color.LIGHT_GRAY, Component.CENTER_ALIGNMENT, option_singleplayer, option_multiplayer, option_continue_saved_game, option_back, option_exit);
        
        // Gombok eseménykezelése

        Map<JButton, Runnable> actions = new HashMap<JButton, Runnable>();
        actions.put(option_singleplayer, () -> {
            f.dispose();
            new Side_choice(results);
        });
        
        actions.put(option_multiplayer, () -> {
            f.dispose();
            Game game = new Game(results);
            game.startMultiplayerGame("","");
        });
        
        actions.put(option_continue_saved_game, () -> {
        	f.dispose();
        	SaveGame savedGame = new SaveGame("SavedGame.txt", results);
        	PlayersAndResult result = new PlayersAndResult(savedGame.getCowPlayer(), savedGame.getLeopardPlayer(),true);
        	BoardRenderer br = new BoardRenderer(savedGame.getPuppets(), result, results);
        });
        
        actions.put(option_back, () -> {
            f.dispose();
             new Menu(results);
        });
        
        actions.put(option_exit, () -> {
            System.exit(0); 
        });

        buttonGUI.setup_buttons(f, actions);
        
        p.add(Box.createVerticalStrut(20));
        p.add(option_singleplayer);
        p.add(Box.createVerticalStrut(10));
        p.add(option_multiplayer);
        p.add(Box.createVerticalStrut(10));
        p.add(option_continue_saved_game);
        p.add(Box.createVerticalStrut(10));
        p.add(option_back);
        p.add(Box.createVerticalStrut(10));
        p.add(option_exit);
        p.add(Box.createVerticalStrut(20));

        f.add(p, BorderLayout.CENTER);

        f.setVisible(true);
    }
		
}
