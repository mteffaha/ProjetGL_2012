package bebetes;

import java.awt.*;

/**
 * @author  le collègue
 * @version  1.1
 */
public class BebeteHasard extends Bebete {

	public static final int nbTourChgt = 30; // nombre de tours entre chaque
												// changement au hasard de
												// direction et de vitesse

	protected int nbTour; // nombre de tours de la bébêtes depuis le précédent
							// changement de direction et de vitesse

	protected BebeteHasard(ChampDeBebetes c, int x, int y, float dC, float vC, Color col) {
		champ = c;
		this.x = x;
		this.y = y;
		directionCourante = dC;
		vitesseCourante = vC;
		couleur = col;
		nbTour = 0;
	}

	public void calculeDeplacementAFaire() {
		nbTour++;
		nbTour %= nbTourChgt;
		if (nbTour == 0) { // c'est le moment de changer de direction et de
						   // vitesse
			vitesseCourante = vitesseCourante
					+ (float) ((Math.random() * 2) - 1);
			if (vitesseCourante < 3f) {
				vitesseCourante = 3f;
			} else if (vitesseCourante > 10f) {
				vitesseCourante = 10f;
			}
			/*
			 * version buggée par une parenthèse mal placée directionCourante =
			 * (float) (directionCourante + ((Math.random() * Math.PI / 2) -
			 * (Math.PI / 4)) % (Math.PI * 2));
			 */
			directionCourante = (float) ((directionCourante + ((Math.random()
					* Math.PI / 2) - (Math.PI / 4))) % (Math.PI * 2));
			if (directionCourante < 0) {
				/*
				 * formule fausse directionCourante = (float) (Math.PI * 2) -
				 * directionCourante;
				 */
				// formule juste
				directionCourante += (float) (Math.PI * 2);
			}
		}
	}

	/*
	 * effectueDeplacement : rebondir sur les cotés du champ
	 */
	public void effectueDeplacement() {
		x += (int) (vitesseCourante * Math.cos((double) directionCourante));
		y += (int) (vitesseCourante * Math.sin((double) directionCourante));
		if (x < 0) {
			x = -x;
			directionCourante = (float) (Math.PI - directionCourante)
					% (float) (Math.PI * 2);
		} else if (x >= champ.getLargeur()) { // la version précédente utilisait >
			x = 2 * champ.getLargeur() - x - 1; // la version précédente oubliait le
											// -1
			directionCourante = (float) (Math.PI - directionCourante)
					% (float) (Math.PI * 2);
		}
		if (directionCourante < 0) {
			directionCourante += (float) (Math.PI * 2);
		}
		if (y < 0) {
			y = -y;
			directionCourante = (float) (Math.PI * 2 - directionCourante);
		} else if (y >= champ.getHauteur()) { // la version précédente utilisait >
			y = 2 * champ.getHauteur() - y - 1; // la version précédente oubliait le
											// -1
			directionCourante = (float) (Math.PI * 2 - directionCourante);
		}
	}

	/**
	 * @return  nbTour
	 * @uml.property  name="nbTour"
	 */
	public int getNbTour() {
		return nbTour;
	}

	/**
	 * @param nbTour  nbTour à définir
	 * @uml.property  name="nbTour"
	 */
	public void setNbTour(int nbTour) {
		this.nbTour = nbTour;
	}

}
