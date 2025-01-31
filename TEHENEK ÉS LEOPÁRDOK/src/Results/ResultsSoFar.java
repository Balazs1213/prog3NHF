package Results;

import Game.PlayersAndResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GUI.Button_GUI;
import GUI.Graphics;
import javax.swing.*;
import java.awt.*;

import Menü.Menu;


public class ResultsSoFar implements Graphics{
	private List<PlayersAndResult> results;
	
	public ResultsSoFar(List<PlayersAndResult> results) {
		this.results = results;
		this.show_GUI();
	}

	@Override
	public void show_GUI() {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Results So far");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setSize(800, 400);
	    frame.setLayout(new BorderLayout());

	    JLabel title = new JLabel("Results So Far", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 24));
	    frame.add(title, BorderLayout.NORTH);    
	       
	    JPanel contentPanel = new JPanel();
	    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	    contentPanel.setBackground(Color.BLACK);
	    
	    for (PlayersAndResult result : results) {
            String displayText = result.getCowPlayer() + " vs " + result.getLeopardPlayer() + " - " +
                    (result.getisCowWon() ? "Cows won" : "Leopard won");
            

            JLabel resultLabel = new JLabel(displayText);
            resultLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            if(result.getisCowWon()) {
            	resultLabel.setForeground(Color.white);
            }else {
            	resultLabel.setForeground(Color.ORANGE);
            }
            resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(resultLabel);
        }
	    
	    JScrollPane scrollPane = new JScrollPane(contentPanel);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    
	    //Alsó panel  gombokkal
	    JPanel bottomPanel = new JPanel();
	    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    bottomPanel.setBackground(Color.BLACK);
	    
	    JButton backButton = new JButton("Back");
	    JButton exitButton = new JButton("Exit");
	    
	    Button_GUI buttonGui = new Button_GUI();
	    buttonGui.button_options(40, 100, Color.LIGHT_GRAY, Component.CENTER_ALIGNMENT, backButton, exitButton);
	    
	    Map<JButton, Runnable> buttonActions = new HashMap<>();
	    buttonActions.put(backButton, () -> {
	        frame.dispose(); // Bezárja az aktuális ablakot
	        new Menu(results); // Megnyitja a menüt (feltételezi, hogy van egy `Menu` osztály)
	    });
	    buttonActions.put(exitButton, () -> System.exit(0));
	    
	    buttonGui.setup_buttons(frame, buttonActions);
	    
	    bottomPanel.add(backButton);
	    bottomPanel.add(exitButton); 
	    
	    frame.add(bottomPanel, BorderLayout.SOUTH);
	    
	    
	    frame.setVisible(true);
	}
	
}
