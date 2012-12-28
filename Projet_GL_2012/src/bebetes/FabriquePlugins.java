package bebetes;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.unice.plugin.ClassPluginLoader;
import fr.unice.plugin.Plugin;

/**
 * <p>
 * Titre : PluginFactory
 * </p>
 * 
 * <p>
 * Description : R�alisation concr�te d'une EntiteFactory avec chargement des
 * plugins
 * </p>
 * 
 * <p>
 * Copyright : Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Soci�t� :
 * </p>
 * 
 * @author collet
 * @version 1.0
 */
public class FabriquePlugins extends FabriqueEntites {

	protected ClassPluginLoader pluginLoader;
	// Les plugins Bebete sont dans le r�pertoire plugins/repository
	// (chemin relatif au r�pertoire dans lequel java est lanc�).
	private static final String pluginDir = "plugins/repository";

	// liste des constructeurs valides pour chaque type de plugin support� :
	// bebete et champi dans cette version
	protected Constructor[] bebeteConstructors;
	protected Constructor[] champiConstructors;
	// Index des constructeurs "courant" pour chaque type de plugin support�
	protected int bebeteIdx;
	protected int champiIdx;

	private static Logger logger = Logger
			.getLogger("fr.unice.plugin.PluginFactory");

	public FabriquePlugins() {
		try {
			pluginLoader = new ClassPluginLoader(pluginDir);
		} catch (MalformedURLException ex) {

		}
		loadPlugins();
	}

	public void loadPlugins() {
		/*
		 * @TODO CODE A ECRIRE : charger les plugins de bebetes et de champi
		 * rŽcupŽrer les "constructors" associŽs
		 */
		pluginLoader.loadPlugins(Bebete.class);
		pluginLoader.loadPlugins(Champi.class);
		initConstructors();
	}

	/**
	 * reloadPlugins recharge tous les plugins necessaires à l'usine depuis le
	 * repertoire pluginDir
	 */
	public void reloadPlugins() {
		/*
		 * @TODO CODE A ECRIRE : REcharger les plugins de bebetes et de champi
		 * recuperer les "constructors" associes
		 */

		pluginLoader.reloadPlugins(Bebete.class);
		pluginLoader.reloadPlugins(Champi.class);
		initConstructors();
	}

	/**
	 * getAllConstructors renvoie le tableau de tous les constructeurs des
	 * plugins sous-classes de c et dont la signature est params
	 * 
	 * @param c
	 *            Class
	 * @param params
	 *            Class[]
	 * @return Constructor[]
	 */
	protected Constructor[] getAllConstructors(Class<? extends Plugin> c,
			Class... params) {
		ArrayList<Constructor> theConstructors = new ArrayList<Constructor>();
		List<Class<Plugin>> loadedOnes = pluginLoader.getPluginClasses(c);
		for (Class<Plugin> cp : loadedOnes) {
			try {
				Constructor cons = cp.getDeclaredConstructor(params);
				// l'instruction ci-dessous indique au Security Manager que l'on
				// peut
				// acc�der au constructeur, m�me si celui-ci est protected ou
				// private
				// sinon, on pourrait recevoir une IllegalAccessException lors
				// de
				// l'instanciation avec ce constructeur
				cons.setAccessible(true);
				theConstructors.add(cons);
			} catch (SecurityException ex) {
				logger.warning("La classe " + cp.getName()
						+ " interdit la r�cup�ration du constructeur");
			} catch (NoSuchMethodException ex) {
				logger.warning("La classe " + cp.getName()
						+ " n'a pas un constructeur a la bonne signature");
			}
		}
		return (Constructor[]) theConstructors
				.toArray(new Constructor[theConstructors.size()]);
	}

	/**
	 * initConstructors initialise tous les constructeurs valides pour la
	 * factory
	 */
	protected void initConstructors() {
		bebeteConstructors = getAllConstructors(Bebete.class,
				ChampDeBebetes.class, int.class, int.class, float.class,
				float.class, Color.class);
		// Aucun constructeur de b�b�tes, impossible de continuer
		if (bebeteConstructors.length == 0) {
			logger.severe("Aucun constructeur de b�b�tes trouv�");
			System.exit(1);
		}
		// le premier constructeur du tableau est utilis� par d�faut
		setBebeteIdx(0);
		champiConstructors = getAllConstructors(Champi.class, new Class[] {
				ChampDeBebetes.class, int.class, int.class });
		// le chargement du constructeur de champi a �chou� : impossible de
		// continuer
		if (champiConstructors.length == 0) {
			logger.severe("Aucun constructeur de champi trouv�");
			System.exit(1);
		}
		// le premier constructeur du tableau est utilis� par d�faut
		setChampiIdx(0);
	}

	public Constructor[] getBebeteConstructors() {
		return bebeteConstructors;
	}

	public Constructor[] getChampiConstructors() {
		return champiConstructors;
	}

	public void setBebeteIdx(int idx) {
		bebeteIdx = idx;
	}

	public int getBebeteIdx() {
		return bebeteIdx;
	}

	public void setChampiIdx(int idx) {
		champiIdx = idx;
	}

	public int getChampiIdx() {
		return champiIdx;
	}

	/**
	 * Cr�ation d'une b�b�te a partir du constructeur d'index courant bebeteIdx
	 * 
	 * @param c
	 *            ChampDeBebetes
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param dC
	 *            float
	 * @param vC
	 *            float
	 * @param col
	 *            Color
	 * @return BebeteAbstraite
	 */
	public Bebete creeBebete(ChampDeBebetes c, int x, int y, float dC,
			float vC, Color col) {
		Object instance = null;
		try {
			instance = bebeteConstructors[bebeteIdx].newInstance(c, x, y, dC,
					vC, col);
		} catch (Exception ex) {
			logger.severe("Impossibilit� d'instancier une b�b�te de type"
					+ bebeteConstructors[bebeteIdx].getDeclaringClass()
							.getName());
			return null;
		}
		return (Bebete) instance;
	}

	/**
	 * Cr�ation d'un champ (pas de plugin utilis� mais c'est tout � fait
	 * faisable et souhaitable !!!)
	 * 
	 * @param largeur
	 *            int
	 * @param hauteur
	 *            int
	 * @param nb
	 *            int
	 * @return ChampDeBebetes
	 */
	public ChampDeBebetes creeChampDeBebetes(int largeur, int hauteur, int nb) {
		// Le champ n'est pas charg� comme un plugin, on pourrait le faire !
		return new ChampDeBebetesAunChampi(largeur, hauteur, nb);
	}

	/**
	 * Cr�ation d'un champi a partir du constructeur d'index courant champiIdx
	 * 
	 * @param c
	 *            ChampDeBebetes
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @return Champi
	 */
	public Champi creeChampi(ChampDeBebetes c, int x, int y) {
		Object instance = null;
		try {
			instance = champiConstructors[champiIdx].newInstance(c, x, y);
		} catch (Exception ex) {
			logger.severe("Impossibilit� d'instancier un champi de type"
					+ champiConstructors[champiIdx].getDeclaringClass()
							.getName());
			return null;
		}
		return (Champi) instance;
	}

}
