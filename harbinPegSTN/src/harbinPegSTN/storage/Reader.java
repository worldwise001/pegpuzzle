package harbinPegSTN.storage;

import harbinPegSTN.model.Model;

public interface Reader {

	public Model readModel();
	public boolean ready();
}
