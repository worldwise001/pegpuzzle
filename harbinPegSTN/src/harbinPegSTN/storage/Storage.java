package harbinPegSTN.storage;

import harbinPegSTN.model.Model;

public interface Storage {
	public void write(Model m);
	public Model read();
}
