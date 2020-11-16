package com.github.merkurevsergei.model;

import java.time.LocalDateTime;

public class Event {
    private LocalDateTime date;
    private String status;

    public Event(LocalDateTime date, String status) {

        this.date = date;
        this.status = status;
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
