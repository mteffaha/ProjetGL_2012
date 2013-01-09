package Panel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class PanelCustom {

	private JFrame frame;
	private JTextArea bmortes;
	private String nom;

	public void showPanel() {
		frame = new JFrame(nom);
		JPanel cadre = new JPanel();
		cadre.setLayout(new BorderLayout());
		JPanel info = new JPanel();
		bmortes = new JTextArea(20, 30);
		JScrollPane scrollPane = new JScrollPane(bmortes,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		bmortes.setEditable(false);
		info.add(scrollPane);
		cadre.add(info, BorderLayout.CENTER);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(cadre);
		frame.pack();
		frame.setLocation(400, 300);
		frame.setVisible(true);
	}

	PanelCustom(String nom) {
		this.nom = nom;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextArea getTextArea() {
		return bmortes;
	}

	public void addStringTextArea(String string) {
		try {
			bmortes.getDocument().insertString(bmortes.getCaretPosition(),
					string, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}