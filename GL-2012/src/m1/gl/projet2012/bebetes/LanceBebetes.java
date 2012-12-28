package m1.gl.projet2012.bebetes;

/**
 *
 * @author  collet
 * @version
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class LanceBebetes extends JFrame implements ActionListener {

	private boolean IsLanched=true;
	private ChampDeBebetes champ;
	
    public LanceBebetes() {
        champ = new ChampDeBebetes(640,480,200);
        getContentPane().add(champ, BorderLayout.CENTER);
        this.setJMenuBar(MenuBar());
        setName("Champ de bébétes");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        pack ();
        // d�marrage de tout � la fois !
        champ.demarre();
    }
    public JMenuBar MenuBar() {
    	//Create the menu bar
    	JMenu menuOpt,menuSimu;
    	JMenuItem menuItem,menuStart,menuEnd,exit,newSim;
    	JMenuBar menuBar;
    	
    	menuBar = new JMenuBar();
    	//Build the first menu.    	
    	menuSimu = new JMenu("Simulation");
    	menuSimu.setMnemonic(KeyEvent.VK_S);
    	menuBar.add(menuSimu);
    	
    	menuOpt = new JMenu("Options");
    	menuOpt.setMnemonic(KeyEvent.VK_O);
    	menuBar.add(menuOpt);
    	
    	newSim= new JMenuItem("Nouveau",
                KeyEvent.VK_T);
    	newSim.addActionListener(this);
    	newSim.setAccelerator(KeyStroke.getKeyStroke(
    			KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    	menuSimu.add(newSim);
    	
    	menuSimu.addSeparator();
    	
    	menuStart = new JMenuItem("Demarre",
                KeyEvent.VK_T);
    	menuStart.addActionListener(this);
    	menuStart.setAccelerator(KeyStroke.getKeyStroke(
    			KeyEvent.VK_D, ActionEvent.CTRL_MASK));
    	menuSimu.add(menuStart);
    	
    	menuEnd = new JMenuItem("Stop",
                KeyEvent.VK_S);
    	menuEnd.addActionListener(this);
    	menuEnd.setAccelerator(KeyStroke.getKeyStroke(
    			KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    	menuSimu .add(menuEnd);
    	
    	//a group of JMenuItems
    	menuItem = new JMenuItem("Paramétre",
    	                         KeyEvent.VK_T);
    	menuItem.addActionListener(this);
    	menuItem.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_P, ActionEvent.CTRL_MASK));

    	menuOpt.add(menuItem);
    	
    	menuSimu.addSeparator();
    	
    	exit = new JMenuItem("Exit",
                KeyEvent.VK_ESCAPE);
    	
    	menuSimu .add(exit);
    	exit.addActionListener(this);
    	return menuBar;
    }
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
		if(action.equals("Demarre")){
				IsLanched=true;
				champ.demarre();
		}else{
			if(action.equals("Stop")){
				IsLanched=false;
				champ.arrete();
			}else{
				if(action.equals("Paramétre")){
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {

							UIManager.put("swing.boldMetal", Boolean.FALSE);
							new configSimu();
						}
					});
				}else{
					if(action.equals("exit")){
						System.exit(0);
					}else{
						this.setVisible(false);
						 new LanceBebetes().setVisible(true);
					}
					
				}
			}
		}
		
	}

    private void exitForm(java.awt.event.WindowEvent evt) {
        System.exit (0);
    }

    public static void main (String args[]) {
        new LanceBebetes().setVisible(true);
    }

}
