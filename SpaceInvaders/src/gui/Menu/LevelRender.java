package gui.Menu;

import java.awt.Component;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import game.AlienCrab;
import game.AlienOctopus;

public class LevelRender implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel label = new JLabel();

		if (value != null) {
			Image imgLabel = null;
			if (value.equals(AlienCrab.class.getSimpleName())) {
				try {
					imgLabel = ImageIO.read(getClass().getResourceAsStream("/images/crab1.png"))
							.getScaledInstance(11 * 3, 8 * 3, Image.SCALE_DEFAULT);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (value.equals(AlienOctopus.class.getSimpleName())) {
				try {
					imgLabel = ImageIO.read(getClass().getResourceAsStream("/images/octopus1.png"))
							.getScaledInstance(12 * 3, 8 * 3, Image.SCALE_DEFAULT);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					imgLabel = ImageIO.read(getClass().getResourceAsStream("/images/squid1.png"))
							.getScaledInstance(8 * 3, 8 * 3, Image.SCALE_DEFAULT);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			label = new JLabel(new ImageIcon(imgLabel));

		}

		return label;

	}

}
