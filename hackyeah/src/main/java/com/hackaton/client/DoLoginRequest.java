package com.hackaton.client;

class DoLoginRequest {
    private String userLogin;
    private String userPassword;
    private int countryCode;
    private long localVersion;
    private String webapiKey;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public long getLocalVersion() {
        return localVersion;
    }

    public void setLocalVersion(long localVersion) {
        this.localVersion = localVersion;
    }

    public String getWebapiKey() {
        return webapiKey;
    }

    public void setWebapiKey(String webapiKey) {
        this.webapiKey = webapiKey;
    }
}
