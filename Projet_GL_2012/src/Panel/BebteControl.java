package Panel;

import java.util.Observable;
import java.util.Observer;

import bebetes.Bebete;

public class BebteControl implements Observer {
	
	protected static BebteControl _singleton=null;
	private PanelCustom panel;
	
	private BebteControl()
	{
		panel = new PanelCustom("Info");
	}
	
	public static BebteControl getInstance() {
	 	if(_singleton==null){
	  		_singleton= new BebteControl();
	  	}
	  	return _singleton;
	}
	
	public PanelCustom getPanel() {
		return panel;
	}

	public void update(Observable beb, Object arg1) {
		// TODO Auto-generated method stub
		if(beb instanceof Bebete){
			String msg="la bebete a la  position : x = "+((Bebete) beb).getX()+" y: "+((Bebete) beb).getY()+" mort\n";
			panel.addStringTextArea(msg);
		}
		
	}
	
}