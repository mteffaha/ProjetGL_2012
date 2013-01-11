package Panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class PanelCustom {

	private JFrame frame;
	private ArrayList<JTextArea> texte;
	private String nom;
	private JTabbedPane tabbedPane ;
	private int numonglet=0;
	

	public void showPanel() {
		//panneau pour rajouter des informations
		JPanel cadre = new JPanel();
		cadre.setLayout(new BorderLayout());
		cadre.add(tabbedPane, BorderLayout.CENTER);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(cadre);
		frame.pack();
		frame.setLocation(400, 300);
		frame.setVisible(true);
	}
	
	public void addnewOnglet(String name){
		JPanel info = new JPanel();
		texte.add(new JTextArea(20, 30)) ;
		JScrollPane scrollPane = new JScrollPane(texte.get(numonglet),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		texte.get(numonglet).setEditable(false);
		info.add(scrollPane);
	
		// premier onglet pour les paramétre generales;
			tabbedPane.addTab(name,null, info, "Parametre general");
			tabbedPane.setMnemonicAt(numonglet++, KeyEvent.VK_1);

	}
	
	public JComponent getOngletat(int position){
		return (JComponent) tabbedPane.getTabComponentAt(position);
	}
	
	public  void setOnglet(int position){
		numonglet=position;
	}
	
	public PanelCustom(String nom) {
		this.nom = nom;
		frame = new JFrame(nom);
		tabbedPane= new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(350,350));
		texte=new ArrayList<JTextArea>();
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextArea getTextArea() {
		return texte.get(numonglet);
	}

	public void addStringTextArea(String string) {
		try {
			getTextArea().getDocument().insertString(getTextArea().getCaretPosition(),
					string, null);
			getTextArea().setCaretPosition(getTextArea().getDocument().getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public  int getNumonglet() {
		return numonglet;
	}
	



}