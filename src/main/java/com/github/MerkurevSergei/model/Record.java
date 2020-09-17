package com.github.MerkurevSergei.model;

import java.time.LocalDateTime;

public class Record {
    private String address;
    private LocalDateTime date;
    private String status;

    public Record(String address, LocalDateTime date, String status) {
        this.address = address;
        this.date = date;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
