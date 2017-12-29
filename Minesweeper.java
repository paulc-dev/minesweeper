package com.github.minesweeper;
import java.util.Random;

public class Minesweeper {
	int height;
	int width;
	int mines;
	String[][] grid;
	
	public Minesweeper () {
		height = 3;
		width = 3;
		mines = 3;
		grid = new String[height][width];
	}
	
	public Minesweeper (int height, int width, int mines) {
		this.height = height;
		this.width = width;
		this.mines = mines;
		grid = new String[height][width];
	}
	
	public void populateMines () {
		int minesPlaced = 0;
		Random random = new Random();
		while (minesPlaced < mines) {
			int x = random.nextInt(width); // a number between 0 and mWidth - 1
			int y = random.nextInt(height);
			if(grid[y][x] != "M") {
				grid[y][x] = "M";
				minesPlaced ++;
			}
		}
	}
	
	public void findMines () {
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				if(grid[i][j] != "M") {
		            grid[i][j] = minesNear(i, j);
		  }
			}
		}
	}

	public String minesNear(int y, int x) {
		int mineCount = 0;
		// check mines in all directions
		mineCount += hasMine(y - 1, x - 1);  // NW
		mineCount += hasMine(y - 1, x);      // N
		mineCount += hasMine(y - 1, x + 1);  // NE
		mineCount += hasMine(y, x - 1);      // W
		mineCount += hasMine(y, x + 1);      // E
		mineCount += hasMine(y + 1, x - 1);  // SW
		mineCount += hasMine(y + 1, x);      // S
		mineCount += hasMine(y + 1, x + 1);  // SE
		if(mineCount > 0) {
			return Integer.toString(mineCount);
		} else {
			return "E";
		}
	}
	
	void cascadeEmptySquares(int y, int x) { // for a given square, uncover neighboring squares that are also empty
		if ( grid[y][x] == "E") {
			
		} else {
			return;
		}
	}
	
	public int hasMine(int y, int x) {
		// we need to check also that we're not out of array bounds as that would
		// be an error
		if(y >= 0 && y < height && x >= 0 && x < width && grid[y][x] == "M") {
		    return 1;
		} else {
			return 0;
		}
	}
	
	public void revealMines(int y, int x) { 
		// Finds adjacent squares to a clicked square that can be revealed
		
		
	}

	public void drawBoard () {
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				System.out.print("| " + grid[i][j] + " |");
			}
			System.out.println("");
		}
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public String[][] getGrid() {
		return this.grid;
	}
	
	public String checkSquare(int y, int x) {
		return this.grid[y][x];
	}
	
	public boolean mineSelected(int y, int x) {
		if (this.hasMine(y, x) == 1) {
			return true;
		} else {
			grid[y][x] = "S"; // The character 'S' will represent that the square has been selected already
			return false;
		}
	}
	
	public boolean selectSquare(int y, int x) {
		String cellValue = grid[y][x];
		String updateValue = "E";
		
		if (cellValue == "M") {
			// When a square with a mine has been selected, set all squares without a mine to a selected state
			for(int i=0; i<height;i++) {
				for(int j=0;j<width;j++) {
					if (grid[i][j] != "M") {
						grid[i][j] = "S";
					}
				}
			}
			return true;
		} else {
			switch (cellValue) {
				case "E": grid[y][x] = "S";
					break;
				case "1": grid[y][x] = "S1";
					break;
				case "2": grid[y][x] = "S2";
					break;
				case "3": grid[y][x] = "S3";
					break;
				case "4": grid[y][x] = "S4";
					break;
				case "5": grid[y][x] = "S5";
					break;
				case "6": grid[y][x] = "S6";
					break;
				case "7": grid[y][x] = "S7";
					break;
				case "8": grid[y][x] = "S8";
					break;
				default:
					break;
			}
			//grid[y][x] = "S";
			return false;
		}
	}
	public void flagSquare(int y, int x) {
		String cellValue = grid[y][x];
		System.out.println(cellValue);
		if(y >= 0 && y < height && x >= 0 && x < width) {
			if (!grid[y][x].contains("S")) {
				switch (grid[y][x]) {
					case "1": grid[y][x] = "F1";
						break;
					case "2": grid[y][x] = "F2";
						break;
					case "3": grid[y][x] = "F3";
						break;
					case "4": grid[y][x] = "F4";
						break;
					case "5": grid[y][x] = "F5";
						break;
					case "6": grid[y][x] = "F6";
						break;
					case "7": grid[y][x] = "F7";
						break;
					case "8": grid[y][x] = "F8";
						break;
					case "E": grid[y][x] = "FE";
						break;
					case "M": grid[y][x] = "FM";
						break;
					case "FE": grid[y][x] = "E";
						break;
					case "FM": grid[y][x] = "M";
						break;
					case "F1": grid[y][x] = "1";
						break;
					case "F2": grid[y][x] = "2";
						break;
					case "F3": grid[y][x] = "3";
						break;
					case "F4": grid[y][x] = "4";
						break;
					case "F5": grid[y][x] = "5";
						break;
					case "F6": grid[y][x] = "6";
						break;
					case "F7": grid[y][x] = "7";
						break;
					case "F8": grid[y][x] = "8";
						break;
					default:
						break;
				}
			}
		}
	}
}
