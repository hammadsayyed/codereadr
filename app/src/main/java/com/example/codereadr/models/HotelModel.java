package com.example.codereadr.models;

import java.util.ArrayList;
import java.util.List;

public class HotelModel {
    private List<Business> businesses = new ArrayList<Business>();
    private Integer total;
    private Region region;
    public List<Business> getBusinesses() {
        return businesses;
    }
    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }
    public void addBusinesses(List<Business> businesses) {
        this.businesses.addAll(businesses);
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }
}
