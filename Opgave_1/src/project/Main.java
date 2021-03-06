package project;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jspace.*;

public class Main {
	public final static String GATE_URI = "tcp://127.0.0.1:9001/?keep";
	public final static String REMOTE_URI = "tcp://127.0.0.1:9001/aspace?keep";	
	
	public static void main(String[] args) {
		SpaceRepository repository = new SpaceRepository();
		repository.addGate(GATE_URI);
		repository.add("aspace", new SequentialSpace());
		
		Thread t1 = new Thread( () -> {//PONG TREAD: get pong from "PONG" and write ping to "PING" 
			try {
				RemoteSpace space = new RemoteSpace(REMOTE_URI);
				space.put("GREETING","Hello");
				System.out.println("T1 Done!");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("T1 Error!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("T1 Error!");
			}			
		});		

		Thread t2 = new Thread( () -> {//PONG TREAD: get pong from "PONG" and write ping to "PING" 
			try {
				RemoteSpace space = new RemoteSpace(REMOTE_URI);
				space.put("NAME","World");
				System.out.println("T2 Done!");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("T2 Error!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("T2 Error!");
			}			
		});		

		Thread t3 = new Thread( () -> {//PONG TREAD: get pong from "PONG" and write ping to "PING" 
			try {				
				RemoteSpace space = new RemoteSpace(REMOTE_URI);
				Object[] greetingData = space.get(new ActualField("GREETING"), new FormalField(String.class));				
				Object[] nameData = space.get(new ActualField("NAME"), new FormalField(String.class));				
				System.out.println(greetingData[1]+" "+nameData[1]+"!");
				System.out.println("T3 Done!");
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("T3 Error!");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("T2 Error!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("T2 Error!");
			}			
		});		
		
		t1.start();
		t2.start();
		t3.start();
	}
	
	
	
}
