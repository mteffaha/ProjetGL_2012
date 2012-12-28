package m1.gl.projet2012.visu;

import java.util.List;


/**
 * @author  collet
 */
public class VisualisateurAnime extends Visualisateur implements Runnable {

	protected Thread threadAnimation = null;
	
	protected int delaiVisuel = 10; // en ms
	
	public int getDelaiVisuel() {
		return delaiVisuel;
	}

	public void setDelaiVisuel(int delaiVisuel) {
		this.delaiVisuel = delaiVisuel;
	}
	
	public void demarre() {
	    threadAnimation = new Thread(this);
	    threadAnimation.start();
	}

	public void arrete() {
		threadAnimation = null; // ceci sera test� dans le run()
	}
	
	public void run() {
	    // Code du thread pour arr�t propre (cf. sun technical tips)
	    Thread currentThread = Thread.currentThread();
	    while (threadAnimation == currentThread) {
	    	repaint();
	        try {
				Thread.sleep(delaiVisuel);
			} catch (InterruptedException e) {
			}
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
