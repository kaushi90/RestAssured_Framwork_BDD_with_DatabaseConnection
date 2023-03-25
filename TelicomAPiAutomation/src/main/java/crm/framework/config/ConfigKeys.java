package crm.framework.config;


public enum ConfigKeys {
    KEY_DRIVER_URL("driverurl"),
    KEY_BROWSER("browser"),
    KEY_ENVIRONMENT("environment"),
    KEY_START_PAGE_URL("startpageurl"),
    KEY_TEMPLATE_PATH("templatepath"),
    KEY_DATA_WORKBOOK_PATH("dataworkbookpath"),
    KEY_SCREENSHOT_PATH("screenshotdirectory"),
    KEY_START_PAGE_CLASS("startpageclass"),
    KEY_WAIT_TIMEOUT("waittimeout"),
    KEY_VERIFY_TIMEOUT("verifytimeout"),
    KEY_VERIFY_INTERVAL("verifyinterval"),
    KEY_ELEMENT_TIMEOUT("elementtimeout"),
    KEY_ELEMENTS_TIMEOUT("elementstimeout"),
    KEY_PAGELOAD_TIMEOUT("pageloadtimeout"),
    KEY_DBUSERNAME("dbUser"),
    KEY_DBUSERPASSWORD("dbPassword"),
    KEY_DBURL("dbURL"),
    KEY_DEFAULT_APPLICATION_USER("defaultUser"),
    KEY_DEFAULT_APPLICATION_PASSWORD("defaultPassword"),
    KEY_HOME_PAGE_URL("homePageUrl"),
    PROJECT_BASE_DIR("projects.base.dir"),
    KEY_DBUSERNAME2("dbUser1"),
    KEY_DBUSERPASSWORD2("dbPassword1"),
    KEY_DBURL2("dbURL1");

    String key;

    private ConfigKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
