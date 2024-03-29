/**
 * 
 */
package visu;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * @author  collet
 */
public class Visualisateur extends JPanel implements Champ  {
	
	// volatile = gestion des threads
	// par exemple, largeur et hauteur devraient �tre volatiles si on les modifiait dynamiquement
	// leurs valeurs ne pourraient ainsi pas �tre mis en cache par un thread
	
	protected volatile List<? extends Dessinable> dessinables;
	protected int largeur;
	protected int hauteur;
	
	public Visualisateur(int largeur, int hauteur, List<? extends Dessinable> ld) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		dessinables = ld;
	}
	
	public Visualisateur(int largeur, int hauteur) {
		this(largeur,hauteur,new ArrayList<Dessinable>(0));
	}
	
	public Visualisateur() {
		this(640,480);
	}
	
	/* Impl�mentation de l'interface Champ */
	
	public int getHauteur() {
		return hauteur;
	}

	public int getLargeur() {
		return largeur;
	}
	
	public synchronized List<? extends Positionnable> getPositionnables() {
		return dessinables;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		synchronized (dessinables) {
			for(Dessinable d : dessinables)
				d.seDessine(g);
		}
	}

	public synchronized void setDessinables(List<? extends Dessinable> dessinables) {
		this.dessinables = dessinables;
	}

}
