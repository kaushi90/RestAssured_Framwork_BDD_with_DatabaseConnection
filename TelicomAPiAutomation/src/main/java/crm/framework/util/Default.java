package crm.framework.util;


public enum Default {
    CONFIG_PATH("/config/config.cfg"),
    UI_TYPE("xpath"),
    DRIVER_URL("localhost"),
    DRIVER_HOST("localhost"),
    DRIVER_PORT("4567"),
    OS("Windows 10");

    public static int CONFIG_VAR_DEPTH = 3;
    private String value;

    private Default(String val) {
        this.value = val;
    }

    public String getValue() {
        return this.value;
    }

}
