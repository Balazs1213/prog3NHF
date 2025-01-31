package Game;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import GUI.*;
import Menü.Menu;

public class EndOfTheGame {
	private List<PlayersAndResult> results;
	private String resultsFilename;
	private PlayersAndResult currentGame;

	
	
	public EndOfTheGame() {
	     results = new ArrayList<>();
	     resultsFilename = "Results.txt";
	     this.showOptions();
	}
	
	public EndOfTheGame(List<PlayersAndResult> results, PlayersAndResult currentgame) {
		this.results = results;
		this.resultsFilename = "Results.txt";
		this.currentGame = currentgame;
		this.showOptions();
	}
	
	public EndOfTheGame(List<PlayersAndResult> results, PlayersAndResult currentgame, String fileName) {
		this.results = results;
		this.resultsFilename = fileName;
		this.currentGame = currentgame;
		this.showOptions();
	}
	
	public EndOfTheGame(PlayersAndResult currentgame) {
		this.results = new ArrayList<>();
		this.resultsFilename = "Results.txt";
		this.currentGame = currentgame;
		this.showOptions();
	}
	
	public void showAfterSave() {
		pageGUI pg = new pageGUI("After Save","Do you want to return to the menu?");
		
		Button_GUI buttonGui = new Button_GUI();
	    JButton menuButton = new JButton("Back to menu");
	    JButton exitButton = new JButton("Exit");
	    buttonGui.button_options(40, 150, Color.LIGHT_GRAY, Component.CENTER_ALIGNMENT, menuButton, exitButton);
	    
	    Map<JButton, Runnable> buttonActions = new HashMap<>();  
        buttonActions.put(menuButton, () -> {
        	pg.getFrame().dispose();
            Menu menu = new Menu(results);
        });
        
        buttonActions.put(exitButton, () -> {
            System.exit(0); 
        });

        buttonGui.setup_buttons(pg.getFrame(), buttonActions);
        
        pg.getButtonPanel().add(Box.createVerticalStrut(20));
        pg.getButtonPanel().add(menuButton);
        pg.getButtonPanel().add(Box.createVerticalStrut(10));
        pg.getButtonPanel().add(exitButton);
        pg.getButtonPanel().add(Box.createVerticalStrut(20));
        
        pg.getFrame().add(pg.getButtonPanel(), BorderLayout.CENTER);

        
        pg.getFrame().setVisible(true);
	      
		
	}
	
	public void saveGame() {
		if(currentGame == null) {
			System.err.println("No current gamet to save!");
			return;
		}
		results.add(0,currentGame);
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(resultsFilename))){
			for(PlayersAndResult game : results) {
				String formattedData = game.getCowPlayer() + ";" + game.getLeopardPlayer() + ";" + (game.getisCowWon() ? "0;" : "1;");
				writer.write(formattedData);
				writer.newLine();
			}
		} catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }	   
	}
		
	public void showOptions() {
		
		pageGUI pg = new pageGUI("End of the Game", "Would you like to save the result of the game?");
		
        Button_GUI buttonGui = new Button_GUI();
        JButton saveButton = new JButton("Save the result");
        JButton menuButton = new JButton("Back to menu");
        JButton exitButton = new JButton("Exit");

        buttonGui.button_options(40, 150, Color.LIGHT_GRAY, Component.CENTER_ALIGNMENT, saveButton, menuButton, exitButton);

        Map<JButton, Runnable> buttonActions = new HashMap<>();
        buttonActions.put(saveButton, () -> {
        	pg.getFrame().dispose();
        	saveGame();
        	showAfterSave();
        });
        
        buttonActions.put(menuButton, () -> {
        	pg.getFrame().dispose();
            Menu menu = new Menu(results);
        });
        
        buttonActions.put(exitButton, () -> {
            System.exit(0); 
        });

        buttonGui.setup_buttons(pg.getFrame(), buttonActions);

        pg.getButtonPanel().add(Box.createVerticalStrut(20));
        pg.getButtonPanel().add(saveButton);
        pg.getButtonPanel().add(Box.createVerticalStrut(10));
        pg.getButtonPanel().add(menuButton);
        pg.getButtonPanel().add(Box.createVerticalStrut(10));
        pg.getButtonPanel().add(exitButton);
        pg.getButtonPanel().add(Box.createVerticalStrut(20));
        
        pg.getFrame().add(pg.getButtonPanel(), BorderLayout.CENTER);

        
        pg.getFrame().setVisible(true);
    }
}
