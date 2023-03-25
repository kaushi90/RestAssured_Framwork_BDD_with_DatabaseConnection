package crm.framework.exception;


public class ConfigFileException extends FrameworkExceptions{

    private static String exMessage = "Config file exception: ";

    public ConfigFileException(String path) {
        super(exMessage + path);
    }

    public ConfigFileException(String path, Throwable cause) {
        super(exMessage + path, cause);
    }
}
