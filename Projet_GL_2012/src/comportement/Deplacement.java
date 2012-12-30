package comportement;

import bebetes.Bebete;
import bebetes.BebeteAvecComportement;

public interface Deplacement {

	public void calculeDeplacementAFaire(BebeteAvecComportement cl);
	public void  effectueDeplacement(BebeteAvecComportement cl) ;
}
