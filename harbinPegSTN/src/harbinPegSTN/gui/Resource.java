package harbinPegSTN.gui;

import javax.swing.ImageIcon;

public class Resource {
	
	private static Resource instance = new Resource();

	public static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = instance.getClass().getResource("../rsrc/"+path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			imgURL = instance.getClass().getResource("/rsrc/"+path);
			if (imgURL != null) {
				return new ImageIcon(imgURL, description);
			} else {
				System.err.println("Couldn't find file: " + path);
				return null;
			}
		}
	}
}
