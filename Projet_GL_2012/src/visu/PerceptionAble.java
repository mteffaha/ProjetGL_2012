package visu;

import java.util.List;

/**
 * @author  collet
 */
public interface PerceptionAble extends Dirigeable {
	
	public abstract float getChampDeVue();
	
	public abstract int getLongueurDeVue();

	public List<Positionnable> getChosesVues();
	
}
