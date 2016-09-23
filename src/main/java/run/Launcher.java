package run;

import data.InMemoryDatabase;
import http.Server;

public class Launcher {

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt( args[ 0 ] );
        Server server = new Server(port);
        server.useDatabase(InMemoryDatabase.with("7 pacemaker"));
        server.start();
    }
}
