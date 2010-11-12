package harbinPegSTN.storage;

import harbinPegSTN.model.Model;

public interface Writer {

	public boolean writeModel(Model model);
	public boolean readyWrite();
}
