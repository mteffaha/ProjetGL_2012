package fr.unice.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Charge des classes de plugins dont les fichiers classe sont plac�s
 * dans des URsL donn�es.
 * Cette classe d�l�gue � des ClassLoader le chargement des classes.
 * <P>
 * On peut parcourir les URL pour charger "� chaud" de nouveaux plugins
 * qui y auraient �t� nouvellement install�s (m�thode loadPlugins).
 * En ce cas, les anciens plugins ne sont pas recharg�s.
 * On peut m�me r�cup�rer de nouvelles versions des plugins avec
 * les m�thodes reloadPlugins.
 * <P>
 * Normalement cette classe est utilis�e par un PluginManager mais pas
 * directement par les clients qui veulent charger des plugins.
 * @author Richard Grin
 *
 * Modifications par Philippe Collet
 * - le chargeur n'instancie pas les classes plugins pour v�rifier leur type,
 *   la v�rification se fait par introspection
 * - le chargeur est utilis� par une PluginFactory
 * - passage en Java 5 (version 1.2)
 * @version 1.2
 */

public class ClassPluginLoader {
  /**
   * Le chargeur de classes qui va charger les plugins.
   */
  private ClassLoader loader;
  /**
   * Le r�pertoire o� les plugins seront recherch�s.
   */
  private String pluginDirectory;

  private static Logger logger =
      Logger.getLogger("fr.unice.plugin.ClassPluginLoader");

  private ArrayList<Class<Plugin>> loadedPluginClasses = new ArrayList<Class<Plugin>>();

  /**
   * Cr�e une instance qui va chercher les plugins dans le r�pertoire dont
   * on passe le nom en param�tre.
   */
  public ClassPluginLoader(String directory) throws MalformedURLException {
    // On v�rifie que l'URL correspond bien � un r�pertoire.
    File dirFile = new File(directory);
    if (dirFile == null || ! dirFile.isDirectory()) {
      logger.warning(directory + " n'est pas un r�pertoire");
      throw new IllegalArgumentException(directory + " n'est pas un r�pertoire");
    }

    // Si c'est un r�pertoire mais que l'URL ne se termine pas par un "/",
    // on ajoute un "/" � la fin (car URL ClassLoader oblige � donner
    // un URL qui se termine par un "/" pour les r�pertoires).
    if (!directory.endsWith("/")) {
      directory += "/";
    }
    this.pluginDirectory = directory;
    // Cr�e le chargeur de classes.
    createNewClassLoader();
  }

  /**
   * Charge les instances des plugins d'un certain type plac�s dans le
   * r�pertoire indiqu� � la cr�ation du PluginLoader. Ces instances
   * sont charg�es en plus de celles qui ont d�j� �t� charg�es.
   * Si on ne veut que les instances des plugins qui vont �tre charg�es
   * dans cette m�thode, et avec les nouvelles versions s'il y en a, il faut
   * utiliser {@link #reloadPlugins(Class)}
   * On peut r�cup�rer ces plugins par la m�thode {@link #getPluginInstances}.
   * Si un plugin a d�j� �t� charg�, il n'est pas
   * recharg�, m�me si une nouvelle version est rencontr�e.
   * @param type type des plugins recherch�s. Si null, charge les plugins
   * de tous les types.
   */
  public void loadPlugins(Class<? extends Plugin> type) {
    // En pr�vision d'un chargement ailleurs que d'un r�pertoire, on fait
    // cette indirection. On pourrait ainsi charger d'un jar.
    loadFromDirectory(type);
  }

  /**
   * Charge les instances de tous les plugins.
   * On peut r�cup�rer ces plugins par la m�thode {@link #getPluginInstances}.
   * Si un plugin a d�j� �t� charg�, il n'est pas
   * recharg�, m�me si une nouvelle version est rencontr�e.
   */
  public void loadPlugins() {
    loadPlugins(null);
  }

  /**
   * Recharge tous les plugins.
   * Charge les nouvelles versions des plugins s'il les rencontre.
   */
  public void reloadPlugins() {
    reloadPlugins(null);
  }

  /**
   * Recharge tous les plugins d'un type donn�.
   * Charge  les nouvelles versions des plugins s'il les rencontre.
   * @param type type des plugins � charger.
   */
  public void reloadPlugins(Class<? extends Plugin> type) {
    // Cr�e un nouveau chargeur pour charger les nouvelles versions.
    try {
      createNewClassLoader();
    }
    catch(MalformedURLException ex) {
      // Ne devrait jamais arriver car si l'URL �tait mal form�e,
      // on n'aurait pu cr�er "this".
      ex.printStackTrace();
    }
    // Et efface tous les plugins du type d�j� charg�s.
    erasePluginClasses(type);
    // Recharge les plugins du type
    loadPlugins(type);
  }

  /**
   * Cr�e un nouveau chargeur. Permettra ensuite de charger de nouvelles
   * versions des plugins.
   */
  private void createNewClassLoader() throws MalformedURLException {
    logger.info("******Cr�ation d'un nouveau chargeur de classes");
    loader = URLClassLoader.newInstance(new URL[] { getURL(pluginDirectory) });
  }

  /**
    * Renvoie les classes de plugins qui ont �t� r�cup�r�es cette fois-ci et
    * les fois d'avant (si on n'a pas effac� les plugins charg�s avant lors de la
    * derni�re recherche de plugins}.
    * @return une nouvelle liste contenant les instances r�cup�r�es.
    */
   public List<Class<Plugin>> getPluginClasses() {
     return getPluginClasses(null);
   }

   public List<Class<Plugin>> getPluginClasses(Class<? extends Plugin> type) {
     ArrayList<Class<Plugin>> loadedPluginsOfThatType = new ArrayList<Class<Plugin>>();
     for (Class<Plugin> plugin : loadedPluginClasses) {
       if (type == null || type.isAssignableFrom(plugin)) {
         loadedPluginsOfThatType.add(plugin);
       }
     }
     return loadedPluginsOfThatType;
  }

  /**
   * Efface tous les classes de plugins d'un certain type d�j� charg�s.
   * @param type type des plugins � effacer. Efface tous les plugins si null.
   */
  private void erasePluginClasses(Class<? extends Plugin> type) {
	if (type == null) {
      loadedPluginClasses.clear();
    }
    else {
      Iterator<Class<Plugin>> iter = loadedPluginClasses.iterator();
      while(iter.hasNext()){
      	Class<Plugin> plugin = iter.next();
       	 if (type.isAssignableFrom(plugin)) {
       		 System.out.println("retrait de " + plugin);
           iter.remove();
       	 }
        }
      }
    }

  /**
   * Charge les plugins d'un certain type plac�s dans un r�pertoire
   * qui n'est pas dans un jar.
   * @param urlBase URL du r�pertoire de base ; la classe du plugin doit
   * se trouver sous ce r�pertoire dans un sous-r�pertoire qui correspond
   * au nom de son paquetage.
   * Exemple d'URL : file:rep/fichier
   * @param type type des plugins. Charge tous les plugins si <code>null</code>.
   * @param cl le chargeur de classes qui va charger le plugin.
   */
  private void loadFromDirectory(Class<? extends Plugin> type) {
    // Pour trouver le nom complet des plugins trouv�s : c'est la partie
    // du chemin qui est en plus du r�pertoire de base donn� au loader.
    // Par exemple, si le chemin de base est rep1/rep2, le plugin
    // de nom machin.truc.P1 sera dans rep1/rep2/machin/truc/P1.class
    logger.info("=+=+=+=+=+ Entr�e dans loadPluginsFromDirectory=+=+=+=+");
    loadFromSubdirectory(new File(pluginDirectory), type, pluginDirectory);
    logger.info("=+=+=+=+=+ Sortie de loadPluginsFromDirectory=+=+=+=+");
  }

  /**
   * Charge les plugins plac�s directement sous un sous-r�pertoire
   * d'un r�pertoire de base. Les 2 r�pertoires ne sont pas dans un jar.
   * @param baseName nom du r�pertoire de base (sert pour avoir le nom
   * du paquetage des plugins trouv�s).
   * @param dir sous-r�pertoire. Le nom du paquetage du plugin devra
   * correspondre � la position relative du sous-r�pertoire par rapport
   * au r�pertoire de base.
   * @param type type de plugins � charger.
   * Charge tous les plugins si <code>null</code>.
   */
  private void loadFromSubdirectory(File dir, Class<? extends Plugin> type,
                                    String baseName) {
    logger.info("Chargement dans le sous-r�pertoire " + dir
                + " avec nom de base " + baseName);
    int baseNameLength = baseName.length();
    // On parcourt toute l'arborescence � la recherche de classes
    // qui pourraient �tre des plugins.
    // Quand on l'a trouv�, on en d�duit son paquetage avec sa position
    // relativement � l'URL de recherche.
    File[] files = dir.listFiles();
    logger.info("Le listing : " + files);
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      if (file.isDirectory()) {
        loadFromSubdirectory(file, type, baseName);
        continue;
      }
      // Ca n'est pas un r�pertoire
      logger.info("Examen du fichier " + file.getPath() + ";" + file.getName());
      String path = file.getPath();
      String qualifiedClassName = getQualifiedName(baseNameLength, path);
      // On obtient une instance de cette classe
      if (qualifiedClassName != null) {
        Class<Plugin> plugin = loadOnePluginClass(qualifiedClassName, type);
        if(plugin != null) {
          logger.info("Classe " + qualifiedClassName + " est bien un plugin !");
          // S'il n'y a pas d�j� un m�me plugin, on ajoute
          // le plugin que l'on vient de cr�er.
          // Comparaison sur le nom car les classes � comparer peuvent
          // avoir �t� charg�es par deux classloaders diff�rents
          boolean pasChargee = true;
          for (Class<Plugin> cp : loadedPluginClasses) {
        	  if (cp.getName().equals(plugin.getName()))
        		  pasChargee = false;
          }
          if (pasChargee)
        	  loadedPluginClasses.add(plugin);
        }
      }
    } // for
  }

  /**
   * Dans le cas o� un chemin correspond � un fichier .class,
   * calcule le nom complet d'une classe � partir du nom d'un r�pertoire
   * de base et du chemin de la classe, les 2 chemins �tant ancr� au m�me
   * r�pertoire racine.
   * Le r�pertoire de base se termine par "/" (voir classe URLClassLoader).
   * Par exemple, a/b/c/ (c'est-�-dire 6 pour baseNameLength)
   * et a/b/c/d/e/F.class donneront d.e.F
   * @param baseNameLength nombre de caract�res du nom du r�pertoire de base.
   * @param classPath chemin de la classe.
   * @return le nom complet de la classe, ou null si le nom ne correspond
   * pas � une classe externe.
   */
  private String getQualifiedName(int baseNameLength, String classPath) {
    logger.info("Calcul du nom qualifi� de " + classPath + " en enlevant "
                + baseNameLength + " caract�res au d�but");
    // Un plugin ne peut �tre une classe interne
    if ((! classPath.endsWith(".class")) || (classPath.indexOf('$') != -1)) {
      return null;
    }
    // C'est bien une classe externe
    classPath = classPath.substring(baseNameLength)
              .replace(File.separatorChar, '.');
    // On enl�ve le .class final pour avoir le nom de la classe
    logger.info("Nom complet de la classe : " + classPath);
    return classPath.substring(0, classPath.lastIndexOf('.'));
  }

  /**
   * Transforme le nom du r�pertoire en URL si le client n'a pas donn�
   * un bon format pour l'URL (pour pouvoir cr�er un URLClassLoader).
   * @param dir nom du r�pertoire.
   * @return l'URL avec le bon format.
   * @throws MalformedURLException lanc� si on ne peut deviner de quel URL il
   * s'agit.
   */
  private static URL getURL(String dir) throws MalformedURLException {
    logger.info("URL non transform�e : " + dir);

  /* On commence par transformer les noms absolus de Windows en URL ;
    * par exemple, transformer C:\rep\machin en file:/C:/rep/machin
   */
    if (dir.indexOf("\\") != -1) {
      // on peut soup�onner un nom Windows !
      // 4 \ pour obtenir \\ pour l'expression r�guli�re !
      dir = dir.replaceAll("\\\\", "/");
    } // Nom Windows

    /* C'est un r�pertoire ; plusieurs cas :
     *   1. S'il y a le protocole "file:", on ne fait rien ; par exemple,
     *      l'utilisateur indique que les plugins sont dans un r�pertoire
     *      avec un nom absolu et, dans ce cas, il doit mettre le protocole
     *      "file:" au d�but ;
     *   2. S'il n'y a pas de protocole "file:", on le rajoute.
     */
    if (! dir.startsWith("file:")) {
      /* On consid�re que c'est le nom d'une ressource ; si le nom est
       * absolu, c'est un nom par rapport au classpath.
       */
      dir = "file:" + dir;
    }

    logger.info("URL transform�e : " + dir);
    return new URL(dir);
  }

  /**
   * Retourne la classe de plugin d'un supertype donn�.
   * @param nomClasse Nom de la classe du plugin
   * @param type type de plugin
   * @return la classe de plugin. Retourne null si probl�me.
   * Par exemple, si le plugin n'a pas le bon type.
   */
  private Class<Plugin> loadOnePluginClass(String nomClasse, Class<? extends Plugin> type) {

    Class<?> loadedClass = null;

    logger.info("Entr�e dans getInstance");
    try {
      // C'est ici que se passe le chargement de la classe par le
      // chargeur de classes.
      logger.info("Demande de chargement de la classe " + nomClasse + " par " + this);

      loadedClass = loader.loadClass(nomClasse);

      // v�rifer que la classe charg�e est du bon type

      if (loadedClass.getName().equals(nomClasse)) {
    	  return (Class<Plugin>) loadedClass;
    	  } else {
    	  logger.warning("Refus de la classe " + loadedClass.getName());
      }
      
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
      logger.warning("Le plugin " + nomClasse + " est introuvable");
      return null;
    }
    catch (NoClassDefFoundError e ) {
      // Survient si la classe n'a pas le bon nom
      e.printStackTrace();
      logger.warning("La classe " + nomClasse +
                     " ne peut �tre trouv�e");
      return null;
    }
    return null;
  }

}
