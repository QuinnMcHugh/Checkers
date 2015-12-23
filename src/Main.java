import java.io.File;
import java.util.Scanner;

/**
 * Created by quinn on 10/26/15.
 */

public class Main {
	public static void main(String[] args) throws Exception {
		Board board = new Board();
		
		Scanner s = new Scanner(new File("src/input.txt"));
		
		int row = 0, col;
		while (s.hasNextLine()){
			String line = s.nextLine();
			if (line.equals("+-------------------------------+")){
				continue;
			}
			if  (line.equals("|---|---|---|---|---|---|---|---|")){
				continue;
			}
			
			col = 0;
			for (int i = 2; i < line.length(); i += 4){
				char piece = line.charAt(i);
				if (piece == ' '){
					board.matrix[row][col] = Board.EMPTY;
				}
				else if (piece == 'B'){
					board.matrix[row][col] = Board.BLACK_KING;
				}
				else if (piece == 'b'){
					board.matrix[row][col] = Board.BLACK_SOLDIER;
				}
				else if (piece == 'R'){
					board.matrix[row][col] = Board.RED_KING;
				}
				else if (piece == 'r'){
					board.matrix[row][col] = Board.RED_SOLDIER;
				}
				col++;
			}
			row++;
		}
		
		Game game = new Game();
		
		System.out.println(board.toString());
	}
}
