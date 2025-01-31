package Menü;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Game.*;

public class BeforeMenu {
	private List<PlayersAndResult> results;
	private String fileName;
	
	public BeforeMenu() {
		fileName = "Results.txt";
		results = new ArrayList<>();
		loadResultsFromFile();
	}
	
	public BeforeMenu(String fileName, List<PlayersAndResult> results) {
		this.fileName = fileName;
		this.results = results;
	}
	
	public void loadResultsFromFile() {
		 try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(";");
	                if (parts.length == 3) {
	                    String cowPlayer = parts[0];
	                    String leopardPlayer = parts[1];
	                    boolean isCowWon = "0".equals(parts[2]); 
	                    results.add(new PlayersAndResult(cowPlayer, leopardPlayer, isCowWon));
	                }
	            }
	        } catch (IOException e) {
	            System.err.println("Error reading file: " + e.getMessage());
	        }
	}
	
	public List<PlayersAndResult> getResults() {
		return results;
	}

}
