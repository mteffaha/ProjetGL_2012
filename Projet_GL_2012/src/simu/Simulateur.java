package simu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  collet
 */
public class Simulateur implements Runnable {
	
	// volatile = gestion des threads
	
	protected volatile List<? extends Actionnable> actionnables; 
	private volatile int delaiSimulation; // en ms 
	private volatile Thread threadSimulation = null;

	public synchronized List<? extends Actionnable> getActionnables() {
		return actionnables;
	}	
	
	public synchronized void setActionnables(List<? extends Actionnable> l) {
		actionnables = l;
	}
	
	public synchronized int getDelaiSimulation() {
		return delaiSimulation;
	}

	public synchronized void setDelaiSimulation(int delaiSimu) {
		this.delaiSimulation = delaiSimu;
	}
	
	public void demarre() {
	    threadSimulation = new Thread(this);
	    threadSimulation.start();
	}

	public void arrete() {
		threadSimulation.interrupt();
	}
	
	public void run() {
		threadSimulation = Thread.currentThread();
	        try
	        {
	            while (! threadSimulation.isInterrupted()) {
	    	    	synchronized(actionnables) {
	    	    		for (Actionnable a : actionnables) {
	    	    			a.agit();	    		
	    	    		}
	    	    	}
	                Thread.sleep (delaiSimulation);
	            }
	        }
	        catch (InterruptedException exception){}
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
