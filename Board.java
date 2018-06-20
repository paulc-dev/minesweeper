package com.github.minesweeper;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel{
	private final int CELL_SIZE = 20;

    private final int BEG_CELLS_HIGH = 9;
    private final int BEG_CELLS_WIDE = 9;
    private final int BEG_NUM_OF_MINES = 10;
    
    private final int INT_CELLS_HIGH = 16;
    private final int INT_CELLS_WIDE = 16;
    private final int INT_NUM_OF_MINES = 40;
    
    private final int ADV_CELLS_HIGH = 16;
    private final int ADV_CELLS_WIDE = 30;
    private final int ADV_NUM_OF_MINES = 99;
	
	JToggleButton[][] boardButtons;
	int boardHeight;
	int boardWidth;
	int numberOfMines;
	
	Minesweeper myBoard;
	ActionListener actionListener;
	ChangeListener changeListener;
	ItemListener itemListener;
	
	ImageIcon selected;
	ImageIcon selected1;
	ImageIcon selected2;
	ImageIcon selected3;
	ImageIcon selected4;
	ImageIcon selected5;
	ImageIcon selected6;
	ImageIcon selected7;
	ImageIcon selected8;
	ImageIcon unselected;
	ImageIcon flagged;
	ImageIcon mine;
	
    Board() {
    	myBoard = new Minesweeper();
        myBoard.populateMines();
        myBoard.findMines();
        //myBoard.drawBoard();
       
        GridLayout layout = new GridLayout(boardHeight,boardWidth);
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        try {
			setImageIcons();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	this.setLayout(layout);
        this.drawBoard();   
    }
    
    Board(char difficultyLevel) {
    	if (difficultyLevel == 'B') {
    		boardHeight = BEG_CELLS_HIGH;
    		boardWidth = BEG_CELLS_WIDE;
    		numberOfMines = BEG_NUM_OF_MINES;
    	} else if (difficultyLevel == 'I') {
    		boardHeight = INT_CELLS_HIGH;
    		boardWidth = INT_CELLS_WIDE;
    		numberOfMines = INT_NUM_OF_MINES;
    	} else if (difficultyLevel == 'A') {
    		boardHeight = ADV_CELLS_HIGH;
    		boardWidth = ADV_CELLS_WIDE;
    		numberOfMines = ADV_NUM_OF_MINES;
    	} else {
    		boardHeight = BEG_CELLS_HIGH;
    		boardWidth = BEG_CELLS_WIDE;
    		numberOfMines = BEG_NUM_OF_MINES;
    	}

    	myBoard = new Minesweeper(boardHeight,boardWidth,numberOfMines);
        myBoard.populateMines();
        myBoard.findMines();
        //myBoard.drawBoard();
       
        GridLayout layout = new GridLayout(boardHeight,boardWidth);
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        
        try {
			setImageIcons();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	this.setLayout(layout);
        this.drawBoard();          
    }
    
    public int getCellSize() {
    	return CELL_SIZE;
    }
    
    private void drawBoard() {
    	boardButtons = new JToggleButton[boardHeight][boardWidth]; // An array of toggle buttons the same size as the board
    	
    	for (int i=0;i<boardHeight;i++) {
    		final int finalI = i;
    		for (int j=0;j<boardWidth;j++) {
    			final int finalJ = j;
    			boardButtons[i][j] = new JToggleButton("");
    			boardButtons[i][j].setPreferredSize(new Dimension(CELL_SIZE,CELL_SIZE));
    			boardButtons[i][j].getModel().setEnabled(true);
				boardButtons[i][j].setIcon(unselected);
    			boardButtons[i][j].getModel().setSelected(false);
    			boardButtons[i][j].addMouseListener(new MouseAdapter() {
    				public void mouseReleased(MouseEvent e) {    					
    					if (SwingUtilities.isLeftMouseButton(e)) {
    						handleSelection(finalI, finalJ);
    					} else if (SwingUtilities.isRightMouseButton(e)) {
    						handleFlag(finalI, finalJ);
    					}    					 
    				}
    			});

    			add(boardButtons[i][j]);    			
    		}
    	}
    }
    
    private void setImageIcons() throws IOException {
    	unselected = new ImageIcon(ImageIO.read(getClass().getResource("images/unselected.PNG")));
    	selected = new ImageIcon(ImageIO.read(getClass().getResource("images/selected.PNG")));
    	flagged = new ImageIcon(ImageIO.read(getClass().getResource("images/flagged.PNG")));
    	selected1 = new ImageIcon(ImageIO.read(getClass().getResource("images/selected1.PNG")));
    	selected2 = new ImageIcon(ImageIO.read(getClass().getResource("images/selected2.PNG")));
    	selected3 = new ImageIcon(ImageIO.read(getClass().getResource("images/selected3.PNG")));
    	selected4 = new ImageIcon(ImageIO.read(getClass().getResource("images/selected4.PNG")));
    	selected5 = new ImageIcon(ImageIO.read(getClass().getResource("images/selected5.PNG")));
    	selected6 = new ImageIcon(ImageIO.read(getClass().getResource("images/selected6.PNG")));
    	selected7 = new ImageIcon(ImageIO.read(getClass().getResource("images/selected7.PNG")));
    	selected8 = new ImageIcon(ImageIO.read(getClass().getResource("images/selected8.PNG")));
    	mine = new ImageIcon(ImageIO.read(getClass().getResource("images/mine.PNG")));
    }
    
    private void handleSelection(int y, int x) {
    	// First test that the square doesn't have a mine.
    	
    	if (myBoard.selectSquare(y, x) == true) {
    		updateBoard();
    	} else {
    		myBoard.cascadeEmptySquares(y, x);
    		updateBoard();
    		//myBoard.drawBoard();
    	}
    }
    
    private void handleFlag(int y, int x) {
    	// Check if square is not flagged yet
    	//if (!myBoard.)
    	if (myBoard.hasFlag(y,x)) {
    		myBoard.removeFlag(y, x);
    	} else {
    		myBoard.flagSquare(y, x);
    	}
    	updateBoard();
    }
    
    private void updateBoard() {
    	boolean gameOver = myBoard.gameOver();
    	if (gameOver) {
    		System.out.println("GAME OVER");
    	}
    	
    	if (!gameOver) { // How to draw the board when the game isn't over
    		for (int i=0;i<boardHeight;i++) {
    			for (int j=0;j<boardHeight;j++) {
    				Cell state = myBoard.checkSquare(i, j);
    				if (state.isSelected()) {
    					int mineCount = state.getMineCount();
    					if (state.getMineCount() == 0) {
    						boardButtons[i][j].setIcon(selected);
    					} else {
    						switch(mineCount) {
        						case 1: boardButtons[i][j].setIcon(selected1);
        							break;
        						case 2: boardButtons[i][j].setIcon(selected2);
        							break;
        						case 3: boardButtons[i][j].setIcon(selected3);
        							break;
        						case 4: boardButtons[i][j].setIcon(selected4);
        							break;
        						case 5: boardButtons[i][j].setIcon(selected5);
        							break;
        						case 6: boardButtons[i][j].setIcon(selected6);
        							break;
        						case 7: boardButtons[i][j].setIcon(selected7);
        							break;
        						case 8: boardButtons[i][j].setIcon(selected8);
        							break;
    						}
    					}
    				} else if (state.hasFlag()) {
    						boardButtons[i][j].setIcon(flagged);

    				} else {
    					boardButtons[i][j].setIcon(unselected);
    				}
    			}//inner for
    		}// outer for
    	} // end if
    	else { // how to draw the board when the game is over
    		for (int i=0;i<boardHeight;i++) {
    			for (int j=0;j<boardHeight;j++) {
    				Cell state = myBoard.checkSquare(i, j);
    				if (state.isSelected()) {
    					boardButtons[i][j].setIcon(selected);
    				} else if (state.hasMine() && state.hasFlag()) {
    					boardButtons[i][j].setIcon(mine);
    				} else if (state.hasMine()) {		
    					boardButtons[i][j].setIcon(mine);
    				} else {
    					boardButtons[i][j].setIcon(unselected);
    				}
    			}//inner for
    		}// outer for
    	} // end else

    }
}
