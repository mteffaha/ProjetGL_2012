/**
 * 
 */
package visu;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author collet
 *
 */
public interface Dessinable extends Positionnable {

	public static int TAILLEGRAPHIQUE = 25;
	
	public Color getCouleur();

	public void seDessine(Graphics g);
	
}
