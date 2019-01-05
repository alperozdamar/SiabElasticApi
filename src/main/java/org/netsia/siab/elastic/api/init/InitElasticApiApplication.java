package org.netsia.siab.elastic.api.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.netsia.siab.elastic.api.servlet.SearchServlet;
import org.netsia.siab.elastic.config.ConfigurationManager;

/**
 * Netsia
 * 
 * @author alperoz
 *
 */
public class InitElasticApiApplication {
	
	
	public static final int VERSION = 1;

	  private static Logger logger = LogManager.getLogger(InitElasticApiApplication.class);

	    public static void main(String[] args) {
	        try {


	            // Create a basic jetty server object that will listen on port 8080.
	            // Note that if you set this to port 0 then a randomly available port
	            // will be assigned that you can either look in the logs for the port,
	            // or programmatically obtain it for use in test cases.
	            Server server = new Server(ConfigurationManager.getInstance().getServerPort()); //8080

	            //create a ServletHander to attach servlets
	            ServletContextHandler servhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
	            servhandler.addServlet(SearchServlet.class, "/search");


	            server.setHandler(servhandler);

	            // Start things up!
	            server.start();

			logger.debug("Server is started! Version:" + VERSION);

	            // The use of server.join() the will make the current thread join and
	            // wait until the server is done executing.
	            // See
	            // http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
	            server.join();

	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
}
