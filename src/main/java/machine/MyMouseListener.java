package machine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {
	
	View panel;
	Game game;
	Piece grabbed;


	public MyMouseListener(View panel, Game game) {
		this.panel = panel;
		this.game = game;
		grabbed = null;
	}
	
    @Override 
    public void mousePressed(MouseEvent e) {
    	if(e.getButton() == MouseEvent.BUTTON1) {
    		
    		// get the clicked field
    		int column = e.getX()/panel.size;
    		int row = e.getY()/panel.size;
    		
    		if ((column<8) && (row<8)) { // clicked on the chess board
	    		Field chosenField = panel.table.fields[row][column];
	    		
	    		// choosing state
	    		if(grabbed == null) {
	    			Color pieceColor = panel.table.getPieceColor(row, column);
	    			if(pieceColor == panel.table.whoTurns) {
	    				grabbed = chosenField.piece;
			    		game.setOpportunities(row, column);
	    			}
	    		} 
	    		
		    	// if the user wants to make a valid move
	    		else if(chosenField.steppable) {
	    			game.userMove(grabbed.field.row, grabbed.field.column, row, column);
	    			// go back to choosing state
	    			panel.table.setChoosingState();
					grabbed = null;
				}
	    		
	    		// if the user wants to make an invalid move
	    		else {
	    			// we go back to choosing state
					panel.table.setChoosingState();
					grabbed = null;
				}
    		}	
    		else { // clicked out of the chess board
				int clickedButton = panel.rightMenu.getButtonID(e.getX(), e.getY());
    			panel.handleMenu.buttonHandler(clickedButton);
    		}
    		
    	}
    	
    	panel.repaint();
    	game.wake();
    }
}
