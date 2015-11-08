/**
 * Created by quinn on 10/26/15.
 */

public class Board {
	int[][] matrix;
	
	public static final int EMPTY = 0;
	public static final int RED = 1;
	public static final int BLACK = 2;
	
	public static int ROWS = 8;
	public static int COLUMNS = 8;
	
	public Board(){
		matrix = new int[ROWS][COLUMNS];
		
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLUMNS; j++){
				matrix[i][j] = 0;
			}
		}
		
		for (int i = 0; i < 12; i++){
			matrix[i / 4][((i % 4) * 2) + (((i / 4) + 1) % 2) ] = BLACK;
		}
		
		
	}
	
	public String toString(){
		String str = "";
		
		String endings = "+-------------------------------+\n";
		str += endings;
		
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLUMNS; j++){
				str += "| " + matrix[i][j] + " |";
			}
		}
		
		
		return str;
	}
	
	public static void main(String[] args){
		
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

