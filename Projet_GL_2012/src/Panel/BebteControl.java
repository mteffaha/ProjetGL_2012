package Panel;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import bebetes.Bebete;



public class BebteControl implements Observer {
	
	private static JFrame frame;
	private static JTextArea bmortes;	 
	public void ShowInfosPanel() {
		
		frame = new JFrame("Infos");
		
		JPanel cadre = new JPanel();
		cadre.setLayout(new BorderLayout());
		cadre.add(Panelinfo(), BorderLayout.CENTER);
		
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(cadre);
		frame.pack();
		frame.setLocation(400, 300);
		frame.setVisible(true);
		// on met linstance a nuul pour pouvoir creee dautre fenetre si est eté fermé

	
	}
	
	
	public  JPanel Panelinfo(){
		JPanel info = new JPanel();
		bmortes = new JTextArea (20,30);
		 JScrollPane scrollPane = new JScrollPane(bmortes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 bmortes.setEditable(false);
		info.add(scrollPane);
		return info;
	}

	public void update(Observable beb, Object arg1) {
		// TODO Auto-generated method stub
		if(beb instanceof Bebete){
			String msg="la bebete a la  position : x = "+((Bebete) beb).getX()+" y: "+((Bebete) beb).getY()+" mort\n";
			try {
				bmortes.getDocument().insertString(bmortes.getCaretPosition(), msg, null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
