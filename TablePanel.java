package machine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TablePanel extends JPanel{
	
	Table table;
	int size = 50;

	public TablePanel() {
		setPreferredSize(new Dimension(8*size, 8*size));
		table = new Table(8, 8);
		table.standardLineUp();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		// horizontal lines
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < table.height; i++) {
			g.drawLine(0, i*size, table.width*size, i*size);
		}
		// vertical lines
		for(int i = 0; i < table.width; i++) {
			g.drawLine(i*size, 0, i*size, table.height*size);
		}
		
		for(int i = 0; i < table.height; i++) {
			for(int j = 0; j < table.width; j++) {
				table.fields[i][j].paint(g, size);
			}
		}
	}
	
	public void addMyMouseListener() {
		TablePanel p = this;
		p.addMouseListener(new MouseAdapter() {
			
			Piece grabbed;
			
		    @Override 
		    public void mousePressed(MouseEvent e) {
		    	if(e.getButton() == MouseEvent.BUTTON1) {
		    		int column = e.getX()/size;
		    		int row = e.getY()/size;
		    		Field chosenField = p.table.fields[row][column];
		    		if(grabbed == null) {
		    			// grab the piece on the chosen field, and determine the opportunities
			    		grabbed = chosenField.piece;
			    		if(grabbed != null) {
				    		grabbed.grabbed = true;
				    		p.table.setOpportunities(grabbed);
			    		} 
		    		} 
			    	// if the user wants to make a valid move
		    		else if(chosenField.steppable == true) {
		    			// we execute the move, 
	    				p.table.placePiece(null, grabbed.field.row, grabbed.field.column);
	    				p.table.placePiece(grabbed, row, column);
	    				// and go back to choosing state
	    				p.table.setChoosingState(grabbed);
	    				//grabbed = null;
	    		    	System.out.println(grabbed);
	    			}
		    		// if the user wants to make an invalid move
		    		else if(chosenField.steppable == false) {
		    			// we go back to choosing state
	    				p.table.setChoosingState(grabbed);
	    				//grabbed = null;
	    			}
		    	}
		    	System.out.println(grabbed);
		    	p.repaint();
		    }
		});
	}
	
	public static void main(String[] args) {
		TablePanel p = new TablePanel();
		p.addMyMouseListener();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(p);
		f.pack();
		f.setVisible(true);
	}
}
