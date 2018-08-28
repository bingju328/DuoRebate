package com.aigestudio.wheelpicker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/2/15.
 * 市的实体类
 */

public class City {

    /**
     * -ID : 1
     * -CityName : 北京市
     * -PID : 1
     * -ZipCode : 100000
     * #text : 北京市
     */

    @SerializedName("-ID")
    private String ID;
    @SerializedName("-CityName")
    private String CityName;
    @SerializedName("-PID")
    private String PID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    @Override
    public String toString() {
        return CityName == null ? "null" : CityName;
    }




}
