package bebetes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author collet
 *
 * Fabrication des entités du simulateur de bébètes
 * - vérification statique (Java 5) 
 * @version 1.1
 */
public abstract class FabriqueEntites {

    // Les constructeurs des classes dérivées seront privés pour qu'on ne puisse pas créer 
    // directement une usine (pattern singleton)

    protected static FabriqueEntites _singleton;

    public static void init(Class<? extends FabriqueEntites> myClass) {
    /* initialisation par utilisation d'un objet classe d'un version concrète de la Fabrique
       myClass doit représenter une classe héritière de FabriqueEntites => vérification statique
       grâce au type paramétré Class<T> de Java 5 */

        if (_singleton != null) {
            throw new RuntimeException("FabriqueEntites: déjà créée par l'usine concrète " 
				  + _singleton.getClass().getName());
        } else {
            try {
                _singleton = (FabriqueEntites) myClass.newInstance();
            } catch (Exception ex) {
                System.err.println("FabriqueEntites: échec de l'instanciation de l'usine concrète " + 
											  myClass.getName());
                System.exit(1);
            }}}

    public static FabriqueEntites getFabriqueEntites() {
    	return _singleton;
    }
    /* renvoie l'usine courante ; l'usine doit avoir été initialisée */

    public abstract Bebete creeBebete(ChampDeBebetes c, int x, int y, float dC, float vC, Color col);

    public List<Bebete> fabriqueBebetes(ChampDeBebetes c, int nbBebetes) {
    /* rend une liste de nbBebetes bebetes créées par l'usine courante */
    		ArrayList<Bebete> nouvBebetes = new ArrayList<Bebete>();
    		Random generateur = new Random();
    		// unicité des couleurs des bébètes, juste là pour faire joli...
    		double racineCubiqueDuNombreDeBebetes = Math
    				.pow((double) nbBebetes, 1.0 / 3.0);
    		float etapeDeCouleur = (float) (1.0 / racineCubiqueDuNombreDeBebetes);
    		float r = 0.0f;
    		float g = 0.0f;
    		float b = 0.0f;
    		for (int i = 0; i < nbBebetes; i++) {
    			int x = (int) (generateur.nextFloat() * c.getLargeur());
    			if (x > c.getLargeur() - Bebete.TAILLEGRAPHIQUE)
    				x -= Bebete.TAILLEGRAPHIQUE;
    			int y = (int) (generateur.nextFloat() * c.getHauteur());
    			if (y > c.getHauteur() - Bebete.TAILLEGRAPHIQUE)
    				y -= Bebete.TAILLEGRAPHIQUE;
    			float direction = (float) (generateur.nextFloat() * 2 * Math.PI);
    			float vitesse = generateur.nextFloat() * ChampDeBebetes.vitesseMax;
    			r += etapeDeCouleur;
    			if (r > 1.0) {
    				r -= 1.0f;
    				g += etapeDeCouleur;
    				if (g > 1.0) {
    					g -= 1.0f;
    					b += etapeDeCouleur;
    					if (b > 1.0)
    						b -= 1.0f;
    				}
    			}
    			//
    			nouvBebetes.add(creeBebete(c, x, y, direction, vitesse, new Color(r, g, b)));
    		}
    		return nouvBebetes;
    	}

    public abstract Champi creeChampi(ChampDeBebetes c, int x, int y);

    public List<Champi> fabriqueChampis(ChampDeBebetes c, int nbChampi) {
            ArrayList<Champi> nouvChampis = new ArrayList<Champi>();
            Random generateur = new Random();
            for (int i = 0; i < nbChampi; i++) {
                int x = (int) (generateur.nextFloat() * (c.getLargeur()-Champi.TAILLEGRAPHIQUE));
                int y = (int) (generateur.nextFloat() * (c.getHauteur()-Champi.TAILLEGRAPHIQUE));
                nouvChampis.add(creeChampi(c, x, y));
            }
            return nouvChampis;
    }

    /* Création supplémentaire dans la fabrique : le champ */
    public abstract ChampDeBebetes creeChampDeBebetes(int largeur, int hauteur, int nb);
	
}
