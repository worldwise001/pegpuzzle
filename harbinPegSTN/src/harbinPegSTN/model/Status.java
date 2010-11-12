package harbinPegSTN.model;

public enum Status { WHITE_PLACE, WHITE_CLICK, WHITE_JUMP, 
	WHITE_SLIDE, WHITE_REMOVED, WHITE_MOVE,
	BLACK_MOVE, BLACK_CLICK, BLACK_SLIDE,
	PEG_JUMP, INVALID ,WHITE_PLACE_1ST, WHITE_PLACE_2ND, 
	WINNER_WHITE, WINNER_BLACK, DRAW, PENALTY_REQUIRED, 
	BLACK_MOVE_OR_WHITE_MULTIJUMP, 
	STN_START,
	NONE;



public static String toString(Status st){

	switch (st) {
	case WHITE_PLACE: { return "White peg placed"; }
	case WHITE_CLICK: { return "White click"; }
	case WHITE_JUMP: { return "White jump"; }
	case WHITE_SLIDE: { return "White slide"; }
	case WHITE_REMOVED: { return "White removed"; }
	case WHITE_MOVE: { return "It's white's turn"; }
	case BLACK_MOVE: { return "It's black's turn"; }
	case BLACK_CLICK: { return "Black click"; }
	case BLACK_SLIDE: { return "Black slide"; }
	case PEG_JUMP: { return "Peg jump"; }
	case INVALID: { return "Invalid move/jump"; }
	case WHITE_PLACE_1ST: {return "White place first";}
	case WHITE_PLACE_2ND: {return "White place second";}
	case WINNER_WHITE:{return "White is winner!";}
	case WINNER_BLACK:{return "Black is winner!";}
	case DRAW:{return "Tie up";}
	case PENALTY_REQUIRED:{return "Penalty for white, press continue button!";}
	case BLACK_MOVE_OR_WHITE_MULTIJUMP:{return "Black move/white multijump";}
	case STN_START:{return "Game start! White need to place two pegs!";}
	case NONE: return "";
	
	default: { return "INVALID OPERATION"; }
	}

}


}
