package bebetes;

import visu.Dessinable;
import fr.unice.plugin.Plugin;

public abstract class Champi implements Dessinable, Plugin {

	// partie propre � la transformation en Plugin

	public String getName() {
		return "champi";
	}

}
