package com.nelson.mvplibrary.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @author nelson
 */
public class KeyScore extends LitePalSupport {
    private String keyName;
    private int keyScore;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getKeyScore() {
        return keyScore;
    }

    public void setKeyScore(int keyScore) {
        this.keyScore = keyScore;
    }
}
