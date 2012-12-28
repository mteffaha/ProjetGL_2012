package bebetes;

import java.awt.Graphics;

/*
 * version étendue ultra-basique avec un champi (pour démonstration)
 */
public class ChampDeBebetesAunChampi extends ChampDeBebetes {

	protected Champi leChampi;
	
	protected ChampDeBebetesAunChampi(int largeur, int hauteur, int nb) {
		super(largeur,hauteur,nb);
		regenereChampi();
	}

	// méthode pour regénérer un champi lorsqu'on relance une simulation (pas très propre)
	// il faudra mettre à plat le processus de création des entitÈs du champ par la suite
	public void regenereChampi() {
		leChampi = FabriqueEntites.getFabriqueEntites().creeChampi(this, largeur / 2, hauteur / 2);
	}
	
	public void initialiseElements() {
		regenereChampi();
		super.initialiseElements();
	}
	
    public void paint(Graphics g) {
		  // Redéfinition de la méthode de ChampDeBebetes pour afficher les champignons
      super.paint(g); // s'occupe donc d'afficher les bebetes
      leChampi.seDessine(g);
  }
	
}
