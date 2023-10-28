package gui.customComponents;

import java.awt.Font;
import java.io.InputStream;

public class FontLoader {
	public Font getCustomFont(int size) {
		Font font = null;
		try {
			InputStream is = getClass().getResourceAsStream("/fuentes/PressStart2P-Regular.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			font = font.deriveFont(Font.PLAIN, size);
		} catch (Exception e) {
			font = new Font("Arial", Font.BOLD, size);
		}
		return font;
	}
}
