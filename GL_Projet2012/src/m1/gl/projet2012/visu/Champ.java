/**
 * 
 */
package m1.gl.projet2012.visu;

import java.util.List;

/**
 * @author collet
 *
 */
public interface Champ {
	
	public int getLargeur();
	
	public int getHauteur();
	
	public List<? extends Positionnable> getPositionnables();

}
