package com.example.vacationplanner.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String hotel;
    private String startDate;
    private String endDate;


    //add constructor


    public Vacation(int vacationID, String vacationName, String hotel, String startDate, String endDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //add getters and setters
    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String toString(){
        return vacationName;
    }
}
