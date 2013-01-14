package util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import Panel.PanelCustom;

/**
 * Classe de chargement de tests dynamiques
 * 
 * @author tirius
 */
public class TestDynamique {

	/**
	 * Attribut representant le panel custom ou l'on va ecrire les resultats des
	 * tests
	 */
	private PanelCustom panel;

	/**
	 * Chaine de caractere representant le dossier ou le ClassLoader est
	 * initialise
	 */
	private String directory;

	/**
	 * ClassLoader
	 */
	private ClassLoader classLoader;

	/**
	 * ArrayList contenant les classes de tests deja realisees
	 */
	private ArrayList<String> testPassed;

	/**
	 * Constructeur
	 * 
	 * @param panel
	 */
	public TestDynamique(PanelCustom panel) {
		this.panel = panel;
		testPassed = new ArrayList<String>();
	}

	/**
	 * Methode renvoyant une URL au format correcte ( sans antislash etc )
	 * 
	 * @param dir
	 * @return
	 * @throws MalformedURLException
	 */
	private static URL getURL(String dir) throws MalformedURLException {
		if (dir.indexOf("\\") != -1) {
			dir = dir.replaceAll("\\\\", "/");
		}
		if (!dir.startsWith("file:")) {
			dir = "file:" + dir;
		}
		return new URL(dir);
	}

	/**
	 * Methode permettant de retourner le nom complet ( package etc ) de la
	 * classe
	 * 
	 * @param baseNameLength
	 * @param classPath
	 * @return
	 */
	private String getQualifiedName(int baseNameLength, String classPath) {
		if ((!classPath.endsWith("Test.class"))
				|| (classPath.indexOf('$') != -1)) {
			return null;
		}
		classPath = classPath.substring(baseNameLength).replace(
				File.separatorChar, '.');
		return classPath.substring(0, classPath.lastIndexOf('.'));
	}

	/**
	 * Methode permettant d'executer tous les test
	 */
	public void executerTousLesTests() {
		directory = "plugins/repositoryTest/";
		// Instanciation du ClassLoader
		try {
			classLoader = URLClassLoader
					.newInstance(new URL[] { getURL(directory) });
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		File dirFile = new File(directory);
		// Verification de la nature du fichier
		if (dirFile == null || !dirFile.isDirectory()) {
			throw new IllegalArgumentException(directory
					+ " n'est pas un repertoire");
		}
		if (!directory.endsWith("/")) {
			directory += "/";
		}
		// Appel a la methode loadFromSubdirectoy effectuant principalement le
		// traitement
		loadFromSubdirectory(dirFile);
	}

	/**
	 * Permet de charger les test d'un dossier Si un sous-dossier est detecte on
	 * fait un appel recurssif de la fonction
	 * 
	 * @param dir
	 */
	private void loadFromSubdirectory(File dir) {
		// Parcours de la liste de fichier du dossier mis en parametre
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			// Si le fichier est un dossier
			if (file.isDirectory()) {
				loadFromSubdirectory(file);
				continue;
			} else {
				String qualifiedName = getQualifiedName(
						this.directory.length(), files[i].toString());
				// Si le fichier est correct
				if (qualifiedName != null) {
					// Chargement de la classe
					try {
						Class<?> loadedClass = classLoader
								.loadClass(qualifiedName);
						// Si le test n'a pas encore été passe
						if (testAlreadyPassed(files[i].getName()) == false) {
							// Ajout dans le tableau des tests deja effectues
							testPassed.add(files[i].getName());
							if (executerTests(loadedClass) == true) {
								panel.addStringTextArea("Tous les tests de la classe "
										+ loadedClass.getName()
										+ " ont été réussi avec succés. \n");
							} else {
								panel.addStringTextArea("Les tests de la classe "
										+ loadedClass.getName()
										+ " n'ont pas été réussi correctement. \n");
							}
						} else {
							panel.addStringTextArea("Une classe de test "
									+ qualifiedName
									+ " a déjà été effectuée. \n");
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Methode permettant de regarder si le test a deja ete effectue
	 * 
	 * @param nom
	 * @return
	 */
	private boolean testAlreadyPassed(String nom) {
		boolean resultat = false;
		int i = 0;
		while (i < this.testPassed.size() && resultat == false) {
			if (this.testPassed.get(i).equals(nom)) {
				resultat = true;
			}
			i++;
		}
		return resultat;
	}

	/**
	 * Execute une classe de tests. Si un des tests de la classes s'est mal
	 * deroule, la methode renvoie faux
	 * 
	 * @param classTest
	 * @return
	 */
	private boolean executerTests(Class<?> classTest) {

		Result result = JUnitCore.runClasses(classTest);
		if (result.getFailures().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
}