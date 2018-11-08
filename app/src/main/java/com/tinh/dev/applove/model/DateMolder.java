package com.tinh.dev.applove.model;

public class DateMolder {
    private String day;

    private long ngaykiniem;

    public DateMolder(String day, long ngaykiniem) {
        this.day = day;
        this.ngaykiniem = ngaykiniem;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getNgaykiniem() {
        return ngaykiniem;
    }

    public void setNgaykiniem(long ngaykiniem) {
        this.ngaykiniem = ngaykiniem;
    }
}
