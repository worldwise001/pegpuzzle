package harbinPegSTN.storage.xml;

import harbinPegSTN.model.Model;
import harbinPegSTN.storage.Reader;
import harbinPegSTN.storage.Writer;

import org.jdom.Document;

public class XMLEngine implements Reader, Writer {
	
	private Document doc = null;
	
	private void initialize() {
		doc = new Document();
	}

	@Override
	public boolean writeModel(Model model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean readyWrite() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Model readModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean readyRead() {
		initialize();
		return false;
	}

}
