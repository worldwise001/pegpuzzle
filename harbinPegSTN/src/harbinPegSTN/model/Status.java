package harbinPegSTN.model;

public enum Status { WHITE_PLACE, WHITE_CLICK, WHITE_JUMP, 
	WHITE_SLIDE, WHITE_REMOVED, WHITE_TURN,
	BLACK_TURN, BLACK_CLICK, BLACK_SLIDE,
	PEG_JUMP, INVALID ;


	public static int toInt(Status st) {
		switch (st) {
		case WHITE_PLACE: { return 0; }
		case WHITE_CLICK: { return 1; }
		case WHITE_JUMP: { return 2; }
		case WHITE_SLIDE: { return 3; }
		case WHITE_REMOVED: { return 4; }
		case WHITE_TURN: { return 5; }
		case BLACK_TURN: { return 6; }
		case BLACK_CLICK: { return 7; }
		case BLACK_SLIDE: { return 8; }
		case PEG_JUMP: { return 9; }
		case INVALID: { return 10; }
		default: { return -1; }
		}
	}


}
