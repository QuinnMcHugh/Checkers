import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quinn on 1/1/16.
 */

public class MiniMax {
	public static List<Move> getMoves(Game game){
		List<Move> moves = new ArrayList<Move>();
		
		for (int row = 0; row < Board.ROWS; row++){
			for (int col = 0; col < Board.COLUMNS; col++){
				int pieceColor = game.getBoard().matrix[row][col];
				if (game.getTurn() == Move.RED){
					if (pieceColor == Board.RED_KING || pieceColor == Board.RED_SOLDIER){
						List<Move> pieceMoves = getMovesForPiece(game, new Point(col, row));
						moves.addAll(pieceMoves);
					}
				}
				else if (game.getTurn() == Move.BLACK){
					if (pieceColor == Board.BLACK_KING || pieceColor == Board.BLACK_SOLDIER){
						List<Move> pieceMoves = getMovesForPiece(game, new Point(col, row));
						moves.addAll(pieceMoves);
					}
				}
			}
		}
		
		return moves;
	}
	
	public static List<Move> getMovesForPiece(Game game, Point piece){
		List<Move> moves = new ArrayList<Move>();
		
		int[] deltaX = {-1, 1, 1, -1};
		int[] deltaY = {-1, -1, 1, 1};
		
		for (int i = 0; i < deltaX.length; i++){
			Move move = new Move();
			move.startPoint = piece;
			move.turnColor = game.getTurn();
			move.jumps = new ArrayList<Point>();
			move.jumps.add(new Point(piece.x + deltaX[i], piece.y + deltaY[i]));
			
			if (game.canMakeMove(move.getCopy())){
				moves.add(move.getCopy());
			}
		}
		
		List<Move> jumps = getJumps(game, piece, new ArrayList<Point>());
		moves.addAll(jumps);
		
		return moves;
	}
	
	public static List<Move> getJumps(Game game, Point start, List<Point> previousJumps){
		List<Move> allJumps = new ArrayList<Move>();
		
		Point[] jumps = {new Point(-2, -2), new Point(2, -2), new Point(2, 2), new Point(-2, 2)};
		for (int i = 0; i < jumps.length; i++){
			Move move = new Move();
			move.turnColor = game.getTurn();
			move.startPoint = start;
			move.jumps = new ArrayList<Point>();
			move.jumps.addAll(previousJumps);
			
			Point lastSpot;
			if (previousJumps.size() == 0){
				lastSpot = start;
			}
			else {
				lastSpot = previousJumps.get(previousJumps.size() - 1);
			}
			
			move.jumps.add(new Point(lastSpot.x + jumps[i].x, lastSpot.y + jumps[i].y));
			
			if (game.canMakeMove(move.getCopy())){
				allJumps.add(move.getCopy());
				
				// Add the latest spot to jump to
				previousJumps.add(new Point(start.x + jumps[i].x, start.y + jumps[i].y));
				List<Move> furtherJumps = getJumps(game, start, previousJumps);
				// remove the latest spot to jump to
				previousJumps.remove(previousJumps.size() - 1);
				
				allJumps.addAll(furtherJumps);
			}
		}
		
		return allJumps;
	}
}
