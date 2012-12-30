package bebetes;

import visu.Dessinable;
import fr.unice.plugin.Plugin;

public abstract class Champi implements Dessinable, Plugin {

	// partie propre � la transformation en Plugin

	protected boolean visibilite=true;
	
	public String getName() {
		return "champi";
	}

	public boolean isVisibile() {
		return visibilite;
	}

	public void setVisible(boolean visibilite) {
		this.visibilite = visibilite;
	}


}
