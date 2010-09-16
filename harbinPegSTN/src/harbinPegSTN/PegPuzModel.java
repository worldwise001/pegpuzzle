package harbinPegSTN;

public class PegPuzModel {

	public static final int CENTER_LOC = 17;

	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_LEFT = 2;
	public static final int DIRECTION_DOWN = 3;
	public static final int DIRECTION_RIGHT = 4;
	public static final int DIRECTION_INVALID = 0;

	private boolean[] pegs;

	public PegPuzModel() {
		pegs = new boolean[34];
		for (int i = 1; i < pegs.length; i++) {
			pegs[i] = true;
		}
		pegs[CENTER_LOC] = false;
	}

	public int size() {
		// TODO Auto-generated method stub
		return pegs.length;
	}

	public boolean isPegAtLocation(int loc) {
		if (loc < 1 || loc > 33)
			return false;
		return pegs[loc];
	}

	public boolean movePeg(int firstClick, int secondClick) {
		// TODO Auto-generated method stub
		if (secondClick == firstClick + 2) {
			if (pegs[firstClick] && pegs[firstClick + 1]
					&& !pegs[firstClick + 2]) {
				pegs[firstClick+2] = true;
				pegs[firstClick] = false;
				pegs[firstClick+1] = false;
				return true;
			}
		}
		return false;
	}

	public int checkMoveDirection(int firstClick, int secondClick) {
		if (firstClick > secondClick) //up or left 
		{
			if ((firstClick-2) == secondClick) //left
			{
				if (firstClick <= 6 && ((firstClick)%3 != 0))
					return DIRECTION_INVALID;
				else if (firstClick >= 7 && firstClick <= 27 && ( ((firstClick)%7 == 0) || (firstClick-1)%7 == 0))
					return DIRECTION_INVALID;
				else if (firstClick >= 28 && (firstClick)%3 != 0)
					return DIRECTION_INVALID;
				else
					return DIRECTION_LEFT;
			}
			else //up
			{
				if (firstClick >= 9 && firstClick <= 11 && (firstClick-secondClick) == 8)
					return DIRECTION_UP;
				if (firstClick >= 16 && firstClick <= 18 && (firstClick-secondClick) == 12)
					return DIRECTION_UP;
				if (firstClick >= 21 && firstClick <= 27 && (firstClick-secondClick) == 14)
					return DIRECTION_UP;
				if (firstClick >= 28 && firstClick <= 30 && (firstClick-secondClick) == 12)
					return DIRECTION_UP;
				if (firstClick >= 31 && firstClick <= 33 && (firstClick-secondClick) == 8)
					return DIRECTION_UP;
			}
		}
		else if (firstClick < secondClick) //down or right
		{
			if ((firstClick+2) == secondClick) //right
			{
				if (firstClick <= 6 && ((firstClick-1)%3 != 0))
					return DIRECTION_INVALID;
				else if (firstClick >= 7 && firstClick <= 27 && ( ((firstClick-5)%7 == 0) || (firstClick-6)%7 == 0))
					return DIRECTION_INVALID;
				else if (firstClick >= 28 && (firstClick-1)%3 != 0)
					return DIRECTION_INVALID;
				else
					return DIRECTION_RIGHT;
			}
		}
		return DIRECTION_INVALID;
	}
	

}
