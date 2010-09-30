package harbinPegSTN;

import java.awt.Point;

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
	public boolean checkMove(Point fPt,Point sPt){
		int fLoc=this.pointToPegID(fPt.x, fPt.y);
		int sLoc=this.pointToPegID(sPt.x, sPt.y);
		return checkMove(fLoc,sLoc);
	}
	public boolean makeMove(Point fPt,Point sPt){
		int fLoc=this.pointToPegID(fPt.x, fPt.y);
		int sLoc=this.pointToPegID(sPt.x, sPt.y);
		return makeMove(fLoc,sLoc);
	}
	public boolean checkMove(int firstLoc,int secLoc){
		if(firstLoc<1||firstLoc>33)
			return false;
		if(secLoc<1||secLoc>33)
			return false;
		Point mid=new Point();
		int midLoc=0;
		Point fPt=pegIDToPoint(firstLoc);
		Point sPt=pegIDToPoint(secLoc);
		if(Math.abs(fPt.x-sPt.x)==2 && fPt.y==sPt.y)
			{
				mid.y=fPt.y;
				if(fPt.x>sPt.x)
						mid.x=fPt.x-1;
				else
					mid.x=fPt.x+1;
				midLoc=super.pointToPegID(mid.x, mid.y);  
				return (isPegAtLocation(firstLoc)&&isPegAtLocation(midLoc));
			}
		if(Math.abs(fPt.y-sPt.y)==2 && fPt.x==sPt.x)
		{
			mid.x=fPt.x;
			if(fPt.y>sPt.y)
					mid.y=fPt.y-1;
			else
				mid.y=fPt.y+1;
			midLoc=super.pointToPegID(mid.x, mid.y);  
			return (isPegAtLocation(firstLoc)&&isPegAtLocation(midLoc));
		}
		return false;
	}
	
	public boolean makeMove(int firstLoc,int secLoc){
		if(!checkMove(firstLoc,secLoc))
			return false;
		Point mid=new Point();
		
		Point fPt=pegIDToPoint(firstLoc);
		Point sPt=pegIDToPoint(secLoc);
		if(Math.abs(fPt.x-sPt.x)==2 && fPt.y==sPt.y)
			{
				mid.y=fPt.y;
				if(fPt.x>sPt.x)
						mid.x=fPt.x-1;
				else
					mid.x=fPt.x+1;
				
			}
		if(Math.abs(fPt.y-sPt.y)==2 && fPt.x==sPt.x)
		{
			mid.x=fPt.x;
			if(fPt.y>sPt.y)
					mid.y=fPt.y-1;
			else
				mid.y=fPt.y+1; 
			
		}
		pegs[fPt.x][fPt.y]=Peg.BLANK;
		pegs[mid.x][mid.y]=Peg.BLANK;
		pegs[sPt.x][sPt.y]=Peg.NORMAL;
		return true;
	}
}
