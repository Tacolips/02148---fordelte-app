package opgave5;

import java.io.IOException;
import org.jspace.*;


public class Philosopher {
	public static void main(String[] args) {
        // parse arguments
        int numPhilosophers = 0;
        int me = 0;
        int port = 31145;
        String host = "localhost";
        if (args.length < 2 || args.length > 4) {
            System.out.println("Wrong number of arguments");
            System.out.println("Usage: java -jar run main.jar <number of philosopers> <my id> [host] [port]");
            return;
        }
        numPhilosophers = Integer.parseInt(args[0]);
        if (numPhilosophers <= 1) {
            System.out.println("Wrong number of philosophers. Must be at least 2.");
            return;
        }
        me = Integer.parseInt(args[1]);
        if (me < 0 || me >= numPhilosophers) {
            System.out.println("Wrong philosopher id. Must be betwen 0 and " + (numPhilosophers - 1));
            return;
        }
        if (args.length >= 3) {
            host = args[2];
        }
        if (args.length >= 4) {
            port = Integer.parseInt(args[3]);
        }
        // connect to tuple space
        try {
            String uri = "tcp://" + host + ":" + port + "/board?conn";
            Space board = new RemoteSpace(uri);
            philosopher(board, me, numPhilosophers);
        } catch (IOException e) {}
    }
    public static void philosopher(Space board, int me, int numPhilosophers) {
        int left = me;
        int right = (me + 1) % numPhilosophers;
        // The philosopher enters his endless life cycle.
        while (true) {
            try {
                // Get a ticket.
                board.get(new ActualField("ticket"));
                Thread.sleep(500);
                // Wait until the left fork is ready (get the corresponding tuple).
                board.get(new ActualField("fork"), new ActualField(left));
                System.out.println("Philosopher " + me + " got left fork");
                // Wait until the right fork is ready (get the corresponding tuple).
                board.get(new ActualField("fork"), new ActualField(right));
                System.out.println("Philosopher " + me + " got right fork");
                // Lunch time.
                System.out.println("Philosopher " + me + " is eating...");
                // Return the forks (put the corresponding tuples).
                board.put("fork", left);
                board.put("fork", right);
                board.put("ticket");
                System.out.println("Philosopher " + me + " put both forks and a ticket on the table");
            } catch (InterruptedException e) {}
        }
    }
}

