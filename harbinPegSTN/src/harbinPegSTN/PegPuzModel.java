package harbinPegSTN;

import java.awt.Point;

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
			else //down
			{
				if (firstClick >= 1 && firstClick <= 3 && (secondClick-firstClick) == 8)
					return DIRECTION_DOWN;
				if (firstClick >= 4 && firstClick <= 6 && (secondClick-firstClick) == 12)
					return DIRECTION_DOWN;
				if (firstClick >= 7 && firstClick <= 13 && (secondClick-firstClick) == 14)
					return DIRECTION_DOWN;
				if (firstClick >= 16 && firstClick <= 18 && (secondClick-firstClick) == 12)
					return DIRECTION_DOWN;
				if (firstClick >= 23 && firstClick <= 25 && (secondClick-firstClick) == 8)
					return DIRECTION_DOWN;
			}
		}
		return DIRECTION_INVALID;
	}

	public Point pegIDToPoint(int i) {
		// TODO Auto-generated method stub
		if (i < 1 || i > 33) return null;
		Point p = new Point();
		if (i >= 1 && i <= 6)
		{
			p.x = i/3;
			p.y = (i%3)+1;
		}
		else if (i >= 7 && i <= 27)
		{
			p.x = (i/7)+1;
			p.y = (i%7);
		}
		else
		{
			p.x = (i/3)-4;
			p.y = (i%3)+1;
		}
		return p;
	}

	public int pointToPegID(int x, int y) {
		if (x < 0 || y < 0 || x > 6 || y > 6) return -1;
		int id = -1;
		if (x <= 1 && (y >= 2 && y <= 4))
			id = (y-1)+(x*3);
		else if (x >= 2 && x <= 4)
			id = (x-1)*7+y;
		else if (x >= 5 && (y >= 2 && y <= 4))
			id = (y-1)+(x+4)*3;
		return id;
	}
	

}
