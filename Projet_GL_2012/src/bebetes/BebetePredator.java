package bebetes;

import comportement.DeplacementPredator;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * User: Teffaha Mortadha
 * Date: 1/13/13
 * Time: 1:25 AM
 * Bebete Predator : choisie une bebete a suivre (choisit la plus proche tant qu'elle à un minimum d'énergie)
 */
public class BebetePredator extends BebeteAvecComportement {


    private static final int STARTER_ENERGY = 10;       // le taux d'enérgie initiale si on ne passe pas de bébete initiale
    private static final int NEGATIVE_SLOPE = -10;      // la pente utilise dans l'équation qui calcule le caution(expliqué en vas)

    private Bebete target;  // la bebete cible du prédateur
    private boolean isLazy; // définit si la bébete est paresseuse (vas essaye de suivre les bebete les moin rapide)
    // ou pas (vas éssayer de poursuivre les bebete qui ont le plus d'énergie)
    private Class<? extends Bebete> nextPendingState; // l'état vers lequelle la bebete vas se transformer une foix
    private Class<? extends Bebete> pendingState;
    // elle a récuperer l'enérgie de la cible


    public BebetePredator(ChampDeBebetes c, int x, int y, float dC, float vC, Color col) {
        super(c, x, y, dC, vC, col);
        Logger.getLogger("fr.unice.plugin.PluginFactory") .log(Level.SEVERE,"Bebete Predatrive creer");
        energie = BebetePredator.STARTER_ENERGY;
        setDeplacement(new DeplacementPredator());
        target = null;
        isLazy = new Random().nextBoolean();
        // on récupere au hasard soit la bebete emergente soit la bebete hasard
        if (new Random().nextBoolean()) {
            this.nextPendingState = BebeteEmergente.class;
        } else {
            this.nextPendingState = BebeteHasard.class;
        }
    }


    /**
     * Copy Constructor : crée une Bebete a partir d'un autre
     *
     * @param ancienneBebete , lz bebete dont on vas copie les attribue
     */
    public BebetePredator(Bebete ancienneBebete) {
        super(ancienneBebete.getChamp(), ancienneBebete.getX(),
                ancienneBebete.getY(), ancienneBebete.getDirectionCourante(),
                ancienneBebete.getVitesseCourante(), ancienneBebete.getCouleur());
        energie = ancienneBebete.getEnergie();
        target = null;
        nextPendingState = ancienneBebete.getClass();
    }

    @Override
    public void InitBebeteField() {

        /*si aucune cible n'a étais définit
          on essaye de récupérer la bebete qui nous offre le plus d'enérgie en ce déplacent le mois possible
          */
        if (target != null) {
            // on recupere les Tous les Bebete
            Iterator<Bebete> iterator = this.getChamp().getListeBebete().iterator();
            // list qui vas contenie les bebete et leur caution calculer, ceux qui ont
            TreeMap<Double, Bebete> list = new TreeMap<Double, Bebete>();
            Double caution = 0.0d;
            while (iterator.hasNext()) {
                Bebete beb = iterator.next();

                /*  on utilise une fonction qui contient une pente negative en fonction de la distance/vitesse
                    (selon la pareisse de la bebete) pour que plus la distance est grande plus le caution est petit
                    elle prend aussi la taux d'énergie positive pour que plus on a d'enérgie plus la valeur
                    est grande*/
                if (isLazy) {
                    Double distance = Math.sqrt(Math.pow(this.getX() - beb.getX(), 2)
                            + Math.pow(this.getY() - beb.getY(), 2));
                    caution = distance * BebetePredator.NEGATIVE_SLOPE * (-1) + beb.getEnergie() - beb.getVitesseCourante();
                    list.put(caution, beb);
                } else {
                    caution = Double.valueOf(beb.getVitesseCourante() * BebetePredator.NEGATIVE_SLOPE * (-1) + beb.getEnergie() - beb.getVitesseCourante());
                    list.put(caution, beb);
                }
            }
            /*  on recupere la bebete qui à le plus grand caution
                plus grand caution ça veut dire celle qui posséde la grand rapport (rapprochement/Taille d'enérgie)
             */
            target = list.lastEntry().getValue();

        }

    }

    @Override
    public void changeEnergie() {
        if (target == null) // si on n'a pas encore de cible on ne peut pas changer d'enérgie.
            return;
        boolean isTouching = false; // permet de teste s'il y a une collision entre notre predateur et ca cible

        if (new Square(getX() + (TAILLEGRAPHIQUE / 2), getY() + (TAILLEGRAPHIQUE / 2), TAILLEGRAPHIQUE)
                .isCollidingWith(new Square(target.getX() + (TAILLEGRAPHIQUE / 2), target.getY() + (TAILLEGRAPHIQUE / 2), TAILLEGRAPHIQUE))) {
            // on consome l'enérgie de la cible et on la tuer
            this.energie += this.energie + target.getEnergie();
            target.setDead(true);
            this.setPendingState(this.nextPendingState);
        }
    }


    public Bebete getTarget() {
        return target;
    }

    @Override
    public void seDessine(Graphics g) {
        // a refaire
        int CDVDegres = (int) Math.toDegrees(champDeVue);
        g.setColor(new Color(0,0,0));

        g.fillArc(x, y, TAILLEGRAPHIQUE, TAILLEGRAPHIQUE,
                -(int) Math.toDegrees(directionCourante) - (CDVDegres / 2),
                CDVDegres);
    }

    public void setPendingState(Class<? extends Bebete> pendingState) {
        this.pendingState = pendingState;
    }

    public Class<? extends Bebete> getPendingState() {
        return pendingState;
    }
}

class Square {
    private int x, y;
    private int width;

    Square(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    /**
     * test si une interesction entre ce carre et un autre passée en parametre existe
     *
     * @param other l'autre carrer avec lequelle on doit tester
     * @return vrai si une intersection existe , faux sinon
     */
    boolean isCollidingWith(Square other) {
        // on suppose qu'il y a un BoundingBox en forme de cercle qui entoure les carre
        int x1 = this.x + (this.width / 2), x2 = other.x + (other.width / 2);
        int y1 = this.y + (this.width / 2), y2 = other.y + (other.width / 2);
        Double distanceBetweenCenters = Math.sqrt(Math.pow(Math.abs(x1 - x2), 2)
                + Math.pow(Math.abs(y1 - y2), 2));
        if (distanceBetweenCenters < (2 * this.width)) {    // il y a une intersection
            return true;
        }

        return false;
    }


}