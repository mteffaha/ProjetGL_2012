package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Logger;
import javax.swing.JMenu;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import bebetes.ChampDeBebetes;
import Panel.BebteControl;
import Panel.configSimu;

public class PluginMenuSimu implements ActionListener, MenuSimu {

	public static ChampDeBebetes champ;
	private String title;
	private JMenu menu;
	private static Logger logger = Logger.getLogger("main.util.PluginMenuSimu");

	public PluginMenuSimu() {
		menu = new JMenu();
		menu.setText(title);
		
	}
	/**
	 *  definit le champ de simulation ou la barre de menu sera affichier 
	 */
	public void setChamp(ChampDeBebetes c){
		champ=c;
	}
	
	public void setTitle(String title){
		menu.setText(title);
	}
	public void BuildMenuBar(String[] subMenu) {
		// Create the menu bar
	     logger.info("Construction du menu statique de simulateur");
	     menu.removeAll();
		JMenuItem menuItem;

		for (int i = 0; i < subMenu.length; i++) {
			if (subMenu[i].equalsIgnoreCase("|")) {
				menu.addSeparator();
			} else {
				// a group of JMenuItems
				menuItem = new JMenuItem(subMenu[i]);
				menuItem.addActionListener(this);
				menu.add(menuItem);
			}
		}
	}

	/**
	 * renvoie le menu construit
	 * 
	 * @return JMenu
	 */
	public JMenu getMenu() {
		return menu;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
		if (action.equals("Demarre")) {
			// /IsLanched=true;
			champ.demarre();
		} else {
			if (action.equals("Stop")) {
				// IsLanched=false;
				 champ.arrete();
			} else {
				if (action.equals("Paramétre")) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {

							UIManager.put("swing.boldMetal", Boolean.FALSE);
							new configSimu(champ);
						}
					});
				} else {
					if (action.equals("Exit")) {
						System.exit(0);
					} else {
						if(action.equals("Nouveau")){
						 champ.arrete();
						 champ.initialiseElements();
						 champ.demarre();
						}else{
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {

									UIManager.put("swing.boldMetal", Boolean.FALSE);
									BebteControl.getInstance().ShowInfosPanel();
								}
							});
							
							
						}
					}
				}
			}
		}

	}

}
