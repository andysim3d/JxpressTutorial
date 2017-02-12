package org.Jxpress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Pengfei on 2/9/2017.
 */

@FunctionalInterface
public interface IController {
    // main logic

    void execute(HttpServletRequest request,
                 HttpServletResponse response)
            throws Exception;
}


