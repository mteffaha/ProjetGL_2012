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

    public BebeteHasard(BebeteAvecComportement bebete) {
        super(bebete.getChamp(),bebete.getX(), bebete.getY(), bebete.getDirectionCourante(), bebete.getVitesseCourante(), bebete.getCouleur());
        setDeplacement(new DeplacementHasard());
        energie = 50; //valeur de depart de l'energie
        nbTour = 0;
    }

	
	@Override
	public void changeEnergie() {
		Random rand = new Random();
		if (rand.nextInt(60) == 0) {
			energie--;
		}
		else {
			energie++;
		}
		//if(energie <= 0) setDead(true);
	}


	@Override
	public void InitBebeteField() {
		// TODO Auto-generated method stub
		nbTour = 0;
	}

    @Override
    public void seDessine(Graphics g) {
        // a refaire
        int CDVDegres = (int) Math.toDegrees(champDeVue);
        g.setColor(couleur);
        g.fillArc(x, y, TAILLEGRAPHIQUE, TAILLEGRAPHIQUE,
                -(int) Math.toDegrees(directionCourante) - (CDVDegres / 2),
                CDVDegres);
    }


}
