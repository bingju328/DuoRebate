package com.aigestudio.wheelpicker.model;

/**
 * Created by Administrator on 2017/2/15.
 * 省的实体类
 */

public class Province {
    public String ID;
    public String ProvinceName;

    public Province(String id, String provinceName) {
        this.ID = id;
        ProvinceName = provinceName;
    }

    public Province() {
        super();
    }

    public void setid(String id){
        this.ID=id;
    }
    public void setprovinceName(String ProvinceName){
        this.ProvinceName=ProvinceName;
    }
    public String getid(){
        return this.ID;
    }
    public String getProvinceName(){
        return this.ProvinceName;
    }

    @Override
    public String toString() {
        return ProvinceName == null ? "null" : ProvinceName;
    }
}
