package opgave5;

import org.jspace.*;

public class Waiter {
    public static void main(String[] args) {
        // parse arguments
        int port = 31145;
        int numPhilosophers = 0;
        if (args.length < 1 || args.length > 2) {
            System.out.println("Wrong number of arguments");
            System.out.println("Usage: java -jar run main.jar <number of philosopers> [port]");
            return;
        }
        numPhilosophers = Integer.parseInt(args[0]);
        if (numPhilosophers <= 1) {
            System.out.println("Wrong number of philosophers. Must be at least 2.");
            return;
        }
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }
        // create a repository
        String uri = "tcp://localhost:" + port + "/?conn";
        SpaceRepository repository = new SpaceRepository();
        repository.addGate(uri);
        Space board = new SequentialSpace();
        repository.add("board", board);
        // waiter prepares the board with forks and tickets.
        waiter(board, numPhilosophers);
        // keep the tuple space open
        try {
            board.get(new ActualField("done"));
        } catch (InterruptedException e) {}
    }
    public static void waiter(Space board, int numPhilosophers) {
        System.out.println("Waiter putting forks on the table...");
        try {
            for (int i = 0; i < numPhilosophers; i ++) {
                board.put("fork", i);
                System.out.println("Waiter put fork " + i + " on the table.");
            }
            System.out.println("Waiter putting tickets on the table...");
            for (int i = 0; i < numPhilosophers - 1; i ++) {
                board.put( "ticket");
            }
        } catch (InterruptedException e) {}
        System.out.println("Waiter done.");
    }
}