package m1.gl.projet2012.bebetes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class configSimu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3392740322372893948L;

	public configSimu() {
		
		JFrame frame = new JFrame("Paramétre");
		JPanel cadre=new JPanel();
		JPanel cadrebut=new JPanel();
		JButton valid=new JButton("Appliquer");
		JButton annul=new JButton("Annuler");
		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = null;	
		// premier onglet pour les paramétre generales
		JComponent gen = PanelConfigGen();
		tabbedPane.addTab("General", icon, gen, "Parametre general");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		// dexieme  onglet pour configuer les m1.gl.projet2012.bebetes hasard
		JComponent hasard = PanelConfigHasard();
		tabbedPane.addTab("Hasard", icon, hasard, "parametre de m1.gl.projet2012.bebetes hasard");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		// troisiéme onglet pour configurer les bebers Emergentes
		JComponent emerg = PanelConfigEmerg();
		tabbedPane.addTab("Emergente", icon, emerg, "parametre de m1.gl.projet2012.bebetes Emergentes");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		// Add the tabbed pane to this panel.
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setPreferredSize(new Dimension(320, 240));

		cadre.setLayout(new BorderLayout());
	    cadre.add(tabbedPane,BorderLayout.NORTH);
	    cadrebut.add(annul);
	    cadrebut.add(valid);
	    cadre.add(cadrebut,BorderLayout.SOUTH);
	    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(cadre);
		frame.pack();
		frame.setLocation(400, 300);
		frame.setVisible(true);

	}
	// creation de panneau de confiquration des m1.gl.projet2012.bebetes  Emergentes
	private JComponent PanelConfigEmerg() {
		JComponent config = new JPanel();
		config.setLayout(new GridLayout(8, 2));
		JLabel dist= new JLabel("distance minimale :");
		dist.setToolTipText("distance minimale entre deux m1.gl.projet2012.bebetes pour changer leurs direction et vitesse");
		JTextField tdist = new JTextField(3);
		config.add(dist);
		config.add(tdist);
		config.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		return config;
	}
	// creation de panneau de confiquration des m1.gl.projet2012.bebetes hasard
	private JComponent PanelConfigHasard() {
		JComponent config = new JPanel();
		config.setLayout(new GridLayout(8, 2));
		JLabel nbTour = new JLabel("nombre de tours : ");
		nbTour.setToolTipText("nombre de tours entre chaque changement au hasard de direction et de vitesse");
		JTextField tTour = new JTextField(3);
		config.add(nbTour);
		config.add(tTour);
		config.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		return config;
		
	}
	// creation de panneau de confiquration générale 
	public JComponent PanelConfigGen() {

		JComponent config = new JPanel();
		JLabel nbBebetes;
		JLabel Vitesse;
		JLabel direction;
		JLabel comp;
		JTextField tnbB;
		JTextField tVit;
		JTextField tdir;
		config.setLayout(new GridLayout(8, 2));
		Border bord = BorderFactory.createRaisedBevelBorder();
		config.setBorder(bord);

		nbBebetes = new JLabel("nombre de m1.gl.projet2012.bebetes : ");
		Vitesse = new JLabel("Vitesse (pixel/seconde) : ");
		direction = new JLabel("Direction (radians) : ");

		config.add(nbBebetes);
		tnbB = new JTextField(3);
		config.add(tnbB);

		config.add(Vitesse);
		tVit = new JTextField(3);
		config.add(tVit);

		config.add(direction);
		tdir = new JTextField(3);
		config.add(tdir);
		
		ButtonGroup bg=new ButtonGroup();
		JRadioButton hasr = new JRadioButton("Hasard", false);
		JRadioButton emer = new JRadioButton("Emergentes",true);
		bg.add(hasr);
		bg.add(emer);
		comp = new JLabel("type des m1.gl.projet2012.bebetes : ");
		config.add(comp);
		config.add(new JLabel());
		config.add(hasr);
		config.add(emer);
		config.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		return config;
	}


}
