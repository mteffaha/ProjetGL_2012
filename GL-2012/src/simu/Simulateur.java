package simu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  collet
 */
public class Simulateur implements Runnable {
	
	protected List<? extends Actionnable> actionnables;

	protected int delaiSimulation; // en ms
	
	protected Thread threadSimulation = null;

	public List<? extends Actionnable> getActionnables() {
		return actionnables;
	}	
	
	public void setActionnables(List<? extends Actionnable> l) {
		actionnables = l;
	}
	
	public int getDelaiSimulation() {
		return delaiSimulation;
	}

	public void setDelaiSimulation(int delaiSimu) {
		this.delaiSimulation = delaiSimu;
	}
	
	public void demarre() {
	    threadSimulation = new Thread(this);
	    threadSimulation.start();
	}

	public void arrete() {
		threadSimulation = null; // ceci sera testé dans le run()
	}
	
	public void run() {
	    // Code du thread pour arrêt propre (cf. sun technical tips)
	    Thread currentThread = Thread.currentThread();
	    while (threadSimulation == currentThread) {
	    	synchronized(actionnables) {
	    		for (Actionnable a : actionnables) {
	    			a.agit();	    		
	    		}
	    	}
	        try {
				Thread.sleep(delaiSimulation);
			} catch (InterruptedException e) {
			}
	    }
	}

	public Simulateur() {
		this(20);
	}

	public Simulateur(int delaiSimulation) {
		this(delaiSimulation, new ArrayList<Actionnable>(0));
	}

	public Simulateur(int delaiSimulation, List<? extends Actionnable> l) {
		this.delaiSimulation = delaiSimulation;
		setActionnables(l);
	}
	
}
