package bebetes;

import java.awt.Color;

import comportement.Deplacement;


public abstract class BebeteAvecComportement extends Bebete {

	public static final int nbTourChgt = 30; // nombre de tours entre chaque
	// changement au hasard de
	// direction et de vitesse

	protected int nbTour; // nombre de tours de la b�b�tes depuis le pr�c�dent
	// changement de direction et de vitesse

	public static final float distanceMin = 10f; // En pixels

	public float distancePlusProche = Float.MAX_VALUE;

	protected int BEAUCOUP = 2;

	public BebeteAvecComportement(ChampDeBebetes c, int x, int y, float dC,
			float vC, Color col) {
		super(c, x, y, dC, vC, col);
		// TODO Auto-generated constructor stub
	}

	public abstract void InitBebeteField();

	public void calculeDeplacementAFaire() {
		InitBebeteField();
		if (champ.isBebeteSensible()) {
			if (((ChampDeBebetesAunChampi) this.getChamp())
					.BebeteSurChampi(this)) {
				calculerDeplacemementSensible();
			}else{
				move.calculeDeplacementAFaire(this);
			}
		} else {
		move.calculeDeplacementAFaire(this);
		}
	}

	public void effectueDeplacement() {
	
			move.effectueDeplacement(this);
		
	}

	public abstract void changeEnergie();

	/**
	 * @return nbTour
	 * @uml.property name="nbTour"
	 */
	public int getNbTour() {
		return nbTour;
	}

	/**
	 * @param nbTour
	 *            nbTour � d�finir
	 * @uml.property name="nbTour"
	 */
	public void setNbTour(int nbTour) {
		this.nbTour = nbTour;
	}

	public float getDistancePlusProche() {
		return distancePlusProche;
	}

	public void setDistancePlusProche(float distancePlusProche) {
		this.distancePlusProche = distancePlusProche;
	}

}
