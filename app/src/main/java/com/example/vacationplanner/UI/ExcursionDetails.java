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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacationplanner.R;
import com.example.vacationplanner.database.Repository;
import com.example.vacationplanner.entities.Excursion;
import com.example.vacationplanner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    private Repository repository;
    int vacationID;
    int excursionID;
    String excursionName;
    String date;
    EditText editName;
    Button startButton;
    String vacaStartDate;
    String vacaEndDate;
    DatePickerDialog.OnDateSetListener myStartDate;
    final Calendar myStartCalendar = Calendar.getInstance();
    final Calendar myVacStartCalendar = Calendar.getInstance();
    final Calendar myVacEndCalendar = Calendar.getInstance();

    Boolean validDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startButton = findViewById(R.id.startDateExcursion);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        //on click listener
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
                }
                new DatePickerDialog(ExcursionDetails.this, myStartDate, myStartCalendar.get(Calendar.YEAR)
                        , myStartCalendar.get(Calendar.MONTH), myStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //format and set each year, month, day
        myStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myStartCalendar.set(Calendar.YEAR, year);
                myStartCalendar.set(Calendar.MONTH, month);
                myStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        repository = new Repository(getApplication());
        editName = findViewById(R.id.excursionName);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacaID", -1);
        //populate info from recycler into details page
        excursionName = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");

        //Testing, Dates not passing through if excursions clicked on recycler view
        // Get start and end dates from the Intent as strings
        vacaStartDate = getIntent().getStringExtra("startDates");
        vacaEndDate = getIntent().getStringExtra("endDates");

        // Ensure that the dates are not null or empty
        if (vacaStartDate != null && !vacaStartDate.isEmpty()) {
            try {
                myVacStartCalendar.setTime(sdf.parse(vacaStartDate));
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid start date format", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Start date is missing", Toast.LENGTH_SHORT).show();
        }

        if (vacaEndDate != null && !vacaEndDate.isEmpty()) {
            try {
                myVacEndCalendar.setTime(sdf.parse(vacaEndDate));
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid end date format", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "End date is missing", Toast.LENGTH_SHORT).show();
        }


        //set excursion name and date
        editName.setText(excursionName);
        startButton.setText(date);
    }

    //method to update button label once date is picked
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startButton.setText(sdf.format(myStartCalendar.getTime()));
    }

    //bring in the menu items
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_list, menu);
        return true;
    }


    //menu item features
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //add validation to make sure start date is within vacation date range

        if (item.getItemId() == R.id.excursionSave) {
            //if excursion is a new item
            validDate = myStartCalendar.after(myVacStartCalendar) && myStartCalendar.before(myVacEndCalendar);
            if (!validDate) {
                Toast.makeText(ExcursionDetails.this, "Start date does not fall within your vacation dates!", Toast.LENGTH_LONG).show();
                return false;
            }
            Excursion excursion;
            if (excursionID == -1) {
                if (repository.getmAllExcursion().size() == 0) {
                    //then it's a new excursion if it's zero
                    excursionID = 1;
                    //make a new excursion item
                    excursion = new Excursion(excursionID, editName.getText().toString(), startButton.getText().toString(), vacationID);
                    repository.insert(excursion);
                } else {
                    //last id in db plus one
                    excursionID = repository.getmAllExcursion().get(repository.getmAllExcursion().size() - 1).getExcursionID() + 1;

                    //add excursion to db
                    excursion = new Excursion(excursionID, editName.getText().toString(), startButton.getText().toString(), vacationID);
                    repository.insert(excursion);
                }
            } else {
                //if excursionID exists update

                excursion = new Excursion(excursionID, editName.getText().toString(), startButton.getText().toString(), vacationID);
                repository.update(excursion);
            }
            this.finish();

        }
        //delete excursions
        if (item.getItemId() == R.id.excursionDelete) {
            Excursion excursion;
            excursion = new Excursion(excursionID, editName.getText().toString(), startButton.getText().toString(), vacationID);
            repository.delete(excursion);
            this.finish();
        }
//        set alert for excursion
        if (item.getItemId() == R.id.excursionAlert) {
            Excursion excursion;
            excursion = new Excursion(excursionID, editName.getText().toString(), startButton.getText().toString(), vacationID);
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
            Intent intentStart = new Intent(ExcursionDetails.this, MyReceiver.class);
            //will need to create a vacation end later
            intentStart.putExtra("start", "Your excursion " + excursion.getExcursionName() + " is starting!");
            //numVacStartAlert has to be different for each alert sent
            PendingIntent senderStart = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numVacAlert, intentStart, PendingIntent.FLAG_IMMUTABLE); //if FLAG_IMMUTABLE does not work, try FLAG_ONE_SHOT
            //get alarm to show on app
            AlarmManager alarmStartManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmStartManager.set(AlarmManager.RTC_WAKEUP, startTrigger, senderStart);
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }


}