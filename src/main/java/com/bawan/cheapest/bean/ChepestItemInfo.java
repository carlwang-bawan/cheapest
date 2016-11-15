package com.bawan.cheapest.bean;

import java.io.Serializable;

/**
 * Created by wangzhen on 2016/11/11.
 */
public class ChepestItemInfo implements Serializable {
    private String href;
    private String data_price;
    private String src;
    private String fromWebSite;

    public String getFromWebSite() {
        return fromWebSite;
    }

    public void setFromWebSite(String fromWebSite) {
        this.fromWebSite = fromWebSite;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getData_price() {
        return data_price;
    }

    public void setData_price(String data_price) {
        this.data_price = data_price;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
