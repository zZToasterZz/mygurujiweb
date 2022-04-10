package in.co.srdt.myguruji.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import in.co.srdt.myguruji.config.HttpSessionConfig;

@Controller
public class SessionCounterController implements HttpSessionListener {

    /*private List<String> sessions = new ArrayList<>();
    private static final String COUNTER =  "session-counter";*/

    @Autowired
    protected HttpSessionConfig appses;
    protected int log=0;

    /*public void sessionCreated(HttpSessionEvent event){
        System.out.println("SessionCounter.sessionCreated");
        HttpSession session = event.getSession();
        sessions.add(session.getId());
        session.setAttribute(SessionCounterController.COUNTER, this);
    }

    public void sessionDestroyed(HttpSessionEvent event){
        System.out.println("SessionCounter.sessionDestroyed");
        HttpSession session = event.getSession();
        sessions.remove(session.getId());
        session.setAttribute(SessionCounterController.COUNTER, this);
    }

    @ResponseBody
    @RequestMapping("/getSessionCount")
    public String getActiveSessions(){
        return ("<h1 style='text-align: center; margin-top: 50px;'>Total Active Sessions: <span style='color: blue;'>" + sessions.size() + "</span></h1>");
    }*/

    @ResponseBody
    @RequestMapping("/getSessionCount")
    public Map<String, Integer> getActiveSessions(){
        log=0;
        Map<String, Integer> ask = new HashMap<>();

        List<HttpSession> sessions = appses.getActiveSessions();
        ask.put("totalsession", sessions.size());

        sessions.stream().forEach(x->{
            if(x.getAttribute("login") != null){
                log++;
            }
        });
        ask.put("logingsession", log);
        return ask;
    }

}
