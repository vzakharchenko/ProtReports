package ua.od.vassio.protect.report.core.system.storage;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 20.10.14
 * Time: 9:18
 */
public class OCSPSettings {
    private boolean useOCSP;
    private boolean beforeStore;
    private String address;
    private String port;

    public boolean isUseOCSP() {
        return useOCSP;
    }

    public void setUseOCSP(boolean useOCSP) {
        this.useOCSP = useOCSP;
    }

    public boolean isBeforeStore() {
        return beforeStore;
    }

    public void setBeforeStore(boolean beforeStore) {
        this.beforeStore = beforeStore;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
