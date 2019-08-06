package com.github.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Game extends JFrame {
    final int WINDOW_WIDTH = 800;
    final int WINDOW_HEIGHT = 680;
    
	int cellSize;
	int horizontalPadding;
	int verticalPadding;
	int boardHeight;
	int boardWidth;
	
	int xCells;
	int yCells;
    char difficultyLevel = 'B'; 
    
	public Game() {
		Board mineBoard = new Board(difficultyLevel);
		cellSize = mineBoard.getCellSize();
		calculatePadding();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        setTitle("Minesweeper");
        
      //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu difficultyMenu = new JMenu("Difficulty");   
        
        JMenuItem exitMi = new JMenuItem("Exit");
        JMenuItem restartMi = new JMenuItem("Restart");

        JMenuItem beginnerMi = new JMenuItem("\u2714" + "Beginner");
        JMenuItem intermediateMi = new JMenuItem("Intermediate");
        JMenuItem advancedMi = new JMenuItem("Advanced");

        difficultyMenu.add(beginnerMi);
        difficultyMenu.add(intermediateMi);
        difficultyMenu.add(advancedMi);
        gameMenu.add(restartMi);
        gameMenu.add(difficultyMenu);
        gameMenu.add(exitMi);
        
        menuBar.add(gameMenu);
        
        setJMenuBar(menuBar);
        
        ActionListener difficultyChanged = new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if (e.getSource() == beginnerMi) {
        			System.out.println("Beginner");
        			beginnerMi.setText("\u2714" + "Beginner");
        			intermediateMi.setText("Intermediate");       			
        			advancedMi.setText("Advanced");
        			difficultyLevel='B';
        		} else if (e.getSource() == intermediateMi) {
        			System.out.println("Intermediate");
        			beginnerMi.setText("Beginner");
        			intermediateMi.setText("\u2714" + "Intermediate");
        			advancedMi.setText("Advanced");
        			difficultyLevel='I';
        		} else if (e.getSource() == advancedMi) {
        			System.out.println("Advanced");
        			beginnerMi.setText("Beginner");
        			intermediateMi.setText("Intermediate");
        			advancedMi.setText("\u2714" + "Advanced");
        			difficultyLevel='A';
        		}
        		//System.out.println("Action performed");
        		
        	}
        };
        
        ActionListener restartGame = new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("Restart game");        		
        	}
        };        

        ActionListener exitGame = new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);        		
        	}
        };        
        
        restartMi.addActionListener(restartGame);
        
        beginnerMi.addActionListener(difficultyChanged);
        intermediateMi.addActionListener(difficultyChanged);
        advancedMi.addActionListener(difficultyChanged);
        exitMi.addActionListener(exitGame);
        
        // The actual minesweeper board gets positioned inside 4 JPanels.
        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(WINDOW_WIDTH,verticalPadding));
        northPanel.setBackground(Color.LIGHT_GRAY);
        
        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(horizontalPadding, boardHeight));
        westPanel.setBackground(Color.LIGHT_GRAY);
                
        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(horizontalPadding, boardHeight));
        eastPanel.setBackground(Color.LIGHT_GRAY);
        
        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(WINDOW_WIDTH,verticalPadding));
        southPanel.setBackground(Color.LIGHT_GRAY);
        
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(westPanel, BorderLayout.WEST);
        getContentPane().add(eastPanel, BorderLayout.EAST);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        getContentPane().add(mineBoard, BorderLayout.CENTER);
             
        //add(new Board());
        setResizable(false);
        pack();
	}
	
	private void calculatePadding() {
		if (difficultyLevel == 'B') {
			xCells = 9;
			yCells = 9;
		} else if (difficultyLevel == 'I') {
			xCells = 16;
			yCells = 16;			
		} else if (difficultyLevel == 'A') {
			xCells = 30;
			yCells = 16;			
		}
		
		boardHeight = cellSize * yCells;
		boardWidth = cellSize * xCells;
		horizontalPadding = (WINDOW_WIDTH - (cellSize * xCells))/2;
		verticalPadding = (WINDOW_HEIGHT - (cellSize * yCells))/2;
		//System.out.println("BoardHeight: " + boardHeight);
		//System.out.println("BoardWidth: " + boardWidth);
		//System.out.println("HorizontalPadding: " + horizontalPadding);
		//System.out.println("VerticalPadding: " + verticalPadding);
	}

    public static void main(String args[]) {
    	EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			Game ex = new Game();
    			ex.setVisible(true);
    		}
    	});
    }
}
