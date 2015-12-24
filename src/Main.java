import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by quinn on 10/26/15.
 */

public class Main {
	public static void main(String[] args) throws Exception {
		Game game = new Game();
		
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
					game.getBoard().matrix[row][col] = Board.EMPTY;
				}
				else if (piece == 'B'){
					game.getBoard().matrix[row][col] = Board.BLACK_KING;
				}
				else if (piece == 'b'){
					game.getBoard().matrix[row][col] = Board.BLACK_SOLDIER;
				}
				else if (piece == 'R'){
					game.getBoard().matrix[row][col] = Board.RED_KING;
				}
				else if (piece == 'r'){
					game.getBoard().matrix[row][col] = Board.RED_SOLDIER;
				}
				col++;
			}
			row++;
		}
		
		Move move = new Move();
		move.turnColor = Move.BLACK;
		move.startPoint = new Point(2, 1);
		ArrayList<Point> jumps = new ArrayList<Point>();
		jumps.add(new Point(4, 3));
		move.jumps = jumps;
		
		System.out.println(game.processMove(move));
		
		System.out.println(game.getBoard().toString());
		
		GameUI ui = new GameUI();
		ui.game = game;
		ui.printGameState();
	}
}
