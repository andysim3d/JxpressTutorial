package org.Jxpress;


/**
 * Created by Pengfei on 2/3/2017.
 */
public interface WebServer {
    /**init Web server
     *
     * @param port listen number
     * @return webserver instance
     */
    WebServer listen(int port);

    /**
     * process get method
     * @param url entire url
     * @return webserver instance
     */
    WebServer get(String url, IController ctl);

    /**
     * control web server running
     * @return
     */
    WebServer stop();

    /**
     * stop the web server
     * @return
     */
    WebServer start();

}
