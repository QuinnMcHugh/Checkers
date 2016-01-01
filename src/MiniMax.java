import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quinn on 1/1/16.
 */

public class MiniMax {
	private Game game;
	
	public void setGame(Game game){ this.game = game; }
	
	public List<Move> getMoves(){
		List<Move> moves = new ArrayList<Move>();
		
		for (int row = 0; row < Board.ROWS; row++){
			for (int col = 0; col < Board.COLUMNS; col++){
				int pieceColor = game.getBoard().matrix[row][col];
				if (game.getTurn() == Move.RED){
					if (pieceColor == Board.RED_KING || pieceColor == Board.RED_SOLDIER){
						List<Move> pieceMoves = getMovesForPiece(new Point(col, row));
						moves.addAll(pieceMoves);
					}
				}
				else if (game.getTurn() == Move.BLACK){
					if (pieceColor == Board.BLACK_KING || pieceColor == Board.BLACK_SOLDIER){
						List<Move> pieceMoves = getMovesForPiece(new Point(col, row));
						moves.addAll(pieceMoves);
					}
				}
			}
		}
		
		return moves;
	}
	
	public List<Move> getMovesForPiece(Point piece){
		List<Move> moves = new ArrayList<Move>();
		
		
		
		return moves;
	}
}
