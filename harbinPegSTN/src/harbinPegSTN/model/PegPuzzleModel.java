package harbinPegSTN.model;

import java.awt.Point;

public class PegPuzzleModel extends Model{

	public static final int CENTER_LOC = 17;
	/*
	 * Initializing board
	 */
	public PegPuzzleModel(){
		reset();
	}
	public boolean checkJump(int id1, int id2){
		
		if(id1<1||id1>33 ||id2<1||id2>33)
			return false;
		return checkJump(pegIDToPoint(id1), pegIDToPoint(id2));
	}
	public boolean checkJump(Point p1, Point p2){
		if(p1==null||p2==null)
			return false;
		return super.checkJump(p1, p2, false);
	}
	/**
	 * do not allow hop in PegPuzzle Game
	 */
	public boolean checkSlide(Point p1,Point p2){
		return false;
	}
	public boolean checkHop(int id1,int id2){
		return false;
	}
	public boolean checkMove(Point fPt,Point sPt){
		Point mid=this.getMiddlePeg(fPt, sPt);
		if(mid==null)
			return false;
		return (isPegAtLocation(fPt)&&isPegAtLocation(mid)&&!isPegAtLocation(sPt));
	}
	public boolean makeMove(Point fPt,Point sPt){
		
		if(!checkMove(fPt,sPt)) 
			return false;
		Point mid=getMiddlePeg(fPt,sPt);
		pegs[fPt.x][fPt.y]=Peg.NONE;
		pegs[mid.x][mid.y]=Peg.NONE;
		pegs[sPt.x][sPt.y]=Peg.NORMAL;
		return true;
	}
	public boolean checkMove(int firstLoc,int secLoc){
		if(firstLoc<1||firstLoc>33)
			return false;
		if(secLoc<1||secLoc>33)
			return false;
		Point fPt=pegIDToPoint(firstLoc);
		Point sPt=pegIDToPoint(secLoc);
		return checkMove(fPt,sPt);
		
	}
	
	public boolean makeMove(int firstLoc,int secLoc){
		if(firstLoc<1||firstLoc>33)
			return false;
		if(secLoc<1||secLoc>33)
			return false;
		
		Point fPt=pegIDToPoint(firstLoc);
		Point sPt=pegIDToPoint(secLoc);
		return makeMove(fPt,sPt);
	}
	public void reset() {
		// TODO Auto-generated method stub
		super.reset();
		for(int i=1;i<=33;i++){
			super.setPeg(Peg.NORMAL, i);
		}
		setPeg(Peg.NONE,CENTER_LOC);
	}
}
