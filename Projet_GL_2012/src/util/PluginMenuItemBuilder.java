package util;

import java.lang.reflect.*;
import java.util.logging.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Classe qui met dans un menu des items liées à des plugins donnés
 * @author Philippe Collet
 * @version 1.0
 */
public class PluginMenuItemBuilder {

  // menu créé par la factory
  private JMenu menu;

  // constructeurs servant a établir les entrées du menu
  private Constructor[] constructors;

  // L'actionListener qui va écouter les entrées du menu
  private ActionListener listener;

  private static Logger logger =
      Logger.getLogger("main.util.PluginMenuItemBuilder");

  /**
   * Construit une instance qui concerne un certain menu. Ce menu aura
   * des choix qui permettront de sélectionner un plugin ou un autre.
   * @param cs le tableau des constructeurs utilisables
   * @param listener l'actionListener qui va écouter les entrées du menu.
   */
  public PluginMenuItemBuilder(Constructor[] cs,
                               ActionListener listener) {
    menu = new JMenu();
    this.constructors = cs;
    this.listener = listener;
  }

  public void setMenuTitle(String title) {
    menu.setText(title);
  }

  /**
   * Construit le menu des plugins.
   * @return le menu construit avec les plugins.
   */
  public void buildMenu() {
     logger.info("Construction du menu des PLUGINS");
    // Enlève les entrées précédentes s'il y en avait
    menu.removeAll();
    // On ajoute une entrée par instance de plugin
    for (int i = 0; i < constructors.length; i++) {
      JMenuItem mi = new JMenuItem(constructors[i].getDeclaringClass().getName());
      // ActionCommand contient la position du constructeur dans le tableau
      mi.setActionCommand(""+i);
      mi.addActionListener(listener);
      menu.add(mi);
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

}
