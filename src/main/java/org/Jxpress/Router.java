package org.Jxpress;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Pengfei on 2/9/2017.
 */
public interface Router {

    void addController(String url, IController controller, String method)
            throws Exception ;

    IController route(HttpServletRequest request)
            throws Exception;

    List<String> routeRules();

}
