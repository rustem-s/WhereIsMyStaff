package kz.pio.whereismystuff.common;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener for ShutdownHook registration
 * @version 20140614
 * @author Rustem S
 */
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
        ((AbstractApplicationContext)springContext).registerShutdownHook();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        servletContextEvent.getServletContext().
    }
}
