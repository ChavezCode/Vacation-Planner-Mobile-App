package com.example.vacationplanner;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

import com.example.vacationplanner.entities.Excursion;
import com.example.vacationplanner.entities.Vacation;

public class UnitTest {
    //setVacation
    @DisplayName("Test for checking the setVacation Method")
    @Test
    public void setVacation() {
        Vacation vacation = new Vacation();
        String Name = "Alaska";
        vacation.setVacationName(Name);
        assertNotNull("Vacation Name Should Not Be Empty",vacation.getVacationName());
        assertEquals(Name, vacation.getVacationName());
        assertEquals(Name, vacation.getVacationName());
        System.out.println("setVacation test completed successfully");
    }

    //vacationID and excursion vacationID match
    @DisplayName("Test for checking if setVacationID, and getVacationID method works for excursion and vacation classes")
    @Test
    public void matchID() {
        Vacation vacation = new Vacation();
        Excursion excursion = new Excursion(5, "Skydive", "1/12/25", 1);
        int vacationID = 1;
        vacation.setVacationID(vacationID);
        assertNotNull("VacationID not null", excursion.getVacationID());
        assertEquals(excursion.getVacationID(), vacation.getVacationID());
        System.out.println("setVacationID and getVacationID test completed successfully");
    }

    @DisplayName("Test for checking the getExcursionID method")
    @Test
    public void getExcursionID() {
        Excursion excursion = new Excursion();
        int ID = 1;
        excursion.setExcursionID(ID);
        assertEquals("Excursion ID should be correct", 1, excursion.getExcursionID());
        System.out.println("getExcursionID test completed successfully");
    }
}