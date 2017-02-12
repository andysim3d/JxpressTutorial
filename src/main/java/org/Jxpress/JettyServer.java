package org.Jxpress;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pengfei on 2/3/2017.
 */

public class JettyServer implements WebServer {
    private Server server;

    protected int port = 80;

    protected UrlRouter urlRouter = new UrlRouter();


    public WebServer listen(int port) {
        this.port = port;
        return null;
    }

    public WebServer get(String url, IController ctl) {
        try {
            urlRouter.addController(url,ctl,"get");
            return this;
        }
        catch (Exception exp){
            // do nothing
        }finally {
            return  this;
        }

    }

    public WebServer stop() {
        // 如果server对象已经存在
        if ( server != null){
            try{
                server.stop();
            }
            catch (Exception e){
                // do nothing
            }
        }
        return this;
    }

    public WebServer start() {
        server = new Server(this.port);
        Handler hdlr = new WebServerHandler(urlRouter);
        server.setHandler(hdlr);
        try{
            server.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    private static class WebServerHandler extends AbstractHandler {

        private UrlRouter urlRouter;
        public WebServerHandler(UrlRouter urlRouter){
            this.urlRouter = urlRouter;
        }
        public void handle(String s, Request request,
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse)
                throws IOException, ServletException {
            try{
                process(httpServletRequest, httpServletResponse);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


        private void process(HttpServletRequest request,
                             HttpServletResponse response)
                throws IOException {
            try {
                urlRouter.route(request).
                        execute(request, response);;
            }
            catch(Exception e){

            }

        }


    }

}
