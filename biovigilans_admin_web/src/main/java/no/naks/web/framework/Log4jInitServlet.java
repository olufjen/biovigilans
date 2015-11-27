package no.naks.web.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Category;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.DriverManager;

/**
 * This allows for dynamic changes in the log4j.xml config file. A separate
 * thread will monitor the config file and reload every 10 sec. This class also
 * sets logging for the DriverManager class so that jdbc-stuff is logged.
 * 
 */

public class Log4jInitServlet extends HttpServlet {

        private static final String CONFIG_FILE = "log4j.xml";

	private static Log logger = LogFactory.getLog(Log4jInitServlet.class);
	private Thread thread = null;

	@Override
	public void destroy() {
		thread.interrupt();
		super.destroy();
	}

	public void init() throws ServletException {
		super.init();
                System.err.println("INFO: Log4jInitServlet.init() called.");
                logger.info("Log4jInitServlet.init() called.");
		initMonitorThread();
		initJdbcLogging();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
	}

	private void initMonitorThread() {
                System.err.println("INFO: Log4jInitServlet.initMonitorThread() called.");
                logger.info("Log4jInitServlet.initMonitorThread() called.");
		if (thread == null) {
                        System.err.println("INFO: Log4jInitServlet.initMonitorThread() creating thread.");
			LogMonitorThread monitorThread = new LogMonitorThread();
			monitorThread.setCheckIntervalMillis(10000);
			monitorThread.setConfigPath(getServletContext().getRealPath("/WEB-INF") + "/" + CONFIG_FILE);
			thread = new Thread(monitorThread);
			thread.start();
		}
	}

	private void initJdbcLogging() {
		DriverManager.setLogWriter(new Log4jPrintWriter(Category.getRoot(), Priority.DEBUG));
	}
}