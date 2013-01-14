package bebetes;

import java.awt.*;
import java.util.*;

import Panel.BebteControl;
import Panel.PanelCustom;

import comportement.Deplacement;
import fr.unice.plugin.Plugin;
import simu.Actionnable;
import util.DistancesEtDirections;
import visu.Dessinable;
import visu.PerceptionAble;
import visu.Positionnable;


public abstract class BebeteAvecComportement extends Observable implements Dessinable,
        Actionnable, PerceptionAble, Plugin {


    static float champDeVue = (float) (Math.PI / 4); // En radians
    static int longueurDeVue = 20; // nb de pixel pour la longueur du champ de
    // vision

    protected int x;
    protected int y;
    protected float vitesseCourante; // vitesse en pixels par second
    protected float directionCourante; // en radians [0 - 2 PI[
    protected Color couleur; // Couleur de remplissage
    protected ChampDeBebetes champ; // Le champ
    protected boolean dead = false;// etat de la bebtete (morte ou vivante)
    protected int energie; // l'energie qu'il reste a la bebete
    protected Deplacement move;

	public static final int nbTourChgt = 30; // nombre de tours entre chaque
	// changement au hasard de
	// direction et de vitesse

	protected int nbTour; // nombre de tours de la b�b�tes depuis le pr�c�dent
	// changement de direction et de vitesse

	public static final float distanceMin = 10f; // En pixels

	public float distancePlusProche = Float.MAX_VALUE;

	protected int BEAUCOUP = 112;

    private Class<? extends BebeteAvecComportement> pendingState;

    private int chanceDevenirPredateur = 5;

    /**
     *
     *
     * @return Chance de devenir predateur (1/valeur de devenir predateur)
     */
    public int getChanceDevenirPredateur(){
        return chanceDevenirPredateur;
    }

    /**
     *
     * @param chance  Chance de devenir predateur (1/valeur de devenir predateur)
     */
    public void setChanceDevenirPredateur(int chance){
          chanceDevenirPredateur = chance;
    }

	public BebeteAvecComportement(ChampDeBebetes c, int x, int y, float dC,
			float vC, Color col) {
        champ = c;
        this.x = x;
        this.y = y;
        directionCourante = dC;
        vitesseCourante = vC;
        couleur = col;
        pendingState = null;
	}

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getEnergie() {
        return energie;
    }

    public void setEnergie(int energie){
        this.energie = energie;
    }

    public void agit() {
        calculeDeplacementAFaire();
        effectueDeplacement();
        changeEnergie();
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public void seDessine(Graphics g) {
        // a refaire
        int CDVDegres = (int) Math.toDegrees(champDeVue);
        g.setColor(couleur);
        g.fillArc(x, y, TAILLEGRAPHIQUE, TAILLEGRAPHIQUE,
                -(int) Math.toDegrees(directionCourante) - (CDVDegres / 2),
                CDVDegres);
    }

    public void calculerDeplacemementSensible() {
        directionCourante = (float) ((directionCourante + ((Math.random()
                * Math.PI / 2) - (Math.PI / 4))) % (Math.PI * 2));
        if (directionCourante < 0) {
            directionCourante += (float) (Math.PI * 2);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public ChampDeBebetes getChamp() {
        return champ; // on retourne un ChampDeBebetes...
    }

	/* Impl�mentation de Dirigeable */

    public float getVitesseCourante() {
        return vitesseCourante;
    }

    public void setVitesseCourante(float vitesseCourante) {
        this.vitesseCourante = vitesseCourante;
    }

    public float getDirectionCourante() {
        return directionCourante;
    }

    public void setDirectionCourante(float directionCourante) {
        this.directionCourante = directionCourante;
    }

    public  int getLongueurDeVue() {
        return longueurDeVue;
    }

    public float getChampDeVue() {
        return champDeVue;
    }

    public java.util.List<Positionnable> getChosesVues() { // utilisation de l'utilitaire
        return DistancesEtDirections.getChosesVues(this);
    }

    public static void setLongueurDeVue(int lDV) {
        longueurDeVue = lDV;
    }

    public static void setChampDeVue(float cv) {
        champDeVue = cv;
    }



    // partie propre � la transformation en Plugin

    public String getName() {
        return "bebete";
    }

    public Deplacement getDeplacement() {
        return move;
    }

    public void setDeplacement(Deplacement move) {
        this.move = move;
    }
	public abstract void InitBebeteField();

	public boolean calculeDeplacementAFaire() {
        boolean notify = false;
		InitBebeteField();
		if (champ.isBebeteSensible()) {
			if (((ChampDeBebetesAunChampi) this.getChamp())
					.BebeteSurChampi(this)) {
				calculerDeplacemementSensible();
                notify = true;
                System.out.println("Notify CalculeDeplacementAFaire");
			}else{
				move.calculeDeplacementAFaire(this);
			}
		} else {
		move.calculeDeplacementAFaire(this);
		}
        return notify;
	}

	public void effectueDeplacement() {
	
			move.effectueDeplacement(this);
		
	}

	public abstract void changeEnergie();

	/**
	 * @return nbTour
	 * @uml.property name="nbTour"
	 */
	public int getNbTour() {
		return nbTour;
	}

	/**
	 * @param nbTour
	 *            nbTour � d�finir
	 * @uml.property name="nbTour"
	 */
	public void setNbTour(int nbTour) {
		this.nbTour = nbTour;
	}

	public float getDistancePlusProche() {
		return distancePlusProche;
	}

	public void setDistancePlusProche(float distancePlusProche) {
		this.distancePlusProche = distancePlusProche;
	}

    public Class<? extends BebeteAvecComportement> getPendingState(){
        return this.pendingState;
    }

    public void setPendingState(Class<? extends BebeteAvecComportement> newPendingState){
        this.pendingState = newPendingState;

    }




}
