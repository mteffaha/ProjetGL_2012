package m1.gl.projet2012.visu;

/**
 * @author  collet
 */
public interface Dirigeable extends Positionnable {

	public float getVitesseCourante(); // vitesse >= 0

	public void setVitesseCourante(float vitesseCourante);

	public float getDirectionCourante(); // direction en radians [0,2PI[

	public void setDirectionCourante(float directionCourante);
	
}
