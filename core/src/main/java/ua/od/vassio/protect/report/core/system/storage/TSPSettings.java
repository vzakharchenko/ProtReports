package ua.od.vassio.protect.report.core.system.storage;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 20.10.14
 * Time: 9:18
 */
public class TSPSettings {
    private boolean getStamps;
    private String address;
    private String port;

    public boolean isGetStamps() {
        return getStamps;
    }

    public void setGetStamps(boolean getStamps) {
        this.getStamps = getStamps;
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
