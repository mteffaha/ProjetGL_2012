package util;

import visu.Positionnable;

/* Utilitaires pour des calculs de distance et de direction */

public class DistancesEtDirections {

	public static float distanceDepuisUnPoint(int x0, int y0, int x1, int y1) {
		// rend la distance entre la bébête et un point donné
		return (float) Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
	}
	
	public static float directionDepuisUnPoint(Positionnable p, int xOrigine, int yOrigine, float axe) {
		// Rends l'angle en radians (entre ]-PI, PI[) de l'objet positionnable p
		// par rapport à un point origine et à l'axe donné
		// tan x = coté opposé / coté adjacent
		float angle;
		int x = p.getX();
		int y = p.getY();
		if (x != xOrigine)
			angle = (float) Math.atan((y - yOrigine) / (x - xOrigine));
		else if (y < yOrigine)
			angle = -(float) (Math.PI / 2);
		else
			angle = (float) (Math.PI / 2);
		// atan a deux solutions, donc il faut corriger...
		if (x < xOrigine) {
			if (y < yOrigine)
				angle -= (float) Math.PI;
			else
				angle += (float) Math.PI;
		}
		// Il suffit maintenant de soustraire l'axe à la direction (en radians)
		// et obtenir une direction entre -PI et PI
		float dir = angle - axe;

		if (dir >= (float) Math.PI)
			return dir - (float) (Math.PI * 2);
		else if (dir < -(float) Math.PI)
			return dir + (float) (Math.PI * 2);
		else
			return dir;
	}
}
