package bebetes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import comportement.DeplacementEmergent;

import util.DistancesEtDirections;
import visu.Dirigeable;
import visu.Positionnable;

public class BebeteEmergente extends BebeteAvecComportement {


    public BebeteEmergente(ChampDeBebetes c, int x, int y,
                           float dC, float vC, Color col) {
        super(c, x, y, dC, vC, col);
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
                output.add((Dirigeable) p);
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
        if (energie <= 0) setDead(true);

    }


    @Override
    public void InitBebeteField() {
        // TODO Auto-generated method stub
        distancePlusProche = Float.MAX_VALUE;
    }


    private int framCounter = 0;

    @Override
    public void seDessine(Graphics g) {

        // a refaire
        int CDVDegres = (int) Math.toDegrees(champDeVue);
        g.setColor(new Color(0.9f, 0, 0, 0.3f));
        if (framCounter > 50) {
            //g.drawOval(x+(TAILLEGRAPHIQUE/4),y+(TAILLEGRAPHIQUE/4),TAILLEGRAPHIQUE/2,TAILLEGRAPHIQUE/2);

        } else {
            g.fillArc(x - (TAILLEGRAPHIQUE / 4), y - (TAILLEGRAPHIQUE / 4), TAILLEGRAPHIQUE + (TAILLEGRAPHIQUE / 2), TAILLEGRAPHIQUE + (TAILLEGRAPHIQUE / 2),
                    -(int) Math.toDegrees(directionCourante) - (CDVDegres / 2),
                    CDVDegres);
        }

        framCounter++;
        if (framCounter > 100) {
            framCounter = 0;
        }
        //g.setColor(new Color(0,0,0,0.2f));
        //g.drawRect(x,y-10,TAILLEGRAPHIQUE,4);
        if (energie < 20) {
            if (energie > 0) {
                g.setColor(new Color(0.9f, 0, 0.0f, 0.5f));
            } else {
                g.setColor(new Color(0.5f,0.5f,0.5F,0.5f));
            }
        } else {
            g.setColor(new Color(0.0f, 0.9f, 0.0f, 0.5f));
        }
        g.drawString(""+energie, x, y - 10);
        g.setColor(couleur);
        g.fillArc(x, y, TAILLEGRAPHIQUE, TAILLEGRAPHIQUE,
                -(int) Math.toDegrees(directionCourante) - (CDVDegres / 2),
                CDVDegres);
    }


}
