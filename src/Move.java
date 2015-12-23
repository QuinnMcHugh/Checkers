import java.awt.*;
import java.util.ArrayList;

/**
 * Created by quinn on 12/19/15.
 */

public class Move {
	public static char RED = 'r';
	public static char BLACK = 'b';
	
	/* Either 'r' or 'b' for Red or Black turn */
	public char turnColor;
	
	/* Where the piece being moved originated */
	public Point startPoint;
	/* Where the piece is being jumped to during the turn */
	public ArrayList<Point> jumps;
	
}
