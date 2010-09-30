package harbinPegSTN;

public class PegPuzzleModel extends Model{

	public static final int CENTER_LOC = 17;
	/*
	 * Initializing board
	 */
	public PegPuzzleModel(){
		super();
		for(int i=1;i<=33;i++){
			super.setPegAt(Peg.NORMAL, i);
		}
		setPegAt(Peg.NONE,CENTER_LOC);
	}
}
