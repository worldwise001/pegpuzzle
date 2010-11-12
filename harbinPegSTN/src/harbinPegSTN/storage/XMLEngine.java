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
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class XMLEngine { //implements Storage {
	

	public static void write(Model m, File file) {
		if (!(m instanceof SaveTheNetworkModel)) { return; }
		SaveTheNetworkModel stnm = (SaveTheNetworkModel)m;
		Document doc = new Document();
		
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
	      serializer.output(doc, new PrintStream(file));
	    }
	    catch (IOException e) {
	      System.err.println(e);
	    }
	}

	public static Model read(File file) {
		SAXBuilder parser = new SAXBuilder();
		try {
			Document doc = parser.build(file);
			SaveTheNetworkModel model = new SaveTheNetworkModel();
			
			Element game = doc.getRootElement();
			Element state = game.getChild("CurrentState");
			Element whitePieces = state.getChild("WhitePieces");
			Element blackPieces = state.getChild("BlackPieces");
			
			Peg[] board = new Peg[34];
			board[0] = Peg.BLANK;
			for (int i = 1; i < 34; i++) board[i] = Peg.NONE;
			String[] splitWhite = whitePieces.getText().split(" ");
			for (int i = 0; i < splitWhite.length; i++) {
				int id = Integer.parseInt(splitWhite[i]);
				board[id] = Peg.WHITE;
			}
			String[] splitBlack = blackPieces.getText().split(" ");
			for (int i = 0; i < splitBlack.length; i++) {
				int id = Integer.parseInt(splitBlack[i]);
				board[id] = Peg.BLACK;
			}
			
			model.setBoard(board);
			String ss = state.getAttributeValue("GameState");
			Status[] array = Status.values();
			Status status = Status.INVALID;
			for (int i = 0; i < array.length; i++) {
				if (ss.equalsIgnoreCase(""+array[i])) {
					status = array[i];
					break;
				}
			}
			model.setStatus(status);
			if (status == Status.PENALTY_REQUIRED) {
				Element ep = state.getChild("Penalty");
				String[] sp = ep.getText().split(" ");
				int p = Integer.parseInt(sp[0]);
				int n = Integer.parseInt(sp[1]);
				model.setPenaltyWhiteLoc(p, n);
			}
			return model;
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
