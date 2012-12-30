package comportement;

import bebetes.Bebete;
import bebetes.BebeteAvecComportement;
import bebetes.BebeteHasard;

public class DeplacementHasard implements Deplacement {

	public static boolean BebeteSensible;
	
	@Override
	public void calculeDeplacementAFaire(BebeteAvecComportement beb) {
		//System.out.println("je suis hasard");
		// TODO Auto-generated method stub
		beb.setNbTour(beb.getNbTour());
		beb.setNbTour(beb.getNbTour() % BebeteHasard.nbTourChgt);
		
		if (beb.getNbTour() == 0) { // c'est le moment de changer de direction et de
						   // vitesse
			beb.setVitesseCourante( beb.getVitesseCourante()
					+ (float) ((Math.random() * 2) - 1));
			if (beb.getVitesseCourante() < 3f) {
				beb.setVitesseCourante(3f);
			} else if (beb.getVitesseCourante() > 10f) {
				beb.setVitesseCourante(10f);
			}
			/*
			 * version bugg�e par une parenth�se mal plac�e directionCourante =
			 * (float) (directionCourante + ((Math.random() * Math.PI / 2) -
			 * (Math.PI / 4)) % (Math.PI * 2));
			 */
			beb.setDirectionCourante((float) ((beb.getDirectionCourante() + ((Math.random()
					* Math.PI / 2) - (Math.PI / 4))) % (Math.PI * 2)));
			if (beb.getDirectionCourante()< 0) {
				/*
				 * formule fausse directionCourante = (float) (Math.PI * 2) -
				 * directionCourante;
				 */
				// formule juste
				beb.setDirectionCourante(beb.getDirectionCourante()+ (float) (Math.PI * 2));
			}
		}

	}

	@Override
	public void effectueDeplacement(BebeteAvecComportement beb) {
		// TODO Auto-generated method stub
		beb.setX(beb.getX()+(int) (beb.getVitesseCourante()* Math.cos((double) beb.getDirectionCourante())));
		beb.setY(beb.getY()+(int) (beb.getVitesseCourante() * Math.sin((double) beb.getDirectionCourante())));
		if (beb.getX() < 0) {
			beb.setX( beb.getX()-beb.getX());
			beb.setDirectionCourante((float) (Math.PI - beb.getDirectionCourante())
					% (float) (Math.PI * 2));
		} else if (beb.getX() >= beb.getChamp().getLargeur()) { // la version pr�c�dente utilisait >
			beb.setX(2 * beb.getChamp().getLargeur() - beb.getX() - 1); // la version pr�c�dente oubliait le
											// -1
			beb.setDirectionCourante((float) (Math.PI - beb.getDirectionCourante())
					% (float) (Math.PI * 2));
		}
		if (beb.getDirectionCourante()< 0) {
			beb.setDirectionCourante(beb.getDirectionCourante()+(float) (Math.PI * 2));
		}
		if (beb.getY()< 0) {
			beb.setY(beb.getY()-beb.getY());
			beb.setDirectionCourante( (float) (Math.PI * 2 - beb.getDirectionCourante()));
		} else if (beb.getY() >= beb.getChamp().getHauteur()) { // la version pr�c�dente utilisait >
			beb.setY( 2 * beb.getChamp().getHauteur() - beb.getY() - 1); // la version pr�c�dente oubliait le
											// -1
			beb.setDirectionCourante((float) (Math.PI * 2 - beb.getDirectionCourante()));
		}

	}

}
