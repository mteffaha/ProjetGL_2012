package bebetes;

/*
 * ChampDeBebetes.java
 */

import java.awt.*;
import java.util.*;
import java.util.List;

import simu.Actionnable;
import simu.Simulateur;
import visu.Dessinable;
import visu.VisualisateurAnime;

/**
 * @author  collet
 * version avec Champignons (intermédiaire : n'implémente pas correctement Builder et/ou Factory Method)
 */
public class ChampDeBebetes extends VisualisateurAnime { 

	public static final float vitesseMax = 10f;

	protected int nbB; // nombre de bébètes
	
	protected Simulateur simu; // Mariage de convenance !!!
	
	protected FabriqueEntites fabrique; // référence sur la fabrique
	
	protected ChampDeBebetes(int largeur, int hauteur, int nb) {
		super(largeur,hauteur);
		setPreferredSize(new Dimension(largeur, hauteur));
		nbB = nb;
		initialiseElements();
	}
	
	// Séparation de la construction des éléments : pas tout à fait encore un Pattern, Builder, Factory Method ?
	public void initialiseElements() {
		// Récupération de la fabrique
		fabrique = FabriqueEntites.getFabriqueEntites();	
		// ATTENTION : le champi est dessinable mais pas actionnable...
		List<Bebete> lb = fabrique.fabriqueBebetes(this,nbB);
		// Initialisation du mariage de convenance avec le simulateur
		simu = new Simulateur();
		// initialisation des Dessinables...
		setDessinables(lb);
	}
		
		
	/* Redéfinitions pour synchroniser la gestion des 2 threads */
	
	public void demarre() {
		// on démarre d'abord la simulation
		simu.demarre();
		super.demarre();
	}

	public void arrete() {
		// on arrête d'abord la visualisation
		super.arrete();
		simu.arrete();
	}
	
	/* Redéfinitions pour rendre les 2 listes (visu et simu) identiques */
	
	public void setDessinables(List<? extends Dessinable> dessinables) {
		// Obligation de faire des contrôles dynamiques car on ne peut :
		// 1. ni changer la signature en List<? extends Bebete>
		// 2. ni faire un controle comme ci-desous (les paramËtres de gÈnÈricitÈ sont effacÈs) 
		//    List<? extends Bebete> lb = (List<? extends Bebete>)dessinables;
		ArrayList<Actionnable> la = new ArrayList<Actionnable>();
		super.setDessinables(dessinables);
		// Création de la liste des simulables...
		for (Dessinable d : dessinables) {
			if (d instanceof Actionnable)
				la.add((Actionnable)d);
		}
		// affectation de la liste pour le simulateur
		simu.setActionnables(la);
	}

	
	public int getNombreDeBebetes() {
		return getPositionnables().size();
	}

	public int getDelaiSimulation() { // Délégation : pattern Adapter
		return simu.getDelaiSimulation();
	}
	
	public void setDelaiSimulation(int delaiSimu) { // Délégation : pattern Adapter
		simu.setDelaiSimulation(delaiSimu);
	}

}
