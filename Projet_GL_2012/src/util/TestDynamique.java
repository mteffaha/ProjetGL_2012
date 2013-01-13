package util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;


import Panel.PanelCustom;

public class TestDynamique {

	private PanelCustom panel;
	private String directory;
	private ClassLoader classLoader;
	private ArrayList<String> testPassed;
	
	public TestDynamique(PanelCustom panel) {
		this.panel = panel;
		testPassed = new ArrayList<String>();
	}

	private static URL getURL(String dir) throws MalformedURLException {
		if (dir.indexOf("\\") != -1) {
			dir = dir.replaceAll("\\\\", "/");
		}
		if (!dir.startsWith("file:")) {
			dir = "file:" + dir;
		}
		return new URL(dir);
	}

	private String getQualifiedName(int baseNameLength, String classPath) {
		if ((!classPath.endsWith(".class")) || (classPath.indexOf('$') != -1)) {
			return null;
		}
		classPath = classPath.substring(baseNameLength).replace(
				File.separatorChar, '.');
		return classPath.substring(0, classPath.lastIndexOf('.'));
	}

	public void executerTousLesTests() {
		directory = "plugins/repository";
		try {
			classLoader = URLClassLoader
					.newInstance(new URL[] { getURL(directory) });
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		File dirFile = new File(directory);
		if (dirFile == null || !dirFile.isDirectory()) {
			throw new IllegalArgumentException(directory
					+ " n'est pas un repertoire");
		}
		if (!directory.endsWith("/")) {
			directory += "/";
		}
		loadFromSubdirectory(dirFile);
	}

	private void loadFromSubdirectory(File dir) {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				loadFromSubdirectory(file);
				continue;
			} else {
				String qualifiedName = getQualifiedName(
						this.directory.length(), files[i].toString());
				if (qualifiedName != null) {
					if (files[i].getName().endsWith("Test.class")) {
						try {
							Class<?> loadedClass = classLoader
									.loadClass(qualifiedName);
							
							if(testAlreadyPassed(files[i].getName()) == false)
							{
								testPassed.add(files[i].getName());
								if(executerTests(loadedClass) == true)
								{
									panel.addStringTextArea("Tous les tests de la classe " + loadedClass.getName() +" ont été réussi avec succés. \n");
								}
								else
								{
									panel.addStringTextArea("Les tests de la classe " + loadedClass.getName() + " n'ont pas été réussi correctement. \n");
								}
							}
							else
							{
								panel.addStringTextArea("Une classe de test " + qualifiedName + " a déjà été effectuée. \n");
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private boolean testAlreadyPassed(String nom)
	{
		boolean resultat = false;
		int i = 0;
		while(i < this.testPassed.size() && resultat == false)
		{
			if(this.testPassed.get(i).equals(nom))
			{
				resultat = true;
			}
			i++;
		}
		return resultat;
	}
	
	private boolean executerTests(Class<?> classTest) {
       /* Result result = JUnitCore.runClasses(classTest);
        if(result.getFailures().size() == 0)
        {
        	return true;
        }
        else
        {
        	return false;
        }*/
		return false;
	}
}