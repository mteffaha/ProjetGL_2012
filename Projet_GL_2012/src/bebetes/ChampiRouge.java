package bebetes;

import java.awt.Color;
import java.awt.Graphics;


import visu.Champ;

public class ChampiRouge extends Champi {

	public static int TAILLEGRAPHIQUE = 16;
	
	public static int diametre=20;
	
    protected ChampDeBebetes champ;   
    protected int x, y;
    protected Color couleur = Color.RED;

    protected ChampiRouge(ChampDeBebetes c, int x, int y) {
        champ = c;
        this.x = x;
        this.y = y;
    }

    protected ChampiRouge(ChampDeBebetes c, int x, int y, Color col) {
        this(c,x,y);
        couleur = col;
    }
    
	public Color getCouleur() {
		return couleur; 
	}

	public void seDessine(Graphics g) {
		if(visibilite){
			int r=diametre/2;
	        g.setColor(couleur);
	        g.fillOval(x,y,TAILLEGRAPHIQUE,TAILLEGRAPHIQUE);
	        g.drawOval((x-r)+TAILLEGRAPHIQUE/2, (y-r)+TAILLEGRAPHIQUE/2, 2*r, 2*r);
		}
	}

	public Champ getChamp() {
		return champ;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}



}
