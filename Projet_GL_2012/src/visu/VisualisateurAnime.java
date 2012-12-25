package visu;

import java.util.List;

import simu.Actionnable;


/**
 * @author  collet
 */
public class VisualisateurAnime extends Visualisateur implements Runnable {

	// volatile = gestion des threads
	
	protected volatile Thread threadAnimation = null;	
	protected volatile int delaiVisuel = 10; // en ms
	
	public synchronized int getDelaiVisuel() {
		return delaiVisuel;
	}

	public synchronized void setDelaiVisuel(int delaiVisuel) {
		this.delaiVisuel = delaiVisuel;
	}
	
	public void demarre() {
	    threadAnimation = new Thread(this);
	    threadAnimation.start();
	}

	public void arrete() {
		threadAnimation.interrupt();
	}
	
	public void run() {
		threadAnimation = Thread.currentThread();
		try {
			while (!threadAnimation.isInterrupted()) {
				repaint();
				Thread.sleep(delaiVisuel);
			}
		} catch (InterruptedException exception) {
		}
	}
	
	public VisualisateurAnime() {
		super();
	}

	public VisualisateurAnime(int delai) {
		super();
		setDelaiVisuel(delai);
	}	
	
	public VisualisateurAnime(int largeur, int hauteur, List<? extends Dessinable> ld) {
		super(largeur, hauteur, ld);
	}

	public VisualisateurAnime(int largeur, int hauteur, List<? extends Dessinable> ld, int delai) {
		super(largeur, hauteur, ld);
		setDelaiVisuel(delai);
	}
	
	public VisualisateurAnime(int largeur, int hauteur) {
		super(largeur, hauteur);
	}

	public VisualisateurAnime(int largeur, int hauteur, int delai) {
		super(largeur, hauteur);
		setDelaiVisuel(delai);
	}
	
}
