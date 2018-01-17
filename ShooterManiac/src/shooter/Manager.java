package shooter;

import org.jspace.*;

public class Manager implements Runnable {
public static Space board;
	public Manager(Space space) {
		this.board = space;
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			
		} catch (Exception e) {}			
	}
	
	public static Space getBoard() {
		return board;
	}
	
}
