package org.Jxpress;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pengfei on 2/9/2017.
 */
public class RegexMatcher extends UrlMatcher {

    String regex;
    private Pattern pattern;
    // used to match pathvariables
    private List<String> pathVariables;

    private static Pattern variablesPattern = Pattern.compile("\\$\\{(\\w+)\\}");

    public static RegexMatcher compile(String url){
        Matcher matcher = variablesPattern.matcher(url);
        StringBuffer accum = new StringBuffer();
        List<String> pathVariables = new ArrayList<String>();
        while (matcher.find()){
            pathVariables.add(matcher.group(1));
            matcher.appendReplacement(accum, "([^/]+)");
        }
        matcher.appendTail(accum);
        return new RegexMatcher(Pattern.compile(accum.toString()), pathVariables);
    }

    public RegexMatcher (Pattern pattern, List<String> pathVariables){
        this.pattern = pattern;
        this.pathVariables = pathVariables;
    }

    @Override
    public boolean match(HttpServletRequest request) {
        Matcher matcher = pattern.matcher(request.getRequestURI());
        if (matcher.matches()){
            if (pathVariables != null && pathVariables.size() > 0){
                for (int i = 0; i < matcher.groupCount(); ++i){
                    request.setAttribute(pathVariables.get(i), matcher.group(i + 1));
                }
            }
            return true;
        }
        return false;
    }




    @Override
    public String toString() {
        return "RegexMatcher{" +
                "regex='" + regex + '\'' +
                ", pattern=" + pattern +
                ", pathVariables=" + pathVariables +
                '}';
    }

    public Pattern getPattern(){
        return pattern;
    }

    public List<String> getPathVariables(){
        return  pathVariables;
    }


}
