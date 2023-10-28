package gui.Menu;

import java.awt.BorderLayout;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import gui.customComponents.LabelPixel;

public class ControlWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	public ControlWindow(Menu menu) {

		setTitle("Controles");
		getContentPane().setBackground(Color.BLACK);
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, lineBorder);
		Border compoundBorder2 = BorderFactory.createCompoundBorder(compoundBorder, emptyBorder);

		LabelPixel lTitle = new LabelPixel("CONTROLES", 30);
		lTitle.setBorder(emptyBorder);
		lTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(lTitle, BorderLayout.NORTH);

		JPanel pCenter = new JPanel();
		pCenter.setBackground(Color.BLACK);
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
		pCenter.setBorder(compoundBorder2);

		String[] controlText = { "Derecha", "Izquierda", "Disparar", "Pausar" };
		String[] controlIcon = { "->", "<-", "space", "Esc" };

		for (int i = 0; i < controlText.length; i++) {
			JPanel pControl = new JPanel();
			pControl.setBackground(Color.BLACK);
			LabelPixel lText = new LabelPixel(controlText[i], 15);
			LabelPixel lIcon = new LabelPixel(controlIcon[i], 15);
			lIcon.setBorder(compoundBorder2);
			pControl.add(lIcon);
			pControl.add(lText);
			pCenter.add(pControl);
		}
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		add(pCenter);
		pack();
		setLocationRelativeTo(menu);
		setVisible(true);
	}
}
