package com.example.vacationplanner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplanner.R;
import com.example.vacationplanner.database.Repository;
import com.example.vacationplanner.entities.Excursion;
import com.example.vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    //we need excursionID to check if the excursion exists.
    List<Excursion> getAssociatedExcursion;
    //we need the edit text info in the vacation detail layout
    int vacationID;
    String vacationName;
    String hotel;
    String startDate;
    String endDate;
    EditText editName;
    EditText editHotel;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
    Button startButton;
    Button endButton;
    DatePickerDialog.OnDateSetListener myStartDate;
    DatePickerDialog.OnDateSetListener myEndDate;
    final Calendar myStartCalendar = Calendar.getInstance();
    final Calendar myEndCalendar = Calendar.getInstance();

    //DATE VALIDATION
    Boolean validDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });



        FloatingActionButton fabExcursion = findViewById(R.id.fabVExcursionList);

        //start calendar button
        startButton = findViewById(R.id.startdate);
        endButton = findViewById(R.id.enddate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        //endbutton onclicklistener
        endButton.setText(currentDate);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = endButton.getText().toString();
                try {
                    myEndCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();

                }
                new DatePickerDialog(VacationDetails.this, myEndDate, myEndCalendar.get(Calendar.YEAR),
                        myEndCalendar.get(Calendar.MONTH), myEndCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        //start button on click listener
        startButton.setText(currentDate);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = startButton.getText().toString();
                try {
                    myStartCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                    ;
                }
                new DatePickerDialog(VacationDetails.this, myStartDate, myStartCalendar.get(Calendar.YEAR),
                        myStartCalendar.get(Calendar.MONTH), myStartCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        //startdate
        myStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myStartCalendar.set(Calendar.YEAR, year);
                myStartCalendar.set(Calendar.MONTH, month);
                myStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        myEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myEndCalendar.set(Calendar.YEAR, year);
                myEndCalendar.set(Calendar.MONTH, month);
                myEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        editName = findViewById(R.id.place);
        editHotel = findViewById(R.id.hotel);
        vacationID = getIntent().getIntExtra("id", -1);
        //need to change the editStartDate and editEndDate not needed for calendar button
//        editStartDate = findViewById(R.id.startdate);
//        editEndDate = findViewById(R.id.enddate);

        //now get info from recycler and have it populate on the vacation details page when clicked
        vacationName = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        startDate = getIntent().getStringExtra("startdate");
        endDate = getIntent().getStringExtra("enddate");

        editName.setText(vacationName);
        editHotel.setText(hotel);

        startButton.setText(startDate);

        endButton.setText(endDate);


//opens excursion details page
        fabExcursion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                //pass the vacationID when selecting an excursion
                intent.putExtra("vacaID", vacationID);
                startActivity(intent);
            }
        });

        //turns on recycler view in vacation list and details
        RecyclerView recyclerView = findViewById(R.id.vacationrecyclerview);
        repository = new Repository(getApplication());
        List<Excursion> allExcursions = repository.getmAllExcursion();
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //to filter by ID
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion ex : repository.getmAllExcursion()) {
            if (ex.getVacationID() == vacationID) {
                filteredExcursions.add(ex);
            }
        }
        //only excursions that are in the current vacation item
        excursionAdapter.setExcurions(filteredExcursions);
        //gets all excursions
        //excursionAdapter.setExcursions(allExcursions);


    }

    //update date picked for calendar button
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

//        editStartDate.setText(sdf.format(myCalendar.getTime()));
        startButton.setText(sdf.format(myStartCalendar.getTime()));

        endButton.setText(sdf.format(myEndCalendar.getTime()));


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        validDate = myEndCalendar.after(myStartCalendar);
        if (item.getItemId() == R.id.savevacation) {
            //date validation
            if(!validDate){
                Toast.makeText(VacationDetails.this, "Make sure your start day is before your end date or that your end date is after your start date!", Toast.LENGTH_LONG).show();
                return false;
            }
            Vacation vacation;
            //if id = -1 we want to make it a new item
            if (vacationID == -1) {


                //we need to know what Id it should be
                //find the last item of the repo if nothing exists

                if (repository.getmAllVacations().size() == 0) {
                    //set id = to 1
                    vacationID = 1;
                } else {
                    //make it the last  ID in the database plus 1
                    vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                }
                //make new vacation
                vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), startButton.getText().toString(), endButton.getText().toString());
                repository.insert(vacation);
                this.finish();



            }

            //if vacationID exists update the item
            else {
                //original
//                vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
//                repository.update(vacation);
                //new version with calendar button
                //DATE VALIDATION
                if (validDate) {
                    vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), startButton.getText().toString(), endButton.getText().toString());
                    repository.update(vacation);
                    this.finish();
                } else {
                    Toast.makeText(VacationDetails.this, "Make sure your start day is before your end date or that your end date is after your start date!", Toast.LENGTH_LONG).show();

                }
            }

        }

        //if the menu option is delete
        else if (item.getItemId() == R.id.deletevacation) {
            Vacation vacation;


            //check to see if an excursion item exists
            if (repository.getAssociatedExcursion(vacationID).size() != 0) {
                Toast.makeText(VacationDetails.this, "You cannot delete vacations with associated excursions!", Toast.LENGTH_LONG).show();
                System.out.println(getIntent().getStringExtra("excursions exist"));

            } else {
                //delete if no associated excursions

                vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), startButton.getText().toString(), endButton.getText().toString());
                repository.delete(vacation);
                Toast.makeText(VacationDetails.this, vacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
            }


        }
        //notification for vacation (START and End)
        if (item.getItemId() == R.id.notify){
            Vacation vacation;
            vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), startButton.getText().toString(), endButton.getText().toString());
            //pull date from the string for START DATE
            String startDateFromScreen = startButton.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US); //simple date format has good method for making mils (milliseconds since start of time)
            Date myStartDate = null;
            try {
                myStartDate = sdf.parse(startDateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long startTrigger = myStartDate.getTime();
            //intent that goes to broadcast receiver
            Intent intentStart = new Intent(VacationDetails.this, MyReceiver.class);
            //will need to create a vacation end later
            intentStart.putExtra("start", "Your vacation to " + vacation.getVacationName() + " is starting!");
            //numVacStartAlert has to be different for each alert sent
            PendingIntent senderStart =PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numVacAlert, intentStart, PendingIntent.FLAG_IMMUTABLE); //if FLAG_IMMUTABLE does not work, try FLAG_ONE_SHOT
            //get alarm to show on app
            AlarmManager alarmStartManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmStartManager.set(AlarmManager.RTC_WAKEUP, startTrigger, senderStart);


            //pull date from the string for END DATE
            String endDateFromScreen = endButton.getText().toString();
            Date myEndDate = null;
            try {
                myEndDate = sdf.parse(endDateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long endTrigger = myEndDate.getTime();
            //intent that goes to broadcast receiver
            Intent intentEnd = new Intent(VacationDetails.this, MyReceiver.class);
            //will need to create a vacation end later
            intentEnd.putExtra("end", "Your vacation to " + vacation.getVacationName() + " ends today!");
            //numVacStartAlert has to be different for each alert sent
            PendingIntent senderEnd =PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numVacAlert, intentEnd, PendingIntent.FLAG_IMMUTABLE); //if FLAG_IMMUTABLE does not work, try FLAG_ONE_SHOT
            //get alarm to show on app
            AlarmManager alarmEndManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmEndManager.set(AlarmManager.RTC_WAKEUP, endTrigger, senderEnd);


            return true;
        }
        //sharing vacation details
        if (item.getItemId() == R.id.sharing) {
            Vacation vacation;
            vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), startButton.getText().toString(), endButton.getText().toString());
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Vacation name: " + vacation.getVacationName() +
                    " Vacation Date range: " + vacation.getStartDate() + " - " + vacation.getEndDate() +
                    " Will be staying at: " + vacation.getHotel() + ".");
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Vacation Details to " + vacation.getVacationName());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }


        return true;
    }
}