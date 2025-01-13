package com.example.vacationplanner;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

import android.app.Application;
import android.os.Bundle;

import androidx.lifecycle.LiveData;

import com.example.vacationplanner.UI.VacationDetails;
import com.example.vacationplanner.database.Repository;
import com.example.vacationplanner.entities.Excursion;
import com.example.vacationplanner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //setVacation
    @DisplayName("Test for checking the setVacation Method")
    @Test
    public void setVacation() {
        Vacation vacation = new Vacation();
        String Name = "Alaska";
        vacation.setVacationName(Name);
        assertEquals("Vacation Name Should Match", Name,vacation.getVacationName());
    }

    //vacationID and excursion vacationID match
    @DisplayName("Test for checking if setVacationID works for the vacation method as well as checks if the getVacationID method works for excursion and vacation classes")
    @Test
    public void matchID() {
        Vacation vacation = new Vacation();
        Excursion excursion = new Excursion(5, "Skydive", "1/12/25", 1);
        int vacationID = 1;
        vacation.setVacationID(vacationID);
        assertEquals("Excursion vacation ID should match the VacationID",excursion.getVacationID(), vacation.getVacationID());
    }

    @DisplayName("Test for checking the getExcursionID method")
    @Test
    public void getExcursionID() {
        Excursion excursion = new Excursion();
        int ID = 1;
        excursion.setExcursionID(ID);
        assertEquals("Excursion ID should be correct", 1, excursion.getExcursionID());
    }



}