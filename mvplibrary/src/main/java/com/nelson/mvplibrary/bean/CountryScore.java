package com.nelson.mvplibrary.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @author nelson
 */
public class CountryScore extends LitePalSupport {
    private String countryName;//名称

    private int countryScore;//代码

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryScore() {
        return countryScore;
    }

    public void setCountryScore(int countryScore) {
        this.countryScore = countryScore;
    }
}
