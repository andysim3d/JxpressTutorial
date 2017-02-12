package org.Jxpress;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Pengfei on 2/11/2017.
 */
public class UrlRouter implements Router {
    private Map<String, MatchAndController> getRouters = new LinkedHashMap<String, MatchAndController>();

    private Map<String, MatchAndController> postRouters = new LinkedHashMap<String, MatchAndController>();

    private Map<String, MatchAndController> putRouters = new LinkedHashMap<String, MatchAndController>();

    private Map<String, MatchAndController> deleteRouters = new LinkedHashMap<String, MatchAndController>();

    private List<String> routeRules;

   // private ExceptionHandler handler;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void addController(String url, IController controller, String method) throws Exception
    {
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            if ("get".equalsIgnoreCase(method)){
                routeRules.add(url);
                getRouters.put(url, new MatchAndController(UrlMatcher.compile(url), controller));
            }else if ("post".equalsIgnoreCase(method)){
                routeRules.add(url);
                postRouters.put(url, new MatchAndController(UrlMatcher.compile(url), controller));
            }else if ("put".equalsIgnoreCase(method)){
                routeRules.add(url);
                putRouters.put(url, new MatchAndController(UrlMatcher.compile(url), controller));
            }else if ("delete".equalsIgnoreCase(method)){
                routeRules.add(url);
                deleteRouters.put(url, new MatchAndController(UrlMatcher.compile(url), controller));
            }
            else {
                throw new Exception("not implement http method");
            }
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public IController route(HttpServletRequest request) throws Exception{
        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            if ("get".equalsIgnoreCase(request.getMethod())){
                for (MatchAndController matchAndController : getRouters.values()){
                    if (matchAndController.getMatcher().match(request)){
                        return matchAndController.getController();
                    }
                }
                throw new Exception("router not matched");
            }
            else if ("post".equalsIgnoreCase(request.getMethod())){
                for (MatchAndController matchAndController : postRouters.values()){
                    if (matchAndController.getMatcher().match(request)){
                        return matchAndController.getController();
                    }
                }
                throw new Exception("router not matched");
            }else if ("put".equalsIgnoreCase(request.getMethod())){
                for (MatchAndController matchAndController : putRouters.values()){
                    if (matchAndController.getMatcher().match(request)){
                        return matchAndController.getController();
                    }
                }
                throw new Exception("router not matched");
            }
            else if ("delete".equalsIgnoreCase(request.getMethod())){
                for (MatchAndController matchAndController : deleteRouters.values()){
                    if (matchAndController.getMatcher().match(request)){
                        return matchAndController.getController();
                    }
                }
                throw new Exception("router not matched");
            }
            throw new Exception("not supported Http method");
        }
        finally {
            readLock.unlock();
        }
    }

    public UrlRouter(){
        this.routeRules = new ArrayList<String>();
    }
    @Override
    public List<String> routeRules(){
        return this.routeRules;
    }

}
