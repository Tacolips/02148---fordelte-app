package shooter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jspace.RemoteSpace;
import org.jspace.Space;

public class LoginMenu extends JPanel {
private static final long serialVersionUID = 1L;
private Space board;
	
	private static Main main;
	
	public LoginMenu(String[] in) {
		setBounds(0, 0, 1280, 720);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		
		 // connect to tuple space
		int port = Integer.parseInt(in[0]);
		String host = in[1];
        try {
           String uri = "tcp://" + host + ":" + port + "/board?conn";
           board = new RemoteSpace(uri);
        } catch (IOException e) {}
		
		
        
		JButton csButton = new JButton("Hero Selection");
		csButton.setBounds(590, 250, 120, 25);
		add(csButton);
		
		csButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				board.put("done");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}});
		

	}
	
	
	

}
