package harbinPegSTN.storage;

import harbinPegSTN.model.Model;
import harbinPegSTN.model.Peg;
import harbinPegSTN.model.SaveTheNetworkModel;
import harbinPegSTN.model.Status;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class XMLEngine implements Storage {
	
	private Document doc = null;
	
	private void initialize() {
		doc = new Document();
	}

	@Override
	public void write(Model m) {
		if (!(m instanceof SaveTheNetworkModel)) { doc = null; return; }
		SaveTheNetworkModel stnm = (SaveTheNetworkModel)m;
		if (doc == null) initialize();
		
		Element game = new Element("SaveTheNetGame");
		Element state = new Element("CurrentState");
		Element whitePieces = new Element("WhitePieces");
		Element blackPieces = new Element("BlackPieces");
		
		state.setAttribute("GameState", ""+stnm.getStatus());
		game.addContent(state);
		if (stnm.getStatus() == Status.PENALTY_REQUIRED) {
			Element penalty = new Element("Penalty");
			int[] ipenalty = stnm.getPenaltyWhiteLoc();
			penalty.setText(ipenalty[0] + " " + ipenalty[1]);
			state.addContent(penalty);
		}
		
		Peg[] board = stnm.getBoard();
		String white = "";
		String black = "";
		for (int i = 1; i < 34; i++) {
			if (board[i] == Peg.WHITE) white += i+" ";
			if (board[i] == Peg.BLACK) black += i+" ";
		}
		if (white.length() > 0) white = white.substring(0, white.length()-1);
		if (black.length() > 0) black = black.substring(0, black.length()-1);
		
		whitePieces.setText(white);
		blackPieces.setText(black);
		state.addContent(whitePieces);
		state.addContent(blackPieces);
		
		doc.setRootElement(game);
		
	    try {
	      XMLOutputter serializer = new XMLOutputter();
	      File output = new File("testsave.xml");
	      serializer.output(doc, new PrintStream(output));
	    }
	    catch (IOException e) {
	      System.err.println(e);
	    }
	}

	@Override
	public Model read() {
		// TODO Auto-generated method stub
		return null;
	}


}
