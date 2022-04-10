package in.co.srdt.myguruji.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpSessionConfig {
    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public List<HttpSession> getActiveSessions() {
        return new ArrayList<>(sessions.values());
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent hse) {
                sessions.put(hse.getSession().getId(), hse.getSession());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent hse) {
                sessions.remove(hse.getSession().getId());
            }
        };
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer(){
        return (tomcat) -> tomcat.addConnectorCustomizers((connector) -> {
            if(connector.getProtocolHandler() instanceof AbstractHttp11Protocol){
                AbstractHttp11Protocol<?> protocolHandler = (AbstractHttp11Protocol<?>) connector
                        .getProtocolHandler();
                protocolHandler.setDisableUploadTimeout(false);
                protocolHandler.setConnectionUploadTimeout(1200000);
            }
        });
    }
}
