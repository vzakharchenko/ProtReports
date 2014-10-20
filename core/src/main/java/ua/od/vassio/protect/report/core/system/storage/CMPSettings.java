package ua.od.vassio.protect.report.core.system.storage;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 20.10.14
 * Time: 9:20
 */
public class CMPSettings {
    private boolean useCMP;
    private String address;
    private String port;
    private String commonName;

    public boolean isUseCMP() {
        return useCMP;
    }

    public void setUseCMP(boolean useCMP) {
        this.useCMP = useCMP;
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

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}
