package bebetes;

/*
 * ChampDeBebetes.java
 */

import java.awt.*;
import java.util.*;
import java.util.List;
import Panel.BebteControl;
import simu.Actionnable;
import simu.Simulateur;
import visu.Dessinable;
import visu.VisualisateurAnime;

/**
 * @author  collet
 * version avec Champignons (interm�diaire : n'impl�mente pas correctement Builder et/ou Factory Method)
 */
public class ChampDeBebetes extends VisualisateurAnime { 

	public static final float vitesseMax = 10f;

	protected int nbB; // nombre de b�b�tes
	
	protected List<Bebete> lb;
	
	protected Simulateur simu; // Mariage de convenance !!!
	
	protected FabriqueEntites fabrique; // r�f�rence sur la fabrique
	
	protected ChampDeBebetes(int largeur, int hauteur, int nb) {
		super(largeur,hauteur);
		setPreferredSize(new Dimension(largeur, hauteur));
		nbB = nb;
		initialiseElements();
	}
	
	// S�paration de la construction des �l�ments : pas tout � fait encore un Pattern, Builder, Factory Method ?
	public void initialiseElements() {
		// R�cup�ration de la fabrique
		fabrique = FabriqueEntites.getFabriqueEntites();	
		// ATTENTION : le champi est dessinable mais pas actionnable...
		lb = fabrique.fabriqueBebetes(this,nbB);
		// Initialisation du mariage de convenance avec le simulateur
		simu = new Simulateur();
		// initialisation des Dessinables...
		setDessinables(lb);
		// ajout d'un observeur a tous les bebetes 
	   for(Bebete bet:this.lb){
			bet.addObserver(BebteControl.getInstance());
		}
	}
	public List<Bebete> getListBebete(){
		return this.lb;
	}
		
	/* Red�finitions pour synchroniser la gestion des 2 threads */
	
	public void demarre() {
		// on d�marre d'abord la simulation
		simu.demarre();
		super.demarre();
	}

	public void arrete() {
		// on arr�te d'abord la visualisation
		super.arrete();
		simu.arrete();
	}
	
	/* Red�finitions pour rendre les 2 listes (visu et simu) identiques */
	
	public void setDessinables(List<? extends Dessinable> dessinables) {
		// Obligation de faire des contr�les dynamiques car on ne peut :
		// 1. ni changer la signature en List<? extends Bebete>
		// 2. ni faire un controle comme ci-desous (les param�tres de g�n�ricit� sont effac�s) 
		//    List<? extends Bebete> lb = (List<? extends Bebete>)dessinables;
		ArrayList<Actionnable> la = new ArrayList<Actionnable>();
		super.setDessinables(dessinables);
		// Cr�ation de la liste des simulables...
		for (Dessinable d : dessinables) {
			if (d instanceof Actionnable)
				la.add((Actionnable)d);
		}
		// affectation de la liste pour le simulateur
		simu.setActionnables(la);
	}
	
	public void setNombreDeBebetes(int nb) {
		this.nbB=nb;
	}
	
	public int getNombreDeBebetes() {
		return getPositionnables().size();
	}

	public int getDelaiSimulation() { // D�l�gation : pattern Adapter
		return simu.getDelaiSimulation();
	}
	
	public void setDelaiSimulation(int delaiSimu) { // D�l�gation : pattern Adapter
		simu.setDelaiSimulation(delaiSimu);
	}

}
