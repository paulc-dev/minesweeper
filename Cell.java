package com.github.minesweeper;

public class Cell {
	boolean mine; // Will be true if cell has a mine
	boolean flag; // Will be true if cell is flagged
	boolean selected; // Will be true if cell is selected
	int mineCount; // If cell has a neighboring mine count, this will contain that value
	
	public Cell () {
		mine = false;
		flag = false;
		selected = false;
		mineCount = 0;
	}
	
	public Cell (boolean mine) {
		this.mine = mine;
	}
	
	public void setSelected () {
		selected = true;
	}
	
	public void setMineCount (int mineCount) {
		this.mineCount = mineCount;
	}
	
	public void setFlagged (boolean flag) {
		this.flag = flag;
	}
	
	public void setMine () {
		mine = true;
	}
	
	public boolean hasMine() {
		return mine;
	}
	
	public boolean hasFlag() {
		return flag;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public int getMineCount() {
		return mineCount;
	}
}
