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
	
	public static int ROWS = 8;
	public static int COLUMNS = 8;
	
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
	
	public String toString(){
		String str = "+-------------------------------+\n";
		
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLUMNS; j++){
				str += "| " + getMoniker(matrix[i][j]) + " ";
			}
			str += "|";
			if (i != ROWS - 1){
				str += "\n|---|---|---|---|---|---|---|---|\n";
			}
		}
		
		str += "\n+-------------------------------+\n";
		
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

