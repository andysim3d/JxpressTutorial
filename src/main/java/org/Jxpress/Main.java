package org.Jxpress;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WebServer server = new JettyServer();
        server.get("/").get("/introduce").listen(8080);
        server.start();
    }
}
