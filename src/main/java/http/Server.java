package http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import data.Database;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Server {

    private int port;
    private HttpServer server;
    private Database database;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = HttpServer.create( new InetSocketAddress( port ), 0 );
        server.createContext( "/", routeToHtml("index.html"));
        server.createContext( "/search", routeToHtml("search.html"));
        server.createContext( "/main.css", routeToCss("main.css"));
        server.createContext( "/execute-search.js", routeToJavascript("execute-search.js"));
        server.createContext( "/jquery-1.9.0.min.js", routeToJavascript("jquery-1.9.0.min.js"));
        server.createContext( "/hospital.jpg", routeToJpeg("hospital.jpg"));
        server.createContext( "/me.png", routeToPng("image/png"));

        server.createContext( "/products", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String body = database.findProduct(query.split("=")[1]);
            exchange.getResponseHeaders().add( "content-type", "text/plain" );
            exchange.sendResponseHeaders( 200, body.length() );
            exchange.getResponseBody().write( body.getBytes() );
            exchange.close();
        });

        server.start();
    }

    private HttpHandler routeToPng(String pngFile) {
        return exchange -> {
            byte[] body = Files.readAllBytes(Paths.get("build", "resources", "main", "me.png"));
            exchange.getResponseHeaders().add( "content-type", pngFile);
            exchange.sendResponseHeaders( 200, body.length );
            exchange.getResponseBody().write( body );
            exchange.close();
        };
    }

    private HttpHandler routeToJpeg(String jpegFile) {
        return exchange -> {
            byte[] body = Files.readAllBytes(Paths.get("build", "resources", "main", jpegFile));
            exchange.getResponseHeaders().add( "content-type", "image/jpeg" );
            exchange.sendResponseHeaders( 200, body.length );
            exchange.getResponseBody().write( body );
            exchange.close();
        };
    }

    private HttpHandler routeToJavascript(String javascriptFile) {
        return exchange -> {
            String body = Files.readAllLines(Paths.get("build", "resources", "main", javascriptFile)).stream().collect(Collectors.joining("\n"));
            exchange.getResponseHeaders().add( "content-type", "application/javascript" );
            exchange.sendResponseHeaders( 200, body.length() );
            exchange.getResponseBody().write( body.getBytes() );
            exchange.close();
        };
    }

    private HttpHandler routeToCss(String cssFile) {
        return exchange -> {
            String body = Files.readAllLines(Paths.get("build", "resources", "main", cssFile)).stream().collect(Collectors.joining("\n"));
            exchange.getResponseHeaders().add( "content-type", "text/css" );
            exchange.sendResponseHeaders( 200, body.length() );
            exchange.getResponseBody().write( body.getBytes() );
            exchange.close();
        };
    }

    private HttpHandler routeToHtml(String htmlFile) {
        return exchange -> {
            String body = Files.readAllLines(Paths.get("build", "resources", "main", htmlFile)).stream().collect(Collectors.joining("\n"));
            exchange.getResponseHeaders().add( "content-type", "text/html" );
            exchange.sendResponseHeaders( 200, body.length() );
            exchange.getResponseBody().write( body.getBytes() );
            exchange.close();
        };
    }

    public void stop() {
        server.stop(0);
    }

    public void useDatabase(Database database) {
        this.database = database;
    }
}
