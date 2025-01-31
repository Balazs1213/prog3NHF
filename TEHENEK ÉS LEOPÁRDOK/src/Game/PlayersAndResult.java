package Game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PlayersAndResult {
	private String cowPlayer;
	private String leopardPlayer;
	private boolean isCowWon;
	
	
	public String getCowPlayer() { return cowPlayer; }
	public String getLeopardPlayer() { return leopardPlayer; }
	public boolean getisCowWon() { return isCowWon; }
	
	public void setisCowWon(boolean isCowWon) { this.isCowWon = isCowWon; }
	
	public PlayersAndResult(String cowPlayer, String leopardPlayer, boolean isCowWon) {
		// TODO Auto-generated constructor stub
		this.cowPlayer = cowPlayer;
		this.leopardPlayer = leopardPlayer;
		this.isCowWon = isCowWon;
	}
	
	public void displayData(String output, String filename) {
		String result = cowPlayer + ";" + leopardPlayer + ";" + (isCowWon ? "0;" : "1;");
		if ("console".equalsIgnoreCase(output)) {
            //Konzolra írás
            System.out.println(result);
        } else if ("file".equalsIgnoreCase(output) && filename != null) {
            //Fájlba írás
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                writer.write(result);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.err.println("Invalid output type or missing filename.");
        }
	}
	
}
