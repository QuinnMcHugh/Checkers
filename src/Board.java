/**
 * Created by quinn on 10/26/15.
 */

public class Board {
	int[][] matrix;
	
	public static final int EMPTY = 0;
	public static final int RED_SOLDIER = 1;
	public static final int BLACK_SOLDIER = 2;
	public static final int RED_KING = 3;
	public static final int BLACK_KING = 4;
	
	public static final int ROWS = 8;
	public static final int COLUMNS = 8;
	
	private String getMoniker(int num){
		if (num == EMPTY){
			return " ";
		}
		else if (num == RED_SOLDIER){
			return "r";
		}
		else if (num == BLACK_SOLDIER){
			return "b";
		}
		else if (num == RED_KING){
			return "R";
		}
		else if (num == BLACK_KING){
			return "B";
		}
		return "ERROR";
	}
	
	public Board(){
		matrix = new int[ROWS][COLUMNS];
		
		// Initializes everything in the board to being empty
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLUMNS; j++){
				matrix[i][j] = EMPTY;
			}
		}
		
		// Uses "fancy math" to compute where every Red and Black piece goes
		for (int i = 0; i < 12; i++){
			matrix[i / 4][((i % 4) * 2) + (((i / 4) + 1) % 2) ] = BLACK_SOLDIER;
		}

		for (int i = 0; i < 12; i++){
			matrix[5 + i / 4][((i % 4) * 2) + ((i / 4) % 2) ] = RED_SOLDIER;
		}
	}
	
	public void setMatrix(int[][] values){
		for (int row = 0; row < ROWS; row++){
			for (int col = 0; col < COLUMNS; col++){
				matrix[row][col] = values[row][col];
			}
		}
	}
	
	public int[][] getMatrixCopy(){
		int[][] copy = new int[ROWS][COLUMNS];
		for (int row = 0; row < ROWS; row++){
			for (int col = 0; col < COLUMNS; col++){
				copy[row][col] = matrix[row][col];
			}
		}
		return copy;
	}
	
	public String[] toStringLines(){
		String[] lines = new String[17];
		
		int index = 0;
		lines[index] = "   +-------------------------------+\n";
		index++;

		for (int i = 0; i < ROWS; i++){
			String str = " " + Integer.toString(i) + " ";
			for (int j = 0; j < COLUMNS; j++){
				str += "| " + getMoniker(matrix[i][j]) + " ";
			}
			str += "|\n";
			
			lines[index] = str;
			index++;
			
			if (i != ROWS - 1){
				str = "   |---|---|---|---|---|---|---|---|\n";
				lines[index] = str;
				index++;
			}
		}
		
		lines[16] = "   +-------------------------------+\n";

		return lines;
	}
	
	public String toString(){
		String str = "";
		
		String[] lines = toStringLines();
		for (int i = 0; i < lines.length; i++){
			str += lines[i];
		}
		
		return str;
	}
}


//		+-------------------------------+
//		|   | B |   | B |   | B |   | B |
//		|---|---|---|---|---|---|---|---|
//		| B |   | B |   | B |   | B |   |
//		|---|---|---|---|---|---|---|---|
//		|   | B |   | B |   | B |   | B |
//		|---|---|---|---|---|---|---|---|
//		|   |   |   |   |   |   |   |   |
//		|---|---|---|---|---|---|---|---|
//		|   |   |   |   |   |   |   |   |
//		|---|---|---|---|---|---|---|---|
//		| R |   | R |   | R |   | R |   |
//		|---|---|---|---|---|---|---|---|
//		|   | R |   | R |   | R |   | R |
//		|---|---|---|---|---|---|---|---|
//		| R |   | R |   | R |   | R |   |
//		+-------------------------------+

