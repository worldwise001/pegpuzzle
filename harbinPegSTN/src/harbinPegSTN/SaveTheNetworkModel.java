package harbinPegSTN;

import java.awt.Point;

public class SaveTheNetworkModel extends Model{

	
	public SaveTheNetworkModel(){
		super();
		//Board is NONE by default
		setPegAt(Peg.BLACK,7);
		setPegAt(Peg.BLACK,8);
		setPegAt(Peg.BLACK,12);
		setPegAt(Peg.BLACK,13);
		for(int i=14;i<=33;i++)
			super.setPegAt(Peg.BLACK, i);
			
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
	public boolean makeMove(int firstLoc, int secLoc) {
		if(!checkMove(firstLoc,secLoc))
			return false;
		Point mid=new Point();
		
		Point fPt=pegIDToPoint(firstLoc);
		Point sPt=pegIDToPoint(secLoc);
		switch(pegs[fPt.x][fPt.y]){
		case BLACK:
			pegs[fPt.x][fPt.y]=Peg.BLANK;
			pegs[sPt.x][sPt.y]=Peg.BLACK;
			return true;
			
		case WHITE:
			//white can move either way
			//1: move
			if(Math.abs(fPt.x-sPt.x)<=1&& Math.abs(fPt.y-sPt.y)<=1){
				pegs[fPt.x][fPt.y]=Peg.BLANK;
				pegs[sPt.x][sPt.y]=Peg.WHITE;
				return true;
			}
			//2: jump
			if(Math.abs(fPt.x-sPt.x)<=2 &&Math.abs(fPt.y-sPt.y)<=2){
				//horizontal jump
				if(Math.abs(fPt.x-sPt.x)==2&& fPt.y==sPt.y){
					mid.x=fPt.x>sPt.x?fPt.x-1:fPt.x+1;
					mid.y=fPt.y;
				}
				// diagonal jump
				else if(Math.abs(fPt.x-sPt.x)==2&& Math.abs(fPt.y-sPt.y)==2){
					mid.x=fPt.x>sPt.x?fPt.x-1:fPt.x+1;
					mid.y=fPt.y>sPt.y?fPt.y-1:fPt.y+1;
					
				}
				//vertical jump
				else if(Math.abs(fPt.y-sPt.y)==2&&fPt.x==sPt.x){
					mid.x=fPt.x;
					mid.y=fPt.y>sPt.y?fPt.y-1:fPt.y+1;
				}
				
				pegs[fPt.x][fPt.y]=Peg.BLANK;
				pegs[mid.x][mid.y]=Peg.BLANK;
				pegs[sPt.x][sPt.y]=Peg.WHITE;
				return true;
			}
		
		}
		return false;
	}
	public boolean checkMove(int firstLoc,int secLoc){
		if(firstLoc<1||firstLoc>33)
			return false;
		if(secLoc<1||secLoc>33)
			return false;
		Point mid=new Point();
		
		Point fPt=pegIDToPoint(firstLoc);
		Point sPt=pegIDToPoint(secLoc);
		switch(pegs[fPt.x][fPt.y]){
		case INVALID:
		case NONE:
		case BLANK:
			return false;
		case BLACK:
			//black dots can only move forward and sideways
			if((fPt.x==sPt.x && Math.abs(fPt.y-sPt.y)==1)||(fPt.x==sPt.x+1 && fPt.y==sPt.y)||(fPt.x==sPt.x+1 && Math.abs(fPt.y-sPt.y)==1)){
				return !isPegAtLocation(secLoc);
			}
			return false;
			
		case WHITE:
			//white can move either way
			//1: move
			if(Math.abs(fPt.x-sPt.x)<=1&& Math.abs(fPt.y-sPt.y)<=1){
				return !this.isPegAtLocation(secLoc);
			}
			//2: jump
			if(Math.abs(fPt.x-sPt.x)<=2 &&Math.abs(fPt.y-sPt.y)<=2){
				//horizontal jump
				if(Math.abs(fPt.x-sPt.x)==2&& fPt.y==sPt.y){
					mid.x=fPt.x>sPt.x?fPt.x-1:fPt.x+1;
					mid.y=fPt.y;
				}
				// diagonal jump
				else if(Math.abs(fPt.x-sPt.x)==2&& Math.abs(fPt.y-sPt.y)==2){
					mid.x=fPt.x>sPt.x?fPt.x-1:fPt.x+1;
					mid.y=fPt.y>sPt.y?fPt.y-1:fPt.y+1;
					
				}
				//vertical jump
				else if(Math.abs(fPt.y-sPt.y)==2&&fPt.x==sPt.x){
					mid.x=fPt.x;
					mid.y=fPt.y>sPt.y?fPt.y-1:fPt.y+1;
				}
				else return false;
					
			}
			
			return pegs[mid.x][mid.y]==Peg.BLACK && !isPegAtLocation(secLoc);
			
		
		}
		return false;

	}
}
