package org.Jxpress;

/**
 * Created by Pengfei on 2/11/2017.
 */
public class MatchAndController {

    private final UrlMatcher matcher;
    private final IController controller;

    public MatchAndController(UrlMatcher matcher, IController controller){
        this.controller = controller;
        this.matcher = matcher;
    }

    public UrlMatcher getMatcher(){
        return matcher;
    }

    public IController getController(){
        return controller;
    }

}
