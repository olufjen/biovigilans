package no.naks.web.framework;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class LogMonitorThread implements Runnable {

    private static Logger log = Logger.getLogger(LogMonitorThread.class);
    boolean interruped;
    private long checkIntervalMillis = 10000;
    private File file;
    private long lastModified = 0;
    private String configPath = null;

    public void run() {
        System.err.println("INFO: LogMonitorThread.run() called.");
        log.info("LogMonitorThread.run() called.");

        file = new File(getConfigPath());

        if (!file.exists()) {
            log.warn("log4j configuration file " + getConfigPath() + " not found.");
            System.err.println("log4j configuration file " + getConfigPath() + " not found.");
        } else if (!file.canRead()) {
            log.warn("log4j configuration file " + getConfigPath() + " found but not readable.");
            System.err.println("log4j configuration file " + getConfigPath() + " found but not readable.");
        } else {
            log.info("log4j configuration file " + getConfigPath() + " found, starting monitoring and reading.");
            System.err.println("log4j configuration file " + getConfigPath() + " found, starting monitoring and reading.");
            lastModified = file.lastModified();
            monitor();
        }
        System.err.println("INFO: LogMonitorThread.run() exiting.");
    }

    private void monitor() {
        System.err.println("INFO: LogMonitorThread.monitor() called.");
        log.info("Starting log4j monitor.");

        while (!interruped) {

            long temp = file.lastModified();
            if (lastModified != temp) {
                log.info("Initialize log4j configuration " + getConfigPath() + ".");
                System.err.println("Initialize log4j configuration " + getConfigPath() + ".");
                DOMConfigurator.configure(getConfigPath());
                lastModified = temp;
            } else {
                log.debug("log4j configuration " + getConfigPath() + " is not modified.");
                System.err.println("log4j configuration " + getConfigPath() + " is not modified.");
            }

            try {
                Thread.currentThread().sleep(checkIntervalMillis);
            } catch (InterruptedException e) {
                interruped = true;
            }
        }
        log.info("Shutting down log4j monitor.");
        System.err.println("INFO: LogMonitorThread.monitor() exiting.");
    }

    public long getCheckIntervalMillis() {
        return checkIntervalMillis;
    }

    public void setCheckIntervalMillis(long checkIntervalMillis) {
        this.checkIntervalMillis = checkIntervalMillis;
    }

    public boolean isInterruped() {
        return interruped;
    }

    public void setInterruped(boolean interruped) {
        this.interruped = interruped;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
}
