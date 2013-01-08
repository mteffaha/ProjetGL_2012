package bebetes;

import java.awt.*;
import java.util.Random;

import comportement.DeplacementEmergent;
import comportement.DeplacementHasard;

/**
 * @author  le coll�gue
 * @version  1.1
 */
public class BebeteHasard extends BebeteAvecComportement {



	public BebeteHasard(ChampDeBebetes c, int x, int y, float dC, float vC, Color col) {
		super(c, x, y, dC, vC, col);
        energie = 50;
        move=new DeplacementHasard();
		nbTour = 0;
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


	@Override
	public void InitBebeteField() {
		// TODO Auto-generated method stub
		nbTour = 0;
	}


}
