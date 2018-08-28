package com.aigestudio.wheelpicker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/2/15.
 * 地区的实体类
 */

public class Districts {

    /**
     * -ID : 1
     * -DistrictName : 东城区
     * -CID : 1
     * #text : 东城区
     */

    @SerializedName("-ID")
    private String ID;
    @SerializedName("-DistrictName")
    private String DistrictName;
    @SerializedName("-CID")
    private String CID;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String DistrictName) {
        this.DistrictName = DistrictName;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }
    @Override
    public String toString() {
        return DistrictName == null ? "null" : DistrictName;
    }



}
