package com.ahmedosman.tripplanner;

import java.io.Serializable;

/**
 * Created by Ahmed on 07-Feb-18.
 */

public class Trip implements Serializable{

    private String userName;
    private String tripName;
    private String startPoint;
    private String endPoint;
    private Integer reminder;
    private Boolean roundTrip;
    private String tripImage;
    private String notes;
    private String status;
    private Integer hours;
    private Integer minutes;
    private Integer year;
    private Integer month;
    private Integer day;
    private String timeFormate;
    private Integer tripId;

    public String getUserName() {
        return userName;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Integer getReminder() {
        return reminder;
    }

    public void setReminder(Integer reminder) {
        this.reminder = reminder;
    }

    public Boolean getRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(Boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public String getTripImage() {
        return tripImage;
    }

    public void setTripImage(String tripImage) {
        this.tripImage = tripImage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String[] notes) {
        String resultNotes = null;
        for(String note:notes){
            resultNotes.concat("*?"+note);
        }
        this.notes = resultNotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getTimeFormate() {
        return timeFormate;
    }

    public void setTimeFormate(String timeFormate) {
        this.timeFormate = timeFormate;
    }
}
