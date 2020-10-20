package pers.zdl1004.SchoolLeaveSystem.adapter;

import java.security.KeyPair;

import javax.servlet.http.HttpSession;

import pers.zdl1004.SchoolLeaveSystem.pojo.User;

public class HttpSessionAdapter {
    private static final String USER_KEY_NAME = "user";
    private static final String RSA_KEY_PAIR_KEY_NAME = "rsaKeyPair";
    private static final String RSA_PUBLIC_KEY_NAME = "rsaPublicKey";
    private static final String RSA_CREATE_TIMESTAMP_KEY_NAME = "rsaCreateTimestamp";
    private HttpSession session;

    public HttpSessionAdapter(HttpSession session) {
        this.session = session;
    }

    public User getUser() {
        return (User) session.getAttribute(USER_KEY_NAME);
    }

    public void setUser(User user) {
        session.setAttribute(USER_KEY_NAME, user);
    }

    public KeyPair getRsaKeyPair() {
        return (KeyPair) session.getAttribute(RSA_KEY_PAIR_KEY_NAME);
    }

    public void setRsaKeyPair(KeyPair keyPair) {
        session.setAttribute(RSA_KEY_PAIR_KEY_NAME, keyPair);
    }

    public String getRsaPublicKey() {
        return (String) session.getAttribute(RSA_PUBLIC_KEY_NAME);
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        session.setAttribute(RSA_PUBLIC_KEY_NAME, rsaPublicKey);
    }

    public long getRsaCreateTimestamp() {
        return (long) session.getAttribute(RSA_CREATE_TIMESTAMP_KEY_NAME);
    }

    public void setRsaCreateTimestamp(long RSACreateTimestamp) {
        session.setAttribute(RSA_CREATE_TIMESTAMP_KEY_NAME, RSACreateTimestamp);
    }
}
