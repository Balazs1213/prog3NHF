package Game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Puppets.*;
import Game.PlayersAndResult;

public class SaveGame {
	private List<CowPuppet> cows;
	private LeopardPuppet leopard;
	private String cowPlayer;
	private String leopardPlayer;
	private String filename;
	private List<PlayersAndResult> results;
	
	
	public SaveGame(List<CowPuppet> cows, LeopardPuppet leopard, PlayersAndResult players,  List<PlayersAndResult> results) {
		this.results = results;
		this.cows = cows;
		this.leopard = leopard;
		this.cowPlayer = players.getCowPlayer();
		this.leopardPlayer = players.getLeopardPlayer();
		this.filename = "SavedGame.txt";
	}
	
	public SaveGame(List<CowPuppet> cows, LeopardPuppet leopard, PlayersAndResult players,  List<PlayersAndResult> results, String filename) {
		this.results = results;
		this.cows = cows;
		this.leopard = leopard;
		this.cowPlayer = players.getCowPlayer();
		this.leopardPlayer = players.getLeopardPlayer();
		this.filename = filename;
	}
	
	
	public SaveGame(String filename,  List<PlayersAndResult> results) {
        this.filename = filename;
        this.results = results;
        this.cows = new ArrayList<>();
        loadFromFile();
    }

	
	public void saveToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // 1-4. sor: Tehenek mentése
            for (int i = 0; i < 4; i++) {
                if (i < cows.size()) {
                    CowPuppet cow = cows.get(i);
                    String cowData = cow.getX() + ";" + cow.getY() + ";" + cow.getPlayer() + ";";
                    writer.write(cowData);
                } else {
                    writer.write(";"); //nincs rá szükség
                }
                writer.newLine();
            }

            if (leopard != null) {
                String leopardData = leopard.getX() + ";" + leopard.getY() + ";" + leopard.getPlayer() + ";";
                writer.write(leopardData);
            } else {
                writer.write(";"); //erre sincs
            }
            writer.newLine();

            String playerData = cowPlayer + ";" + leopardPlayer + ";";
            writer.write(playerData);

        } catch (IOException e) {
        	System.err.println("Error saving game: " + e.getMessage());
        }
	}

	private void loadFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
		    boolean isFileEmpty = true; // Jelzi, hogy tartalmaz-e adatot a fájl

		    for (int i = 0; i < 4; i++) {
		        String line = reader.readLine();
		        if (line != null && !line.trim().isEmpty()) {
		            isFileEmpty = false;
		            String[] data = line.split(";");
		            if (data.length >= 3) {
		                int x = Integer.parseInt(data[0]);
		                int y = Integer.parseInt(data[1]);
		                int player = Integer.parseInt(data[2]);
		                cows.add(new CowPuppet(x, y, player));
		            }
		        }
		    }

		    String leopardLine = reader.readLine();
		    if (leopardLine != null && !leopardLine.trim().isEmpty()) {
		        isFileEmpty = false;
		        String[] data = leopardLine.split(";");
		        if (data.length >= 3) {
		            int x = Integer.parseInt(data[0]);
		            int y = Integer.parseInt(data[1]);
		            int player = Integer.parseInt(data[2]);
		            leopard = new LeopardPuppet(x, y, player); 
		        }
		    }

		    String playerLine = reader.readLine();
		    if (playerLine != null && !playerLine.trim().isEmpty()) {
		        isFileEmpty = false;
		        String[] data = playerLine.split(";");
		        if (data.length >= 2) {
		            cowPlayer = data[0];
		            leopardPlayer = data[1];
		        }
		    }

		    // Ha a fájl üres marad
		    if (isFileEmpty) {
		        throw new IOException("The save file is empty.");
		    }

		} catch (IOException e) {
		    JOptionPane.showMessageDialog(null, 
		        "No saved game found or the file is empty!", 
		        "Error", 
		        JOptionPane.ERROR_MESSAGE);
		    new GameChoice(results);
		}

	}
	
	//Getterek
	public List<CowPuppet> getCows() { return cows; }
	public LeopardPuppet getLeopard() { return leopard; }
	public String getCowPlayer() { return cowPlayer; }
	public String getLeopardPlayer() { return leopardPlayer; }
	public List<Puppet> getPuppets(){
		List<Puppet> puppets = new ArrayList<Puppet>();
		puppets.addAll(cows);
		puppets.add(leopard);
		return puppets;
	}
	
}
