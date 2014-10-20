package ua.od.vassio.protect.report.core.system.storage;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 20.10.14
 * Time: 9:13
 */
public class FileStorage {
    private String path;
    private boolean checkCRLs;
    private boolean autoRefresh;
    private boolean ownCRLsOnly;
    private boolean fullAndDeltaCRLs;
    private boolean autoDownloadCRLs;
    private boolean saveLoadedCerts;
    private int expireTime;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isCheckCRLs() {
        return checkCRLs;
    }

    public void setCheckCRLs(boolean checkCRLs) {
        this.checkCRLs = checkCRLs;
    }

    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public boolean isOwnCRLsOnly() {
        return ownCRLsOnly;
    }

    public void setOwnCRLsOnly(boolean ownCRLsOnly) {
        this.ownCRLsOnly = ownCRLsOnly;
    }

    public boolean isFullAndDeltaCRLs() {
        return fullAndDeltaCRLs;
    }

    public void setFullAndDeltaCRLs(boolean fullAndDeltaCRLs) {
        this.fullAndDeltaCRLs = fullAndDeltaCRLs;
    }

    public boolean isAutoDownloadCRLs() {
        return autoDownloadCRLs;
    }

    public void setAutoDownloadCRLs(boolean autoDownloadCRLs) {
        this.autoDownloadCRLs = autoDownloadCRLs;
    }

    public boolean isSaveLoadedCerts() {
        return saveLoadedCerts;
    }

    public void setSaveLoadedCerts(boolean saveLoadedCerts) {
        this.saveLoadedCerts = saveLoadedCerts;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
