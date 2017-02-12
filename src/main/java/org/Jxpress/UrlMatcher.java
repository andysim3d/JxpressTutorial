package org.Jxpress;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

/**
 * Created by Pengfei on 2/9/2017.
 */
public abstract class UrlMatcher {
    public abstract boolean match(HttpServletRequest request);

    public static UrlMatcher compile(String url){
        return RegexMatcher.compile(url);
    }
}
