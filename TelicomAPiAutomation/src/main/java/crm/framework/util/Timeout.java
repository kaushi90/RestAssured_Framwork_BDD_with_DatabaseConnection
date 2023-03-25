package crm.framework.util;

public enum Timeout {
    WAIT_TIMEOUT(60000L),
    VERIFY_TIMEOUT(60000L),
    VERIFY_INTERVAL(0L),
    ELEMENT_TIMEOUT(3000L),
    ELEMENTS_TIMEOUT(3000L),
    PAGELOAD_TIMEOUT(0L),
    SLEEP_HALF_SEC(500L),
    SLEEP_1_SEC(1000L),
    SLEEP_2_SEC(2000L),
    SLEEP_5_SEC(5000L),
    SLEEP_10_SEC(10000L),
    SLEEP_20_SEC(20000L),
    SLEEP_30_SEC(30000L),
    SLEEP_1_MIN(60000L),
    SLEEP_2_MIN(120000L),
    SLEEP_5_MIN(300000L);

    long value;

    private Timeout(long timeout) {
        this.value = timeout;
    }

    public long getValue() {
        return this.value;
    }
}
