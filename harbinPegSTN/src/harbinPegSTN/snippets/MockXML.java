package harbinPegSTN.snippets;

import harbinPegSTN.model.Model;
import harbinPegSTN.storage.Reader;
import harbinPegSTN.storage.Writer;

public class MockXML implements Reader, Writer {
	
	Model model = null;

	@Override
	public boolean writeModel(Model model) {
		return false;
	}

	@Override
	public boolean readyWrite() {
		return model == null;
	}

	@Override
	public Model readModel() {
		return null;
	}

	@Override
	public boolean readyRead() {
		return false;
	}

}
