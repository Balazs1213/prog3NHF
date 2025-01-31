package Board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Puppets.*;

public class ChessBoard {
	private Cell[][] cells;
	private List<Puppet> puppets;
	
	public ChessBoard() {
		this.cells = new Cell[8][8];
		this.puppets = new ArrayList<Puppet>();
		initalizeBoard();
	}
	
	private void initalizeBoard() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				boolean isDark = (i + j) % 2 == 0;
				this.cells[i][j] = new Cell(i,j, isDark ? Color.LIGHT_GRAY : Color.DARK_GRAY);
			}
		}
		
		placeInitilaPuppets();
	}
	
	private void placeInitilaPuppets() {
		List<Cell> cowStartingCells = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if ((0 + i) % 2 == 0) { 
                cowStartingCells.add(cells[0][i]);
            }
        }
        
        Puppet cow1 = new CowPuppet(0, 1, 1); 
        Puppet cow2 = new CowPuppet(0, 3, 1); 
        Puppet cow3 = new CowPuppet(0, 5, 1); 
        Puppet cow4 = new CowPuppet(0, 7, 1); 
        
        this.puppets.add(cow1);
        this.puppets.add(cow2);
        this.puppets.add(cow3);
        this.puppets.add(cow4);
        
        cells[cow1.getX()][cow1.getY()].setPuppet(cow1);
        cells[cow2.getX()][cow2.getY()].setPuppet(cow2);
        cells[cow3.getX()][cow3.getY()].setPuppet(cow3);
        cells[cow4.getX()][cow4.getY()].setPuppet(cow4);
        
            List<Cell> emptyBlackCells = getEmptyBlackSquares();
            Random rand = new Random();
            int randomIndex = rand.nextInt(emptyBlackCells.size());
            Cell randomCell = emptyBlackCells.get(randomIndex);

            Puppet leopard = new LeopardPuppet(randomCell.getX(), randomCell.getY(), 2);
            this.puppets.add(leopard);
            randomCell.setPuppet(leopard);

        
	}
	
	//Üres fekete mezők 
	private List<Cell> getEmptyBlackSquares(){
		List<Cell> emptyBlackSquares = new ArrayList<Cell>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if((i + j) % 2 != 0 && cells[i][j].getOccupiedBy() == null) {
					emptyBlackSquares.add(cells[i][j]);
				}
			}
		}
		return emptyBlackSquares;
	}
	
	public boolean movePuppet(Puppet puppet, int targetX, int targetY) {
		if (isMoveValid(puppet, targetX, targetY)) {
            // Töröljük az aktuális pozícióról
            cells[puppet.getX()][puppet.getY()].setPuppet(null);
            cells[puppet.getX()][puppet.getY()].getButton().setText("");

            // Áthelyezzük a bábút
            puppet.setX(targetX);
            puppet.setY(targetY);

            // Beállítjuk az új pozícióra
            cells[targetX][targetY].setPuppet(puppet);
            return true;
        }
        return false;
	}
	
	public List<Cell> getCellsForValidMoves(Puppet puppet){
		List<Cell> validMoves = new ArrayList<Cell>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(isMoveValid(puppet, i, j)) {
					validMoves.add(cells[i][j]);
				}
			}
		}
		return validMoves;
	}

	public boolean isMoveValid(Puppet puppet, int targetX, int targetY) {
		if (targetX < 0 || targetX >= 8 || targetY < 0 || targetY >= 8) {
            return false;
        }
		
		if (!cells[targetX][targetY].isDark()) {
            return false;
        }
		
		if (cells[targetX][targetY].isOccupied()) {
	        return false;
	    }
		
		if (puppet instanceof CowPuppet) {
            // Tehén átlósan 1 mezőt léphet
            return isDiagonalMoveValid(puppet, targetX, targetY, 1);
        } else if (puppet instanceof LeopardPuppet) {
            // Leopárd átlósan 1 vagy 2 mezőt léphet
            return isDiagonalMoveValid(puppet, targetX, targetY, 2);
        }

        return false;
	}
	
	
	//Átlós mozgás és lépéshossz beállítása
	private boolean isDiagonalMoveValid(Puppet puppet, int targetX, int targetY, int maxSteps) {
		int dx = Math.abs(puppet.getX() - targetX);
		int dy = Math.abs(puppet.getY() - targetY);
		
		if(dx != dy) {
			return false;
		}
		
		if(dx > maxSteps) {
			return false;
		}
		
		//Bábu átugrás
		if(dx == 2) {
			int midX = (puppet.getX() + targetX) / 2;
			int midY = (puppet.getY() + targetY) / 2;
			
			if(cells[midX][midY].isOccupied()) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isLeopardOnTopRow() {
		for(int i = 0; i < 8; i++) {
			Cell cell = cells[0][i];
			if(cell.isDark() && cell.getOccupiedBy() instanceof LeopardPuppet) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isLeopardOutOfMoves() {
		for(Puppet puppet : puppets) {
			if(puppet instanceof LeopardPuppet) {
				if(!getCellsForValidMoves(puppet).isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
	
	//getterek
	public Cell[][] getCells(){
		return cells;
	}
	
	public List<Puppet> getCowPuppets(){
		List<Puppet> cowPuppets = new ArrayList<Puppet>();
		for(Puppet puppet : puppets) {
			if(puppet instanceof CowPuppet) {
				cowPuppets.add(puppet);
			}
		}
		return cowPuppets;
	}
	
	public List<CowPuppet> getCowPuppets2(){
		List<CowPuppet> cowPuppets = new ArrayList<CowPuppet>();
		for(Puppet puppet : puppets) {
			if(puppet instanceof CowPuppet) {
				cowPuppets.add((CowPuppet)puppet);
			}
		}
		return cowPuppets;
	}
	
	public LeopardPuppet getLeopardPuppet(){
		for(Puppet puppet : puppets) {
			if(puppet instanceof LeopardPuppet) {
				return (LeopardPuppet) puppet;
			}
		}
		return null;
	}
	
	public List<Puppet> getPuppets(){
		return puppets;
	}
	
	
	///Saved Gamed importálásához
	//üres tábla létrehozása
	public ChessBoard(boolean empty) {
		this.cells = new Cell[8][8];
		this.puppets = new ArrayList<>();
		initalizeEmptyBoard();
	}

	private void initalizeEmptyBoard() {
		for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            boolean isDark = (i + j) % 2 == 0;
	            this.cells[i][j] = new Cell(i, j, isDark ? Color.LIGHT_GRAY : Color.DARK_GRAY);
	        }
	    }
	}
	
	public void placePuppets(List<Puppet> puppetsToPlace) {
		for(Puppet puppet : puppetsToPlace) {
			int x = puppet.getX();
			int y = puppet.getY();
			
			if(!cells[x][y].isOccupied()) {
				cells[x][y].setPuppet(puppet);
				puppets.add(puppet);
			} else {
				throw new IllegalArgumentException("Cell at (" + x + ", " + y + ") is already occupied!");
			}
		}
	}

}
