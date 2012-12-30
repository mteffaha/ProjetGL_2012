package comportement;


import java.util.List;
import java.util.logging.Logger;

import bebetes.BebeteAvecComportement;
import bebetes.BebeteEmergente;


import util.DistancesEtDirections;
import visu.Dirigeable;


public class DeplacementEmergent implements Deplacement {

	
	@Override
	public void calculeDeplacementAFaire(BebeteAvecComportement beb) {
		//System.out.println("je suis emergeant");
		// TODO Auto-generated method stub
		// calcul des vitesses et directions moyennes, calcul de la distance a la bete la plus proche
		float vit = beb.getVitesseCourante();
		float dir = beb.getDirectionCourante();
		double plusPetiteDistance = Double.MAX_VALUE;

		// List<? extends Positionnable> lp = getChosesVues();

		List<? extends Dirigeable> betesVues =BebeteEmergente.filtreDirigeables(beb.getChosesVues());
		for (Dirigeable p : betesVues) {
			vit += p.getVitesseCourante();
			dir += p.getDirectionCourante();
			plusPetiteDistance = Math.min(plusPetiteDistance,
					DistancesEtDirections.distanceDepuisUnPoint(beb.getX(), beb.getY(), p.getX(),p.getY()));
		} 
		beb.setVitesseCourante( Math.max(vit / (betesVues.size() + 1),2)); // vitesse minimale = 2
				beb.setDirectionCourante(dir / (betesVues.size() + 1));
				beb.setDistancePlusProche((float) plusPetiteDistance);
				
	}

	@Override
	public void effectueDeplacement(BebeteAvecComportement beb) {
		// TODO Auto-generated method stub

		beb.setX(beb.getX() + (int) (beb.getVitesseCourante() * Math.cos((double) beb.getDirectionCourante())));
		beb.setY(beb.getY() + (int) (beb.getVitesseCourante() * Math.sin((double) beb.getDirectionCourante())));
		beb.setX(beb.getX() % beb.getChamp().getLargeur());
		beb.setY(beb.getY() % beb.getChamp().getHauteur());
		if (beb.getX() < 0) {
			beb.setX(beb.getX() + beb.getChamp().getLargeur());
		}
		if (beb.getY() < 0) {
			beb.setY(beb.getY() + beb.getChamp().getHauteur());
		}

	}

}
