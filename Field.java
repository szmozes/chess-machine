package machine;

import java.awt.Graphics;

public class Field {

	Table table;
	int column;
	int row;
	Piece piece;
	boolean steppable;
	
	public Field(Table table, int row, int column) {
		this.table = table;
		this.row = row;
		this.column = column;
		steppable = false;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
		if(piece != null) {
			piece.field = this;
		}
	}
	
	public void paint(Graphics g, int size) {
		if(piece != null/* && !piece.grabbed*/) {
			piece.paint(g, size);
		}
		if(steppable) {
			g.setColor(new java.awt.Color(192, 192, 192));
			g.drawOval(column*size + size/4, row*size + size/4, size*1/2, size*1/2);
		}
	}
}
