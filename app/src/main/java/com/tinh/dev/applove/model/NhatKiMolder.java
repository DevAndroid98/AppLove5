package com.tinh.dev.applove.model;

public class NhatKiMolder {
    private long ngay;
    private String nhatki;
    private String links;
    private int id;

    public NhatKiMolder(long ngay, String nhatki, String links, int id) {
        this.ngay = ngay;
        this.nhatki = nhatki;
        this.links = links;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNgay() {
        return ngay;
    }

    public void setNgay(long ngay) {
        this.ngay = ngay;
    }

    public String getNhatki() {
        return nhatki;
    }

    public void setNhatki(String nhatki) {
        this.nhatki = nhatki;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }


}
