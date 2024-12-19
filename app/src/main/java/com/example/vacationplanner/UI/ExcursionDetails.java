package com.example.vacationplanner.UI;

import android.app.DatePickerDialog;
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
    DatePickerDialog.OnDateSetListener myStartDate;
    final Calendar myStartCalendar = Calendar.getInstance();

    Boolean validDate;
    Spinner spinner;


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


//////////Spinner code. Commented out, not needed
//        Spinner spinner = findViewById(R.id.spinner);
//        List<Vacation> vacationList = repository.getmAllVacations();
//        ArrayAdapter<Vacation> vacationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vacationList);
//        vacationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        spinner.setAdapter(vacationAdapter);

        // Set the spinner to default to the vacationID passed from the previous activity
//        if (vacationID != -1) {
//            for (int i = 0; i < vacationList.size(); i++) {
//                if (vacationList.get(i).getVacationID() == vacationID) {
//                    spinner.setSelection(i); // Set the spinner's default selected item
//                    break;
//                }
//            }
//        }
//
//        //an onItemSelectedListener interface defined an onItemSelected(); callback that is called when an item is selected
//        //has four parameters
//        //parent - The AdapterView where the selection happened
//        //view - The view within the AdapterView that was selected
//        //position - The position of the view in the adapter
//        //id - The row id of the selected item
//        spinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                Vacation selectedVacation = (Vacation) parentView.getItemAtPosition(position);
//                vacationID = selectedVacation.getVacationID();  // Update vacationID when selected item changes
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

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
            Excursion excursion;
            if (excursionID == -1) {
                if (repository.getmAllExcursion().size() == 0) {
                    //then it's a new excursion if it's zero
                    excursionID = 1;
                } else {
                    //last id in db plus one
                    excursionID = repository.getmAllExcursion().get(repository.getmAllExcursion().size() - 1).getExcursionID() + 1;

                    //make a new excursion item
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
        return true;
    }


}