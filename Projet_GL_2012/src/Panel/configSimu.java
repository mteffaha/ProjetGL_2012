package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


import javax.swing.*;
import javax.swing.border.Border;


import bebetes.*;

public class configSimu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private FabriquePlugins pluginFactory;
	private ChampDeBebetes champ;

	private JFrame frame;
	private JLabel nbBebetes;
	private JTextField tnbB;
	private int hautc;
	private int largc;
	private JTextField Largeur;
	private JTextField Hauteur;

	public configSimu(ChampDeBebetes c) {

		this.champ = c;
		hautc=champ.getHauteur();
		largc=champ.getLargeur();
		pluginFactory = (FabriquePlugins) FabriqueEntites.getFabriqueEntites();
		frame = new JFrame("Paramétre");
		JPanel cadre = new JPanel();
		JPanel cadrebut = new JPanel();
		JButton valid = new JButton("Appliquer");
		JButton annul = new JButton("Annuler");
		valid.addActionListener(this);
		annul.addActionListener(this);

		JTabbedPane tabbedPane = Onglets();

		cadre.setLayout(new BorderLayout());
		cadre.add(tabbedPane, BorderLayout.NORTH);
		cadrebut.add(annul);
		cadrebut.add(valid);

		cadre.add(cadrebut, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(cadre);
		frame.pack();
		frame.setLocation(400, 300);
		frame.setVisible(true);

	}

	public JTabbedPane Onglets() {

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(300,200));
		ImageIcon icon = null;
		int i = 0;
		// premier onglet pour les paramétre generales
		JComponent gen = PanelConfigGen();
		tabbedPane.addTab("General", icon, gen, "Parametre general");
		tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

		// dexieme onglet pour configuer les bebetes hasard
		JComponent hasard = PanelConfigChamp();
		tabbedPane.addTab("Champ bebete", icon, hasard, "parametre de champ des bebetes");
		tabbedPane.setMnemonicAt(i++, KeyEvent.VK_2);

		return tabbedPane;

	}

	// creation de panneau de confiquration des bebetes Emergentes
	private JComponent PanelConfigChamp() {
		JComponent config = new JPanel();
		config.setLayout(new GridLayout(8, 2));
		JLabel larg = new JLabel("Largeur :");
		Largeur = new JTextField(3);
	
		Largeur.setText(""+largc);
		config.add(larg);
		config.add(Largeur);
		Largeur.addActionListener(this);
		
		JLabel Haut = new JLabel("Hauteur :");
		Hauteur= new JTextField(3);

		config.add(Haut);
		config.add(Hauteur);
		Hauteur.setText(""+hautc);
		Hauteur.addActionListener(this);
		config.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		return config;
	}


	// creation de panneau de confiquration générale
	public JComponent PanelConfigGen() {
		
		JComponent config = new JPanel();
		config.setLayout(new GridLayout(8, 2));
		Border bord = BorderFactory.createRaisedBevelBorder();
		config.setBorder(bord);

		nbBebetes = new JLabel("nombre de bebetes : ");

		config.add(nbBebetes);
		tnbB = new JTextField(3);
		tnbB.setText("" + champ.getNombreDeBebetes());
		config.add(tnbB);

		config.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		tnbB.addActionListener(this);



		return config;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Appliquer")) {
			try {
				// champ nombre des bebetes
				int val = Integer.parseInt(tnbB.getText());
				int haut = Integer.parseInt(Hauteur.getText());
				int larg = Integer.parseInt(Largeur.getText());
				if(val!=champ.getNombreDeBebetes()){
					champ.setNombreDeBebetes(val);
					champ.arrete();
					champ.initialiseElements();
					champ.demarre();
				}
				if((hautc!=haut)||(largc!=larg)){
					hautc=haut;
					largc=larg;
					(((champ.getParent()).getParent()).getParent().getParent()).resize(new Dimension(larg,haut));
					champ.repaint();
				}
				frame.dispose();
			} catch (NumberFormatException e1) {
				JOptionPane
						.showMessageDialog(null, "Veuillez saisir Un nombre");
			}
		} else {
			if (action.equals("Annuler")) {
				frame.dispose();
			}
		}
	}
}