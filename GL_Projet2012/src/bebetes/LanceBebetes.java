package bebetes;

/**
 *
 * @author  collet
 * @version
 */

import javax.swing.*;
import java.awt.*;

public class LanceBebetes extends JFrame {

    public LanceBebetes() {
        ChampDeBebetes champ = new ChampDeBebetes(640,480,200);
        getContentPane().add(champ, BorderLayout.CENTER);
        setName("Champ de bébêtes");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        pack ();
        // démarrage de tout à la fois !
        champ.demarre();
    }

    private void exitForm(java.awt.event.WindowEvent evt) {
        System.exit (0);
    }

    public static void main (String args[]) {
        new LanceBebetes().setVisible(true);
    }

}
