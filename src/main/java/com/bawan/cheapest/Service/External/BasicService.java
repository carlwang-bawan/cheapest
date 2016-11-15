package com.bawan.cheapest.Service.External;

import com.bawan.cheapest.bean.ChepestItemInfo;

import java.io.Serializable;

/**
 * Created by wangzhen on 2016/11/11.
 */
public abstract class BasicService implements Serializable {
    public abstract ChepestItemInfo getCheapest (String webSit,String keyword,String itemUrl);
}
