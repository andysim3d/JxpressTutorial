package org.Jxpress;

import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) {

        WebServer server = new JettyServer();
        server.get("/", (req, res)->{
            OutputStreamWriter optwriter = new OutputStreamWriter(res.getOutputStream(), "utf-8");
            optwriter.write("Hello You!");
            res.setStatus(200);
            optwriter.flush();
        }).get("/test", (req, res)->{
            OutputStreamWriter optwriter = new OutputStreamWriter(res.getOutputStream(), "utf-8");
            optwriter.write("Hello There!");
            res.setStatus(200);
            optwriter.flush();
        }).
                listen(8080);
        server.start();
    }
}

