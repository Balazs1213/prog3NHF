package Game;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Board.BoardRenderer;
import Board.ChessBoard;


public class Game {
	private ChessBoard board;
	private List<PlayersAndResult> results;
   
   public Game(List<PlayersAndResult> results) {
	   this.results = results;
	   this.board = new ChessBoard();   
   }
   
   public void startCowSingleplayerGame(String playerName) {
	   if(playerName == "") {
		   playerName = promptForPlayerName("Enter your name for the Cow Player:");
		   handleEmptyPlayerName(playerName);
	   }
	   
	   if (playerName != null && !playerName.trim().isEmpty()) {
           PlayersAndResult playersAndResult = new PlayersAndResult(playerName, "AI", true);
           BoardRenderer br = new BoardRenderer(board, false, playersAndResult, results); 
       }
   }
   
   public void startLeopardSingleplayerGame(String playerName) {
	   if(playerName == "") {
		   playerName = promptForPlayerName("Enter your name for the Cow Player:");
		   handleEmptyPlayerName(playerName);
	   }
	   if (playerName != null && !playerName.trim().isEmpty()) {
           PlayersAndResult playersAndResult = new PlayersAndResult("AI", playerName, true);

           System.out.println("Cow Player: " + playersAndResult.getCowPlayer());
           System.out.println("Leopard Player: " + playersAndResult.getLeopardPlayer());
           System.out.println("Cow won: " + playersAndResult.getisCowWon());
           BoardRenderer br = new BoardRenderer(board, true, playersAndResult, results);
       }
   }
   
   public void startMultiplayerGame(String cowPlayerName, String leopardPlayerName) {
	   if(cowPlayerName == "" && leopardPlayerName =="") {
		    cowPlayerName =  promptForPlayerName("Enter your name for the Cow Player:");
		    leopardPlayerName =  promptForPlayerName("Enter your name for the Leopard Player:");
		   if((cowPlayerName == null || cowPlayerName.trim().isEmpty()) || (leopardPlayerName == null || leopardPlayerName.trim().isEmpty())) {
			   GameChoice gc = new GameChoice(results);
		   }
	   }
	  
	   
	  
	   if ((cowPlayerName != null && !cowPlayerName.trim().isEmpty()) && (leopardPlayerName != null && !leopardPlayerName.trim().isEmpty())) {
           PlayersAndResult playersAndResult = new PlayersAndResult(cowPlayerName, leopardPlayerName, true);
           System.out.println("Cow Player: " + playersAndResult.getCowPlayer());
           System.out.println("Leopard Player: " + playersAndResult.getLeopardPlayer());
           System.out.println("Cow won: " + playersAndResult.getisCowWon());
           BoardRenderer br = new BoardRenderer(board, playersAndResult, results);
       }
   }
    
   public ChessBoard getBoard() {
       return board;
   }
  
   private String promptForPlayerName(String whichName) {
       JTextField playerNameField = new JTextField(40);
       JPanel panel = new JPanel(new BorderLayout());
       panel.add(new JLabel(whichName), BorderLayout.NORTH);
       panel.add(playerNameField, BorderLayout.CENTER);
       int option = JOptionPane.showConfirmDialog(null, panel, "Player Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
       if (option == JOptionPane.OK_OPTION) {
           return playerNameField.getText().trim();
       }
       return null; 
   }
   
   private void handleEmptyPlayerName(String playerName) {
	   if (playerName == null || playerName.trim().isEmpty()) {
	        Side_choice sd = new Side_choice(results);
	    }
   }
}
