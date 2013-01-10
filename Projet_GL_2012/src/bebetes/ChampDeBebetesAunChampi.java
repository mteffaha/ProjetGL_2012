package bebetes;

import java.awt.Graphics;

import util.DistancesEtDirections;


/*
 * version �tendue ultra-basique avec un champi (pour d�monstration)
 */
public class ChampDeBebetesAunChampi extends ChampDeBebetes {

	protected Champi leChampi;
	
	protected ChampDeBebetesAunChampi(int largeur, int hauteur, int nb) {
		super(largeur,hauteur,nb);
		regenereChampi();
	}

	// m�thode pour reg�n�rer un champi lorsqu'on relance une simulation (pas tr�s propre)
	// il faudra mettre � plat le processus de cr�ation des entit�s du champ par la suite
	public void regenereChampi() {
		leChampi = FabriqueEntites.getFabriqueEntites().creeChampi(this, largeur / 2, hauteur / 2);
	}
	
	public void initialiseElements() {
		regenereChampi();
		super.initialiseElements();
	}
	
    public void paint(Graphics g) {
		  // Red�finition de la m�thode de ChampDeBebetes pour afficher les champignons
      super.paint(g); // s'occupe donc d'afficher les bebetes
      leChampi.seDessine(g);
  }
    public boolean BebeteSurChampi(Bebete b) {
        if (DistancesEtDirections.distanceDepuisUnPoint(b.getX(),b.getY(),leChampi.getX(),leChampi.getY())
                     <= (Champi.TAILLEGRAPHIQUE))
                return true;
        
        return false;
    }

	public Champi getLeChampi() {
		return leChampi;
	}

	public void setLeChampi(Champi leChampi) {
		this.leChampi = leChampi;
	}

    
	
}
