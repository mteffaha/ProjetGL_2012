package m1.gl.projet2012.bebetes;

import java.awt.*;
import java.util.Random;

/**
 * @author  le coll�gue
 * @version  1.1
 */
class BebeteHasard extends Bebete {

	public static final int nbTourChgt = 30; // nombre de tours entre chaque
												// changement au hasard de
												// direction et de vitesse

	protected int nbTour; // nombre de tours de la b�b�tes depuis le pr�c�dent
							// changement de direction et de vitesse

	public BebeteHasard(ChampDeBebetes c, int x, int y, float dC, float vC, Color col) {
        super(c,x,y,dC,vC,col);

		nbTour = 0;
		energie = 50;
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
			 * version bugg�e par une parenth�se mal plac�e directionCourante =
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
	 * effectueDeplacement : rebondir sur les cot�s du champ
	 */
	public void effectueDeplacement() {
		x += (int) (vitesseCourante * Math.cos((double) directionCourante));
		y += (int) (vitesseCourante * Math.sin((double) directionCourante));
		if (x < 0) {
			x = -x;
			directionCourante = (float) (Math.PI - directionCourante)
					% (float) (Math.PI * 2);
		} else if (x >= champ.getLargeur()) { // la version pr�c�dente utilisait >
			x = 2 * champ.getLargeur() - x - 1; // la version pr�c�dente oubliait le
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
		} else if (y >= champ.getHauteur()) { // la version pr�c�dente utilisait >
			y = 2 * champ.getHauteur() - y - 1; // la version pr�c�dente oubliait le
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
	 * @param nbTour  nbTour � d�finir
	 * @uml.property  name="nbTour"
	 */
	public void setNbTour(int nbTour) {
		this.nbTour = nbTour;
	}

	@Override
	public void changeEnergie() {
		Random rand = new Random();
		if (rand.nextInt(100) < 50) {
			energie--;
		}
		else {
			energie++;
		}
		
	}

}
