package shooter;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jspace.*;

public class Main extends JFrame {
private static final long serialVersionUID = 1L;	
//public static final int port = 31146;
//public static final String host = "localhost";
public static final int WIDTH = 1250, HEIGHT = 800;
public static final String[] connect = {"31145","localhost"};
	private CardLayout cardLayout;
	private LoginMenu login;
	private JPanel content;
	
	public String loginKey = "login";
	
	public Main(String[] in) { // GUI klassen
		super("V 0.1");
		String[] onCreate = {in[0], connect[0], connect[1]};
		cardLayout = new CardLayout();
		login = new LoginMenu(onCreate);
		content = new JPanel(cardLayout);
		content.add(login, loginKey);
		cardLayout.show(content,loginKey);
		

		
		
		this.add(content);
		this.setBounds(100, 100, 1285, 753);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main(args);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showCard(String key) {
		cardLayout.show(content, key);
	}
	
	
}
		
	
	

