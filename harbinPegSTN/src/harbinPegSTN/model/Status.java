package harbinPegSTN.model;

public enum Status { WHITE_PLACE, WHITE_CLICK, WHITE_JUMP, 
	WHITE_SLIDE, WHITE_REMOVED, WHITE_MOVE,
	BLACK_MOVE, BLACK_CLICK, BLACK_SLIDE,
	PEG_JUMP, INVALID ,WHITE_PLACE_1ST, WHITE_PLACE_2ND, 
	WINNER_WHITE, WINNER_BLACK, DRAW, PENALTY_REQUIRED, 
	BALCK_MOVE_OR_WHITE_MULTIJUMP;


	public static int toInt(Status st) {
		switch (st) {
		case WHITE_PLACE: { return 0; }
		case WHITE_CLICK: { return 1; }
		case WHITE_JUMP: { return 2; }
		case WHITE_SLIDE: { return 3; }
		case WHITE_REMOVED: { return 4; }
		case WHITE_MOVE: { return 5; }
		case BLACK_MOVE: { return 6; }
		case BLACK_CLICK: { return 7; }
		case BLACK_SLIDE: { return 8; }
		case PEG_JUMP: { return 9; }
		case INVALID: { return 10; }
		case WHITE_PLACE_1ST: {return 11;}
		case WHITE_PLACE_2ND: {return 12;}
		case WINNER_WHITE:{return 12;}
		case WINNER_BLACK:{return 12;}
		case DRAW:{return 12;}
		case PENALTY_REQUIRED:{return 12;}
		case BALCK_MOVE_OR_WHITE_MULTIJUMP:{return 12;}
		default: { return -1; }
		}
	}


public static String toString(Status st){
/*
 
 {"white peg placed", 
 "white click", 
 "White jump",
 "white slide",
 "white removed",
 "its white's turn"
,"its black's turn",
"black click", 
"black slide", 
"Peg Jump", 
"its invalid move/click"};
	
 
 */
	switch (st) {
	case WHITE_PLACE: { return "white peg placed"; }
	case WHITE_CLICK: { return "white click"; }
	case WHITE_JUMP: { return "White jump"; }
	case WHITE_SLIDE: { return "white slide"; }
	case WHITE_REMOVED: { return "white removed"; }
	case WHITE_MOVE: { return "it's white's turn"; }
	case BLACK_MOVE: { return "it's black's turn"; }
	case BLACK_CLICK: { return "black click"; }
	case BLACK_SLIDE: { return "black slide"; }
	case PEG_JUMP: { return "peg jump"; }
	case INVALID: { return "invalid move/jump"; }
	case WHITE_PLACE_1ST: {return "white place first";}
	case WHITE_PLACE_2ND: {return "white place second";}
	case WINNER_WHITE:{return "White is winner!";}
	case WINNER_BLACK:{return "Black is winner!";}
	case DRAW:{return "Tie up";}
	case PENALTY_REQUIRED:{return "penalty for white";}
	case BALCK_MOVE_OR_WHITE_MULTIJUMP:{return "black move/white multijump";}
	default: { return "INVALID OPERATION"; }
}
	
}


}
