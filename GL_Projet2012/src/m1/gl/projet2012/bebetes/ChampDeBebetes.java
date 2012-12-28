package m1.gl.projet2012.bebetes;

/*
 * ChampDeBebetes.java
 */

import java.awt.*;
import java.util.*;
import java.util.List;

import m1.gl.projet2012.simu.Actionnable;
import m1.gl.projet2012.simu.Simulateur;
import m1.gl.projet2012.visu.Dessinable;
import m1.gl.projet2012.visu.VisualisateurAnime;

/**
 * @author  collet
 */
public class ChampDeBebetes extends VisualisateurAnime { 

	public static final float vitesseMax = 10f;

	protected int nbB; // nombre de b�b�tes
	
	protected Simulateur simu; // Mariage de convenance !!!
	
	public ChampDeBebetes(int largeur, int hauteur, int nb) {
		super(largeur,hauteur);
		setPreferredSize(new Dimension(largeur, hauteur));
		nbB = nb;
		initialiseElements();
	}
	
	// S�paration de la construction des �l�ments
	protected void initialiseElements() {
		List <? extends Dessinable> ld = fabriqueBebetes(nbB);
		// Initialisation du mariage de convenance avec le simulateur
		simu = new Simulateur(50);
		// initialisation des Dessinables (propag�e dans le simulateur par filtrage)
		setDessinables(ld);
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
	
	/* Red�finitions pour rendre les 2 listes (m1.gl.projet2012.visu et m1.gl.projet2012.simu) identiques */
	
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
		// fixation de la liste pour le simulateur
		simu.setActionnables(la);
	}

	public ArrayList<? extends Bebete> fabriqueBebetes(int nb) {
		ArrayList<Bebete> nouvBebetes = new ArrayList<Bebete>();
		Random generateur = new Random();
		// unicit� des couleurs des b�b�tes, juste l� pour faire joli...
		double racineCubiqueDuNombreDeBebetes = Math
				.pow((double) nb, 1.0 / 3.0);
		float etapeDeCouleur = (float) (1.0 / racineCubiqueDuNombreDeBebetes);
		float r = 0.0f;
		float g = 0.0f;
		float b = 0.0f;
		for (int i = 0; i < nb; i++) {
			int x = (int) (generateur.nextFloat() * largeur);
			if (x > largeur - Bebete.TAILLEGRAPHIQUE)
				x -= Bebete.TAILLEGRAPHIQUE;
			int y = (int) (generateur.nextFloat() * hauteur);
			if (y > hauteur - Bebete.TAILLEGRAPHIQUE)
				y -= Bebete.TAILLEGRAPHIQUE;
			float direction = (float) (generateur.nextFloat() * 2 * Math.PI);
			float vitesse = generateur.nextFloat() * vitesseMax;
			r += etapeDeCouleur;
			if (r > 1.0) {
				r -= 1.0f;
				g += etapeDeCouleur;
				if (g > 1.0) {
					g -= 1.0f;
					b += etapeDeCouleur;
					if (b > 1.0)
						b -= 1.0f;
				}
			}
			// Le fameux de la capture impossible du joker ?
			nouvBebetes.add(new BebeteEmergente(this, x, y, direction, vitesse,
					new Color(r, g, b)));
		}
		return nouvBebetes;
	}

	public int getNombreDeBebetes() {
		return getPositionnables().size();
	}

	public int getDelaiSimulation() { // D�l�gation
		return simu.getDelaiSimulation();
	}
	
	public void setDelaiSimulation(int delaiSimu) { // D�l�gation
		simu.setDelaiSimulation(delaiSimu);
	}

}
