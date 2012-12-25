package bebetes;

import java.awt.Color;
import java.awt.Graphics;

import visu.Champ;

public class ChampiRouge extends Champi {

	public static int TAILLEGRAPHIQUE = 16;
	
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
        g.setColor(couleur);
        g.fillOval(x,y,TAILLEGRAPHIQUE,TAILLEGRAPHIQUE);
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
