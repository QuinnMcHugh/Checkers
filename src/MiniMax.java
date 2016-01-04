import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quinn on 1/1/16.
 */

public class MiniMax {
	private static Move computedBestMove;
	
	public static Move getBestMove(Game game, char player, int depth, int maxDepth){
		computedBestMove = null;
		MiniMax(game, player, depth, maxDepth);
		return computedBestMove;
	}
	
	public static int MiniMax(Game game, char player, int depth, int maxDepth){
		if (depth == maxDepth || game.isGameOver()){
			return evaluate(game, player);
		}
		
		Move bestMove = null;
		int bestScore;
		if (game.getTurn() == player){
			bestScore = Integer.MIN_VALUE;
		}
		else {
			bestScore = Integer.MAX_VALUE;
		}
		
		List<Move> allMoves = getMoves(game, game.getTurn());
		for (Move move : allMoves){
			int[][] matrixCopy = game.getBoard().getMatrixCopy();
			
			game.processMove(move.getCopy());
			game.changeTurn();
			int moveScore = MiniMax(game, player, depth + 1, maxDepth);
			
			game.changeTurn();
			game.getBoard().setMatrix(matrixCopy);
			
			if (game.getTurn() == player){
				if (moveScore > bestScore){
					bestScore = moveScore;
					bestMove = move.getCopy();
				}
			}
			else {
				if (moveScore < bestScore){
					bestScore = moveScore;
					bestMove = move.getCopy();
				}
			}
		}
		
		if (depth == 0){
			computedBestMove = bestMove;
		}
		
		return bestScore;
	}
	
	public static int evaluate(Game game, char player){
		if (game.isGameOver()){
			for (int i = 0; i < Board.ROWS; i++){
				for (int j = 0; j < Board.COLUMNS; j++){
					if (game.getBoard().matrix[i][j] != Board.EMPTY){
						int piece = game.getBoard().matrix[i][j];
						if (piece == Board.BLACK_SOLDIER || piece == Board.BLACK_KING){
							if (player == Move.BLACK){
								return 100;
							}
							else {
								return -100;
							}
						}
						else if (piece == Board.RED_SOLDIER || piece == Board.RED_KING){
							if (player == Move.RED){
								return 100;
							}
							else {
								return -100;
							}
						}
					}
				}
			}
		}
		else {
			int numBlackKings = 0, numRedKings = 0;
			int numBlackPieces = 0, numRedPieces = 0;
			
			for (int i = 0; i < Board.ROWS; i++){
				for (int j = 0; j < Board.COLUMNS; j++){
					int piece = game.getBoard().matrix[i][j];
					if (piece != Board.EMPTY){
						if (piece == Board.BLACK_KING){
							numBlackKings++;
							numBlackPieces++;
						}
						else if (piece == Board.RED_KING){
							numRedKings++;
							numRedPieces++;
						}
						else if (piece == Board.BLACK_SOLDIER){
							numBlackPieces++;
						}
						else if (piece == Board.RED_SOLDIER){
							numRedPieces++;
						}
					}
				}
			}
			
			int score = 0;
			score += 6 * (numBlackPieces - numRedPieces);
			score += (28f / 12f) * (numBlackKings - numRedKings);
			
			if (player == Move.RED){
				score *= -1;
			}
			
			return score;
		}
		return 0;
	}
	
	public static List<Move> getMoves(Game game, char player){
		List<Move> moves = new ArrayList<Move>();
		
		for (int row = 0; row < Board.ROWS; row++){
			for (int col = 0; col < Board.COLUMNS; col++){
				int pieceColor = game.getBoard().matrix[row][col];
				if (player == Move.RED){
					if (pieceColor == Board.RED_KING || pieceColor == Board.RED_SOLDIER){
						List<Move> pieceMoves = getMovesForPiece(game, new Point(col, row));
						moves.addAll(pieceMoves);
					}
				}
				else if (player == Move.BLACK){
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
