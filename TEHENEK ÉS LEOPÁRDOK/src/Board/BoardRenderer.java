package Board;

import javax.swing.*;
import Menü.Menu;
import Game.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import Puppets.*;
import Game.EndOfTheGame;
import Game.PlayersAndResult;

public class BoardRenderer {
	private ChessBoard board;
	private JFrame frame;
	private JPanel boardPanel;
	private Puppet selectedPuppet;
	private int currentPlayer;
	private boolean isMultiPlayer;
	private PlayersAndResult result;
	private List<PlayersAndResult> results_list;
	private int stepsCoutn;
	

	//Multiplayer indításához
	public BoardRenderer(ChessBoard board, PlayersAndResult result, List<PlayersAndResult> results) {
		this.results_list = results;
		this.board = board;
		this.isMultiPlayer = true;
		this.currentPlayer = 0;
		this.result = result;
		initializeFrame();
		renderBoard(false);
	}
	
	//Singleplayer indításához
	public BoardRenderer(ChessBoard board, boolean startingWithLeopard, PlayersAndResult result, List<PlayersAndResult> results) {
		this.results_list = results;
		this.board = board;
		this.currentPlayer = startingWithLeopard ? 1 : 0;
		this.isMultiPlayer = false;
		this.result = result;
		initializeFrame();
        renderBoard(false);
	}
	
	private void incrementCurrentPlayer() { currentPlayer++; }
	
	private void initializeFrame() {
		frame = new JFrame("Chess Board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setLayout(new BorderLayout());
		
		boardPanel = new JPanel(new GridLayout(8, 8));
		frame.add(boardPanel, BorderLayout.CENTER);
		
		JLabel instructions = new JLabel("Select a puppet and then a target square.", SwingConstants.CENTER);
        instructions.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(instructions, BorderLayout.NORTH);
        
        addBottomPanel();
        
        frame.setVisible(true);
	}
	
	public void renderBoard(boolean issaved) {
		
        boardPanel.removeAll(); 
        Cell[][] cells = board.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = cells[i][j];
                JButton button = cell.getButton();

                ///NAGYON FONTOS 
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }
                
                // Hozzárendeljük az eseménykezelőt
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	if(issaved) {
                    		
                    		 String cowPlayer = result.getCowPlayer();
                             String leopardPlayer = result.getLeopardPlayer();
                             if (!"AI".equals(cowPlayer) && !"AI".equals(leopardPlayer)) {
                                 // Multiplayer indítás
                            	 System.out.println("IDe belép");
                                 multihandleCellClick(cell, currentPlayer, issaved);
                             } else if ("AI".equals(cowPlayer)) {
                                 // Cow játékos AI
                            	 singleHandleCellClick_withLeopard(cell, currentPlayer+1, issaved);  
                             } else if ("AI".equals(leopardPlayer)) {
                                 // Leopard játékos AI
                            	 singleHandleCellClick_withCows(cell, currentPlayer, issaved); 
                             } 
                    	} else {
                    		if(isMultiPlayer) {
                        		multihandleCellClick(cell,currentPlayer, issaved);
                        	}else {
                        		if(currentPlayer % 2 == 0) {
                        			singleHandleCellClick_withCows(cell, currentPlayer, issaved);
                        		}
                        		else {
                        			singleHandleCellClick_withLeopard(cell, currentPlayer, issaved);
                        		}
                        	}
                    	}
                    	
                    }
                });

                // Hozzáadjuk a cellát a GUI-hoz
                boardPanel.add(button);
                cell.render(); // Megjelenítjük a bábút is, ha van rajta
            }
        }

        boardPanel.revalidate(); // Újrarajzolás
        boardPanel.repaint();
    }
	
	private void singleHandleCellClick_withLeopard(Cell cell, int stepsCount, boolean isSaved) {
		
		if(selectedPuppet == null) {
			
			Puppet puppet = cell.getOccupiedBy();
			if(puppet != null) {
				
				if(stepsCount % 2 == 1 && puppet instanceof LeopardPuppet) {
					selectedPuppet = puppet;
					cell.highlight();
            		incrementCurrentPlayer();
            		incrementCurrentPlayer();
				}else {
                    JOptionPane.showMessageDialog(frame, "You must choose the leopard (white) puppet!");
                }
			}else {
                JOptionPane.showMessageDialog(frame, "No puppet on this cell!");
            }
		}
		else {
            // Ha már van kiválasztott bábu, próbáljuk meg léptetni
            int targetX = cell.getX();
            int targetY = cell.getY();

            if (board.movePuppet(selectedPuppet, targetX, targetY)) {
               selectedPuppet = null; // Lépés után visszaállítjuk
               renderBoard(isSaved);
               
               moveCowPuppet();
               
               renderBoard(isSaved); // Frissítjük a táblát
               selectedPuppet = null;

            } else {
                JOptionPane.showMessageDialog(frame, "Invalid move! Try again.");
            }
        }
		
		 if(checkVictory()) {
			 frame.dispose();
			 EndOfTheGame end = new EndOfTheGame(this.results_list, this.result);
		 }

	}

	private void singleHandleCellClick_withCows(Cell cell, int stepsCount, boolean isSaved) {
		
		if(selectedPuppet == null) {
			
			Puppet puppet = cell.getOccupiedBy();
			if(puppet != null) {
				if(stepsCount % 2 == 0 && puppet instanceof CowPuppet) {
					cell.highlight();
					selectedPuppet = puppet;
            		incrementCurrentPlayer();
            		incrementCurrentPlayer();
				}else {
                    JOptionPane.showMessageDialog(frame, "You must choose a cow (black) puppet!");
                }
			}else {
                JOptionPane.showMessageDialog(frame, "No puppet on this cell!");
            }
		}
		else {
            // Ha már van kiválasztott bábu, próbáljuk meg léptetni
            int targetX = cell.getX();
            int targetY = cell.getY();
            
            if (board.movePuppet(selectedPuppet, targetX, targetY)) {
               selectedPuppet = null; // Lépés után visszaállítjuk
               renderBoard(isSaved);
               
               moveLeopardPuppet();
               
               renderBoard(isSaved); // Frissítjük a táblát
               selectedPuppet = null;

            } else {
                JOptionPane.showMessageDialog(frame, "Invalid move! Try again.");
            }
        }
		 if(checkVictory()) {
			 frame.dispose();
			 EndOfTheGame end = new EndOfTheGame(this.results_list, this.result);
	        }

	}
	
	private void multihandleCellClick(Cell cell, int stepsCount, boolean isSaved) {		
        if (selectedPuppet == null) {

            // Ha még nincs kiválasztott bábu, próbáljuk meg kiválasztani
            Puppet puppet = cell.getOccupiedBy();
            if (puppet != null) {
            	if((stepsCount % 2 == 0 && puppet instanceof CowPuppet) || (stepsCount % 2 == 1 && puppet instanceof LeopardPuppet)) {
            		cell.highlight();
            		selectedPuppet = puppet;
            		incrementCurrentPlayer();
            	}else {
            		if(puppet instanceof LeopardPuppet) {           			
            			JOptionPane.showMessageDialog(frame, "You must choose a cow (black) puppet!");
            		} else {
            			JOptionPane.showMessageDialog(frame, "You must choose the leopard (white) puppet!");
            		}          
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No puppet on this cell!");
            }
        } else {
            int targetX = cell.getX();
            int targetY = cell.getY();
            if (board.movePuppet(selectedPuppet, targetX, targetY)) {
               selectedPuppet = null;
                renderBoard(isSaved); 
                selectedPuppet = null;
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid move! Try again.");
            }
        }
        if(checkVictory()) {
        	frame.dispose();
        	 EndOfTheGame end = new EndOfTheGame(this.results_list, this.result);
        }
        
    }
	
	public boolean checkVictory() {
		   for(int i = 0; i < 8; i++) {
			   Cell cell = board.getCells()[0][i];
			   if(cell.getOccupiedBy() != null && cell.getOccupiedBy().getPlayer() == 2) {
				   JOptionPane.showMessageDialog(null, "Leopard wins!");
				   result.setisCowWon(false);
	               return true;
			   }
		   }
		   
	       List<Puppet> puppets = board.getPuppets();
	       for (Puppet puppet : puppets) {
	           if (puppet instanceof LeopardPuppet && !board.getCellsForValidMoves(puppet).isEmpty()) {
	               return false;
	           }
	       }

	       JOptionPane.showMessageDialog(null, "Cows win!");
	       result.setisCowWon(true);
	       return true;

	   }
	

	
	private void moveLeopardPuppet() {
		Puppet leopardPuppet = null;
		for(Puppet puppet : board.getPuppets()) {
			if (puppet instanceof LeopardPuppet) {
	            leopardPuppet = puppet;
	            break;
	        }
		}
		
		if (leopardPuppet != null) {
	        // Lekérdezzük a lehetséges lépéseket
	        List<Cell> validMoves = board.getCellsForValidMoves(leopardPuppet);
	        if (!validMoves.isEmpty()) {
	            // Véletlenszerű lépés választása
	            Random rand = new Random();
	            int randomIndex = rand.nextInt(validMoves.size());
	            Cell randomCell = validMoves.get(randomIndex);

	            int targetX = randomCell.getX();
	            int targetY = randomCell.getY();

	            // Lépés végrehajtása
	            board.movePuppet(leopardPuppet, targetX, targetY);       
	        }
	    }
	}

	private void moveCowPuppet() {
		List<Puppet> cowPuppets = board.getCowPuppets();
		
		if(!cowPuppets.isEmpty()) {
			Random rand = new Random();
			Puppet randomCow = cowPuppets.get(rand.nextInt(cowPuppets.size()));
			List<Cell> validMoves = board.getCellsForValidMoves(randomCow);
			if (!validMoves.isEmpty()) {
	            int randomIndex = rand.nextInt(validMoves.size());
	            Cell randomCell = validMoves.get(randomIndex);

	            int targetX = randomCell.getX();
	            int targetY = randomCell.getY();

	            board.movePuppet(randomCow, targetX, targetY);
	        }

		}
	}

	private void addBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 4));
		JButton menuButton = new JButton("Menu");
	    JButton restartButton = new JButton("Restart");
	    JButton saveButton = new JButton("Save");
	    JButton exitButton = new JButton("Exit");
	    
	    menuButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	frame.dispose();
	            new Menu(results_list); 
	        }
	    });
	    
	    restartButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	frame.dispose();
	        	if(isMultiPlayer) {
	        		Game game = new Game(results_list);
	        		game.startMultiplayerGame(result.getCowPlayer(), result.getLeopardPlayer());
	        	}else {
	        		if(!isMultiPlayer && (currentPlayer % 2 == 0)) {					
	        			 Game game = new Game(results_list);
	        			 game.startCowSingleplayerGame(result.getCowPlayer());
	        		}else {
	        			 Game game = new Game(results_list);
	        			 game.startLeopardSingleplayerGame(result.getLeopardPlayer());
	        		}
	        	}
	        }
	    });
	    
	    saveButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	SaveGame saveGame = new SaveGame(board.getCowPuppets2(), board.getLeopardPuppet(), result, results_list);
	        	saveGame.saveToFile();
	        }
	    });
	    
	    exitButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	frame.dispose();
	        	System.exit(0);  
	        }
	    });
	    
	    bottomPanel.add(menuButton);
	    bottomPanel.add(restartButton);
	    bottomPanel.add(saveButton);
	    bottomPanel.add(exitButton);
	    
	    frame.add(bottomPanel, BorderLayout.SOUTH);
	    frame.revalidate(); 
	    frame.repaint();
		
	}
	
	//mentett játék folytatása
	public BoardRenderer(List<Puppet> puppets, PlayersAndResult result, List<PlayersAndResult> results) {
        this.results_list = results;
        this.result = result;
        this.currentPlayer = 0; 
        this.isMultiPlayer = false;

        this.board = new ChessBoard(true); 
        this.board.placePuppets(puppets); 
        
        initializeFrame();
        renderBoard(true);
    }


}
