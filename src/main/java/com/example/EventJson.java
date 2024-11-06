package com.example;

public class EventJson {
    private String eventName;
    private String startTime;
    private String startTimeZone;
    private String endTime;
    private String endTimeZone;
    private String targetTimeZone;

    // Getters and setters

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeZone() {
        return startTimeZone;
    }

    public void setStartTimeZone(String startTimeZone) {
        this.startTimeZone = startTimeZone;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeZone() {
        return endTimeZone;
    }

    public void setEndTimeZone(String endTimeZone) {
        this.endTimeZone = endTimeZone;
    }

    public String getTargetTimeZone() {
        return targetTimeZone;
    }

    public void setTargetTimeZone(String targetTimeZone) {
        this.targetTimeZone = targetTimeZone;
    }
}
