package bebetes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import comportement.DeplacementEmergent;

import util.DistancesEtDirections;
import visu.Dirigeable;
import visu.Positionnable;

public class BebeteEmergente extends BebeteAvecComportement {


	public BebeteEmergente(ChampDeBebetes c, int x, int y,
			float dC, float vC, Color col) {
		super(c,x,y,dC,vC,col);
		setDeplacement(new DeplacementEmergent());
		energie = 50; //valeur de depart de l'energie
		
	}


	/*
	 * pas vraiment de covariance, donc on est oblig� de filtrer la liste pour savoir ce qui remue
	 * de ce qui est potentiellement fixe... 
	 */
	public static List<? extends Dirigeable> filtreDirigeables(List<? extends Positionnable> lp) {
		ArrayList<Dirigeable> output = new ArrayList<Dirigeable>();
		for (Positionnable p : lp) {
			if (p instanceof Dirigeable)
				output.add((Dirigeable)p);
		}
		return output;
	}
	


	// red�finition de l'action pour ne pas se d�placer si la bebete est trop proche d'une autre
	public void agit() {
		calculeDeplacementAFaire();
		if (distancePlusProche >= distanceMin) {
			effectueDeplacement();
		}
		changeEnergie();
	}

	@Override
	public void changeEnergie() {
		List<? extends Dirigeable> betesVues = filtreDirigeables(getChosesVues());
		if (betesVues.size() < BEAUCOUP) {
			energie--;
		}
		//if(energie <= 0) setDead(true);

	}


	@Override
	public void InitBebeteField() {
		// TODO Auto-generated method stub
		distancePlusProche = Float.MAX_VALUE;
	}


}
