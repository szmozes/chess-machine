package machine;

import java.util.ArrayList;

public class StandardGameNew {

	TableState table;
	ArrayList<TableState> log;

	
	public StandardGameNew() {
		table = new TableState();
		log = new ArrayList<TableState>();
	}
	
	public void move(int fromRow, int fromColumn, int toRow, int toColumn) {
		
		// first we update the log
		log.add(table);
		// and create this turn's table state
		table = new TableState(table);
		
		// execute the move (this needs to be extended with the special moves)
		table.fields[toRow][toColumn] = table.fields[fromRow][fromColumn];
		table.fields[fromRow][fromColumn] = null;
	}
	
	public ArrayList<int[]> opportunities(int row, int column){
		
		// the opportunities (x-y pairs) are stored here
		ArrayList<int[]> opportunities = new ArrayList<int[]>();
		
		Piece chosen = table.fields[row][column];
		
		if(chosen == null) {
			return opportunities;
		}
		
		switch(chosen.getKind()) {
		case PAWN:
			
			switch(chosen.getColor()) {
			case WHITE:
				
				// make sure not to exceed the table edge
				if(row-1 < 0) {
					return opportunities;
				}
				
				// the fields before it are empty
				boolean freeNorth1 = table.fields[row-1][column] == null;
				boolean freeNorth2 = table.fields[row-2][column] == null;
				
				// check if it can step 1
				if(freeNorth1) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = row-1;
					oppurtunity[1] = column;
					opportunities.add(oppurtunity);
				}
				
				// check if it can step 2
				if(row == 6 && freeNorth1 && freeNorth2 ) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = row-2;
					oppurtunity[1] = column;
					opportunities.add(oppurtunity);
				}
				
				// capture to west
				if(column-1 >= 0) {
					Piece westPiece = table.fields[row-1][column-1];
					if(westPiece != null && westPiece.getColor() != Color.WHITE) {
						int[] oppurtunity = new int[2];
						oppurtunity[0] = row-1;
						oppurtunity[1] = column-1;
						opportunities.add(oppurtunity);
					}
				}
				
				// capture to east
				if(column+1 < 8) {
					Piece eastPiece = table.fields[row-1][column+1];
					if(eastPiece != null && eastPiece.getColor() != Color.WHITE) {
						int[] oppurtunity = new int[2];
						oppurtunity[0] = row-1;
						oppurtunity[1] = column+1;
						opportunities.add(oppurtunity);
					}
				}
				
				break;
				
			case BLACK:
				
				// make sure not to exceed the table edge
				if(row+1 >= 8) {
					return opportunities;
				}
				
				// the fields before it are empty
				boolean freeSouth1 = table.fields[row+1][column] == null;
				boolean freeSouth2 = table.fields[row+2][column] == null;
				
				// check if it can step 1
				if(freeSouth1) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = row+1;
					oppurtunity[1] = column;
					opportunities.add(oppurtunity);
				}
				
				// check if it can step 2
				if(row == 1 && freeSouth1 && freeSouth2) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = row+2;
					oppurtunity[1] = column;
					opportunities.add(oppurtunity);
				}
				
				// capture to west
				if(column-1 >= 0) {
					Piece westPiece = table.fields[row+1][column-1];
					if(westPiece != null && westPiece.getColor() != Color.BLACK) {
						int[] oppurtunity = new int[2];
						oppurtunity[0] = row+1;
						oppurtunity[1] = column-1;
						opportunities.add(oppurtunity);
					}
				}
				
				// capture to east
				if(column+1 < 8) {
					Piece eastPiece = table.fields[row+1][column+1];
					if(eastPiece != null && eastPiece.getColor() != Color.BLACK) {
						int[] oppurtunity = new int[2];
						oppurtunity[0] = row+1;
						oppurtunity[1] = column+1;
						opportunities.add(oppurtunity);
					}
				}
				
				break;
			}
			
			break;
			
		case ROOK:
			
			// to north
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row changes, the column stays
				int oppRow = row - i;
				int oppCol = column;
				
				// make sure not to exceed the edges
				if(oppRow < 0) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			// to south
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row changes, the column stays
				int oppRow = row + i;
				int oppCol = column;

				// make sure not to exceed the edges
				if(oppRow >= 8) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}
			
			// to west
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row stays, the column changes
				int oppRow = row;
				int oppCol = column - i;

				// make sure not to exceed the edges
				if(oppCol < 0) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			// to east
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row stays, the column changes
				int oppRow = row;
				int oppCol = column + i;

				// make sure not to exceed the edges
				if(oppCol >= 8) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}
			
			break;
			
		case KNIGHT: 

			// here we have the relative spots
			int[] rowPosN = {-1, -2, -2, -1, 1, 2, 2, 1};
			int[] colPosN = {-2, -1, 1, 2, 2, 1, -1, -2};
			
			// we go through one-by-one, to see if we can add it to the possible opportunities
			for(int i = 0; i < 8; i++) {
				
				// to get the exact spot, we add the current and the relative coordinates
				int oppRow = row + rowPosN[i];
				int oppCol = column + colPosN[i];
				
				// first, we have to make sure we don't exceed the table's edges
				if(oppRow < 0 || oppRow >= 8 || oppCol < 0 || oppCol >= 8) {
					// in case we exceed, we ignore the opportunity, and go to the next one
					continue;
				}

				// an then, the other conditions, for example, if there is a same colored piece
				if(table.fields[oppRow][oppCol].getColor() != chosen.getColor()) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = oppRow;
					oppurtunity[1] = oppCol;
					opportunities.add(oppurtunity);
				}
			}
			
			break;
			
		case BISHOP: 

			// to north-west
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row decreases, the column decreases
				int oppRow = row - i;
				int oppCol = column - i;
				
				// make sure not to exceed the edges
				if(oppRow < 0 || oppCol < 0) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			// to north-east
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row decreases, the column increases
				int oppRow = row - i;
				int oppCol = column + i;

				// make sure not to exceed the edges
				if(oppRow < 0 || oppCol >= 8) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}
			
			// to south-west
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row increases, the column decreases
				int oppRow = row + i;
				int oppCol = column - i;

				// make sure not to exceed the edges
				if(oppRow >= 8 || oppCol < 0) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			// to south-east
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row increases, the column increases
				int oppRow = row + i;
				int oppCol = column + i;

				// make sure not to exceed the edges
				if(oppRow >= 8 || oppCol >= 8) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}
			
			break;
			
		case QUEEN:
			
			// to north
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row changes, the column stays
				int oppRow = row - i;
				int oppCol = column;
				
				// make sure not to exceed the edges
				if(oppRow < 0) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			// to south
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row changes, the column stays
				int oppRow = row + i;
				int oppCol = column;

				// make sure not to exceed the edges
				if(oppRow >= 8) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}
			
			// to west
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row stays, the column changes
				int oppRow = row;
				int oppCol = column - i;

				// make sure not to exceed the edges
				if(oppCol < 0) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			// to east
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row stays, the column changes
				int oppRow = row;
				int oppCol = column + i;

				// make sure not to exceed the edges
				if(oppCol >= 8) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
				
			}
			
			// to north-west
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row decreases, the column decreases
				int oppRow = row - i;
				int oppCol = column - i;
				
				// make sure not to exceed the edges
				if(oppRow < 0 || oppCol < 0) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			// to north-east
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row decreases, the column increases
				int oppRow = row - i;
				int oppCol = column + i;

				// make sure not to exceed the edges
				if(oppRow < 0 || oppCol >= 8) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}
			
			// to south-west
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row increases, the column decreases
				int oppRow = row + i;
				int oppCol = column - i;

				// make sure not to exceed the edges
				if(oppRow >= 8 || oppCol < 0) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			// to south-east
			for(int i = 1; i < 8; i++) {

				// to get the opportunity's spot, we add the iterator to the initial spot
				// the row increases, the column increases
				int oppRow = row + i;
				int oppCol = column + i;

				// make sure not to exceed the edges
				if(oppRow >= 8 || oppCol >= 8) {
					break;
				}
				
				// get to know what is on the field
				Piece local = table.fields[oppRow][oppCol];
				
				// if it's empty, we add the opportunity, and go further
				if(local.getColor() == null) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					continue;
				}
				
				// if it's not empty, we stop, and add the opportunity if needed
				if(local.getColor() != chosen.getColor()) {
					int[] opportunity = new int[2];
					opportunity[0] = oppRow;
					opportunity[1] = oppCol;
					opportunities.add(opportunity);
					break;
				}
				
				// if it's neither empty nor enemy, then we can't go further
				break;
			}

			break;
			
		case KING: 

			// here we have the relative spots
			int[] rowPosK = {-1, -1, -1, 0, 0, 1, 1, 1};
			int[] colPosK = {-1, 0, 1, -1, 1, -1, 0, 1};
			
			// we go through one-by-one, to see if we can add it to the possible opportunities
			for(int i = 0; i < 8; i++) {
				
				// to get the exact spot, we add the current and the relative coordinates
				int oppRow = row + rowPosK[i];
				int oppCol = column + colPosK[i];
				
				// first, we have to make sure we don't exceed the table's edges
				if(oppRow < 0 || oppRow >= 8 || oppCol < 0 || oppCol >= 8) {
					// in case we exceed, we ignore the opportunity, and go to the next one
					continue;
				}

				// an then, the other conditions, for example, if there is a same colored piece
				if(table.fields[oppRow][oppCol].getColor() != chosen.getColor()) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = oppRow;
					oppurtunity[1] = oppCol;
					opportunities.add(oppurtunity);
				}
			}
			
			break;
		}
		
		return opportunities;
	}
}
