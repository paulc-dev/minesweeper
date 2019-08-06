package com.github.minesweeper;
import java.awt.Point;
import java.util.Random;
import java.util.Stack;

public class Minesweeper {
	boolean gameOver;
	boolean gameWon;
	int height;
	int width;
	int mines;
	Cell[][] grid;
	
	public Minesweeper () {
		height = 3;
		width = 3;
		mines = 3;
		grid = new Cell[height][width];
		gameOver = false;
		
		for (int i=0; i<height;i++) {
			for (int j=0;j<width;j++) {
				grid[i][j] = new Cell();
			}
		}
		
	}
	
	public Minesweeper (int height, int width, int mines) {
		this.height = height;
		this.width = width;
		this.mines = mines;
		grid = new Cell[height][width];
		gameOver = false;
		
		for (int i=0; i<height;i++) {
			for (int j=0;j<width;j++) {
				grid[i][j] = new Cell();
			}
		}
	}
	
	public void populateMines () {
		int minesPlaced = 0;
		Random random = new Random();
		while (minesPlaced < mines) {
			int x = random.nextInt(width); // a number between 0 and mWidth - 1
			int y = random.nextInt(height);
			
			Cell newCell = grid[y][x];
			boolean value = newCell.hasMine();
			if(newCell.hasMine() == false ) {
				grid[y][x].setMine();
				minesPlaced ++;
			}
		}
	}
	
	public void findMines () {
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				if(!grid[i][j].hasMine()) {
		            grid[i][j].setMineCount(minesNear(i,j));
		  }
			}
		}
	}

	public int minesNear(int y, int x) {
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
			return mineCount;
		} else {
			return 0;
		}
	}
	
	void cascadeEmptySquares(int y, int x) { // for a given square, uncover neighboring squares that are also empty
		// First, check for an out of bounds condition
		Point currentSquare = new Point(x,y);
		Stack<Point> cascade = new Stack<>();
		
		if (!grid[y][x].hasMine() && !grid[y][x].isSelected() && grid[y][x].getMineCount() == 0) {
			grid[y][x].setSelected();
			cascade.push(currentSquare);

			while (!cascade.isEmpty()) {
				currentSquare = cascade.pop();
				x = currentSquare.x;
				y = currentSquare.y;
				
				// NW grid[y-1][x-1]				
				if (checkBounds(y-1, x-1)) {
					if (!grid[y-1][x-1].hasMine() && !grid[y-1][x-1].isSelected()) {
						if (grid[y-1][x-1].getMineCount() == 0) {
							Point newPoint = new Point(x-1,y-1);
							cascade.push(newPoint);
						}
						grid[y-1][x-1].setSelected();
					}	
				}	
				
				// N grid[y-1][x]
				if (checkBounds(y-1, x)) {
					if (!grid[y-1][x].hasMine() && !grid[y-1][x].isSelected()) {
						if (grid[y-1][x].getMineCount() == 0) {
							Point newPoint = new Point(x,y-1);
							cascade.push(newPoint);
						}
						grid[y-1][x].setSelected();
					}					
				}
				
				// NE grid[y-1][x+1]				
				if (checkBounds(y-1, x+1)) {
					if (!grid[y-1][x+1].hasMine() && !grid[y-1][x+1].isSelected()) {
						if (grid[y-1][x+1].getMineCount() == 0) {
							Point newPoint = new Point(x+1,y-1);
							cascade.push(newPoint);
						}
						grid[y-1][x+1].setSelected();
					}				
				}
				
				// W grid[y][x-1]
				if (checkBounds(y,x-1)) {
					if (!grid[y][x-1].hasMine() && !grid[y][x-1].isSelected()) {
						if (grid[y][x-1].getMineCount() == 0) {
							Point newPoint = new Point(x-1,y);
							cascade.push(newPoint);
						}
						grid[y][x-1].setSelected();
					}
				}
				
				// E grid[y][x+1]
				if (checkBounds(y,x+1)) {
					if (!grid[y][x+1].hasMine() && !grid[y][x+1].isSelected()) {
						if (grid[y][x+1].getMineCount() == 0) {
							Point newPoint = new Point(x+1,y);
							cascade.push(newPoint);
						}
						grid[y][x+1].setSelected();
					}					
				}
				
				// SW grid[y+1][x-1]				
				if (checkBounds(y+1,x-1)) {
					if (!grid[y+1][x-1].hasMine() && !grid[y+1][x-1].isSelected()) {
						if (grid[y+1][x-1].getMineCount() == 0) {
							Point newPoint = new Point(x-1,y+1);
							cascade.push(newPoint);
						}
						grid[y+1][x-1].setSelected();
					}					
				}
				
				// S grid[y+1][x]				
				if (checkBounds(y+1,x)) {
					if (!grid[y+1][x].hasMine() && !grid[y+1][x].isSelected()) {
						if (grid[y+1][x].getMineCount() == 0) {
							Point newPoint = new Point(x,y+1);
							cascade.push(newPoint);
						}
						grid[y+1][x].setSelected();
					}					
				}
				
				// SE grid[y+1][x+1]				
				if (checkBounds(y+1,x+1)) {
					if (!grid[y+1][x+1].hasMine() && !grid[y+1][x+1].isSelected()) {
						if (grid[y+1][x+1].getMineCount() == 0) {
							Point newPoint = new Point(x+1,y+1);
							cascade.push(newPoint);
						}
						grid[y+1][x+1].setSelected();
					}
				}				
			} 
		} else if (!grid[y][x].hasMine() && !grid[y][x].isSelected() && grid[y][x].getMineCount() > 0) {
			grid[y][x].setSelected();
		}
	}

	public int hasMine(int y, int x) {
		// we need to check also that we're not out of array bounds as that would
		// be an error
		if(y >= 0 && y < height && x >= 0 && x < width && grid[y][x].hasMine()) {
		    return 1;
		} else {
			return 0;
		}
	}
	
	public boolean hasFlag(int y, int x) {
		if (grid[y][x].hasFlag()) {
			return true;
		} else { 
			return false;
		}
	}
	
	public void removeFlag(int y, int x) {
		grid[y][x].setFlagged(false);
	}
	
	public void revealMines(int y, int x) { 
		// Finds adjacent squares to a clicked square that can be revealed
		
		
	}

	public void drawBoard () {
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				int count = grid[i][j].getMineCount();
				boolean mine = grid[i][j].hasMine();
				boolean flag = grid[i][j].hasFlag();
				boolean state = grid[i][j].isSelected();
				
				if (mine) {
					if (flag) {
						System.out.print("| FM|");
					} else {
						System.out.print("| M |");
					}
					
				} else {
				
					if (flag) {
						if (count == 0) {
							System.out.print("| F |");
						} else {
							System.out.print("| F" + count + "|");
						}
					}
				
					if (state==true) {
						if (count == 0) {
							System.out.print("| S |");
						} else {
							System.out.print("| S" + count + "|");
						}					
					} else {
						if (count == 0) {
							System.out.print("| E |");
						} else {
							System.out.print("| " + count + " |");
						}					
					}
				}
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
	
	public Cell[][] getGrid() {
		return this.grid;
	}
	
	public Cell checkSquare(int y, int x) {
		return this.grid[y][x];
	}
	
	public boolean mineSelected(int y, int x) {
		if (grid[y][x].hasMine()) {
			gameOver = true;
			return true;
		} else {
			grid[y][x].setSelected();
			return false;
		}
	}
	
	public boolean checkGameOver() {
		boolean noEmptySquares = true;
		
		if (!gameOver) {
			for (int i=0;i<height;i++) {
				for (int j=0;j<width;j++) {
					if (!grid[i][j].isSelected()) {
						if (grid[i][j].hasFlag()) {
							if(!grid[i][j].hasMine()) {
								noEmptySquares = false;
							}
						} else {
							if(!grid[i][j].hasMine()) {
								noEmptySquares = false;
							}
						}
					}
				}
			}
			if (noEmptySquares) {
				gameWon = true;
			}
			gameOver = noEmptySquares;
		}	
		
		return gameOver;
	}
	
	public boolean gameWon() {
		return gameWon;
	}
	
	
	public boolean selectSquare(int y, int x) {
		if (grid[y][x].hasMine()) {
			return true;
		} else {
			return false;
		}
	}
	public void flagSquare(int y, int x) {
		grid[y][x].setFlagged(true);
	}
	
	private boolean checkBounds(int y, int x) {
		if (y>=0 && x>=00 && y<height && x<width) {
			return true;
		} else {
			return false;
		}
	}
}
