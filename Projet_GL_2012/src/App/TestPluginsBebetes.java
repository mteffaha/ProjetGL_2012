package App;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import util.PluginMenuItemBuilder;
import util.PluginMenuSimu;
import bebetes.ChampDeBebetes;
import bebetes.FabriqueEntites;
import bebetes.FabriquePlugins;

/**
 * Cette classe impl�mente une interface graphique tr�s simple qui permet
 * de tester le principe des Plugins et du chargement dynamique de classes
 *
 * @author  Philippe Collet
 * d'apr�s la classe TestPlugins de Michel Buffa et Richard Grin
 * @version 1.0
 */
public class TestPluginsBebetes extends JFrame {
	
  private FabriquePlugins pluginFactory;

  private PluginMenuItemBuilder bebeteMenuBuilder;
  private PluginMenuItemBuilder champiMenuBuilder;
  private PluginMenuSimu Menu;
  private JMenuBar mb = new JMenuBar();

  private ChampDeBebetes champ;

  private static Logger logger =
      Logger.getLogger("main.TestPluginsBebetes");

  public TestPluginsBebetes() {
    super("Lancement de b�b�tes avec plugins");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Initialisation de la fabrique
	FabriqueEntites.init(FabriquePlugins.class);
	// r�cup�ration de la version sp�cialis�e car on sait ce qu'on fait dans la classe de d�marrage !
	pluginFactory = (FabriquePlugins)FabriqueEntites.getFabriqueEntites();
    // Construction des menus de plugins
    buildPluginMenus();

    // La zone de simulation au centre
    champ = pluginFactory.creeChampDeBebetes(640,480,new Random().nextInt(50)+1);
    getContentPane().add(champ, BorderLayout.CENTER);
    
    Menu.setChamp(champ);//intialise le champ pour la barre de menu

    // Les boutons pour charger des nouveaux plugins et recharger les plugins
    JPanel boutons = new JPanel();
    JButton charger = new JButton("Charger des nouveaux plugins");
    charger.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pluginFactory.loadPlugins();
        buildPluginMenus();
      }
    });
    boutons.add(charger);

    JButton recharger = new JButton("Recharger les plugins");
    recharger.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pluginFactory.reloadPlugins();
        buildPluginMenus();
      }
    });

    boutons.add(recharger);
    
    JButton tuer = new JButton("tuer une bebete");
    tuer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	champ.getListeBebete().get(0).setDead(true);
      }
    });
    
    boutons.add(tuer);

    // un bouton pour relancer la simulation
    JButton restart = new JButton("Red�marrer la simulation");
    restart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        champ.arrete();
        champ.initialiseElements();
        champ.demarre();
      }
    });
    boutons.add(restart);

    getContentPane().add(boutons, BorderLayout.SOUTH);

    pack();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int largeurEcran = screenSize.width;
    int hauteurEcran = screenSize.height;
    int largeurFrame = getSize().width;
    int hauteurFrame = getSize().height;
    int posX = (largeurEcran - largeurFrame) / 2;
    int posY = (hauteurEcran - hauteurFrame) / 2;
    setLocation(posX, posY);

    // d�marrage de la simulation
    champ.demarre();
    setVisible(true);
  }

  /**
   * Construit les entr�es des menus li�es aux plugins.
   */
  private void buildPluginMenus() {
    mb.removeAll();
    // L'actionListener qui va �couter les entr�es du menu des bebetes
    ActionListener listener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Met le plugin s�lectionn� num�ro X dans le menu comme bebete
        // courante. Le num�ro est contenu dans ActionCommand()
        pluginFactory.setBebeteIdx(Integer.parseInt(
            ( (JMenuItem) e.getSource()).getActionCommand()));
        logger.info("set bebete #" + pluginFactory.getBebeteIdx());
      }
    };
    //if (bebeteMenuBuilder == null) {
      bebeteMenuBuilder = new PluginMenuItemBuilder(pluginFactory.getBebeteConstructors(),
          listener);
      bebeteMenuBuilder.setMenuTitle("Bebetes");
    // }
    bebeteMenuBuilder.buildMenu();
    mb.add(bebeteMenuBuilder.getMenu());

    listener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Met le plugin s�lectionn� num�ro X dans le menu comme champi
        // courant. Le num�ro est contenu dans ActionCommand()
        pluginFactory.setChampiIdx(Integer.parseInt(
            ( (JMenuItem) e.getSource()).getActionCommand()));
      }
    };
    //if (champiMenuBuilder == null) {
      champiMenuBuilder = new PluginMenuItemBuilder(pluginFactory.getChampiConstructors(),
          listener);
      champiMenuBuilder.setMenuTitle("Champis");
    //}
    champiMenuBuilder.buildMenu();
    mb.add(champiMenuBuilder.getMenu());
    Menu = new PluginMenuSimu();
    Menu.setTitle("Simulation");
    String sub[]={"Nouveau","|","Demarre","Stop","|","Exit"};
    Menu.BuildMenuBar(sub);
    mb.add(Menu.getMenu());
    
    Menu = new PluginMenuSimu();
    Menu.setTitle("Affichage");
    Menu.setChamp(champ);
    String sub2[]={"Paramétre","|","Panneau de Controle"};
    Menu.BuildMenuBar(sub2);
    mb.add(Menu.getMenu());
    setJMenuBar(mb);
  }

  /**
   * Le programme principal
   */
  public static void main(String[] args) throws MalformedURLException {
    // Enl�ve le suivi par les loggers
    Logger.getLogger("fr.unice.plugin").setLevel(Level.WARNING);
    Logger.getLogger("bebetes").setLevel(Level.WARNING);
    new TestPluginsBebetes();
  }
}
