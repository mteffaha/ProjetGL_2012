package bebetes;

/*
 * Bebete.java
 */

/**
 *
 * @author  collet  (d'apr�s L. O'Brien, C. Reynolds & B. Eckel)
 * @version 3.0
 */

import java.awt.*;
import java.util.List;
import java.util.Observable;

import fr.unice.plugin.Plugin;

import simu.Actionnable;
import util.DistancesEtDirections;
import visu.Champ;
import visu.Dessinable;
import visu.PerceptionAble;
import visu.Positionnable;

/**
 * @author collet
 */
public abstract class Bebete extends Observable implements Dessinable,
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
	protected boolean dead = false; // etat de la bebetes

	/* D�finition plus pr�cise de l'action de la bebete */

	public boolean isdead() {
		return dead;
	}
	
	public void setIsdead(boolean isdead) {
		dead = isdead;
		if (isdead) {
			setChanged();
			notifyObservers();
		}
	}

	// modifie les param�tres de vitesse et de direction.
	public abstract void calculeDeplacementAFaire();

	// modifie la position en fonction de vitesse et direction courantes
	public abstract void effectueDeplacement();

	// Impl�mentation de Actionnable */

	public void agit() {
		calculeDeplacementAFaire();
		effectueDeplacement();
	}

	/* Impl�mentation de Dessinable */

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

	/* Impl�mentation de Positionnable */

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

	public Champ getChamp() {
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

	/* Impl�mentation de PerceptionAble */

	public int getLongueurDeVue() {
		return longueurDeVue;
	}

	public float getChampDeVue() {
		return champDeVue;
	}

	public List<Positionnable> getChosesVues() { // utilisation de l'utilitaire
		return DistancesEtDirections.getChosesVues(this);
	}

	/*
	 * changer la longueur et le champ de vue est "static", alors que les
	 * consulter se fait par des fonctions membres
	 */

	public static void setLongueurDeVue(int lDV) {
		longueurDeVue = lDV;
	}

	public static void setChampDeVue(int cDV) {
		champDeVue = cDV;
	}

	// partie propre � la transformation en Plugin

	public String getName() {
		return "bebete";
	}

}
