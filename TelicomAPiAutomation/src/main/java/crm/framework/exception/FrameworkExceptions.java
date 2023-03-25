package crm.framework.exception;


import org.apache.log4j.Logger;

public class FrameworkExceptions extends RuntimeException {

    private static Logger logger = Logger.getLogger(FrameworkExceptions.class);

    public FrameworkExceptions(String message) {
        super(message);
        logger.error(message);
    }

    public FrameworkExceptions(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
