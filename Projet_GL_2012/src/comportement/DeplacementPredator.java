package comportement;

import bebetes.Bebete;
import bebetes.BebeteAvecComportement;
import bebetes.BebetePredator;

import java.util.List;

/**
 * User: Teffaha Mortadha
 * Date: 1/13/13
 * Time: 6:33 AM
 */
public class DeplacementPredator implements Deplacement {
    private Bebete target;

    @Override
    public void calculeDeplacementAFaire(BebeteAvecComportement cl) {
        BebetePredator original = (BebetePredator) cl;
        Bebete beb = original.getTarget();
        if(beb == null)
            return;
        /*
         on calcule le prochain points de la bebete cible.
          */
        // tous d'abord on sauvegarde les parametre de la cible
        int x = beb.getX(), y = beb.getY(), ldv = beb.getLongueurDeVue();
        float v = beb.getVitesseCourante(), dpp = beb.getDistancePlusProche(), dc = beb.getDirectionCourante();
        // on luis demande de calculer sont deplacement
        int targetX = beb.getX(), targetY = beb.getY();
        // on remet ses attribue en ordre
        beb.setX(x);
        beb.setY(y);
        beb.setVitesseCourante(v); // vitesse minimale = 2
        beb.setDirectionCourante(dc);
        beb.setDistancePlusProche(dpp);
        // on calcule l'angle entre la position de la bebete predatrice et la position de ça cible
        int dy = targetY - original.getY();
        int dx = targetX - original.getY();
        original.setDirectionCourante((float) Math.atan2(dx, dy));
        /*  on vérifie que notre prédatrive est assez rapide pour rattraper la bebete cible
            ci notre Bebete n'est pas aux moin 110% plus rapide que ça cible on augmente ça vitesse
         ( Survive of the fastest ;D )
        */
        if (original.getVitesseCourante() <= (original.getTarget().getVitesseCourante() * 1.1)) {
            original.setVitesseCourante((float) (original.getVitesseCourante() * 1.1));
        }

    }

    @Override
    public void effectueDeplacement(BebeteAvecComportement beb) {
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
