package logger;

import org.apache.log4j.Logger;

public class RLog {

    public static Logger getLogger(Class mClass) {
        return Logger.getLogger(mClass.getName());
    }

    public static void debug(Logger logger, String logMessage) {
        if (logger.isDebugEnabled()) {
            logger.debug(logMessage);
        }
    }

    public static void info(Logger logger, String logMessage) {
        if (logger.isInfoEnabled()) {
            logger.info(logMessage);
        }
    }

    public static void error(Logger logger, String logMessage) {
        logger.error(logMessage);
    }

    public static void warn(Logger logger, String logMessage) {
        logger.warn(logMessage);
    }
}
