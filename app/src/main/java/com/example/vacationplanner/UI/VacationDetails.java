package com.example.vacationplanner.UI;

import android.content.EntityIterator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

import java.util.ArrayList;
import java.util.List;

public class VacationDetails extends AppCompatActivity {
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


        editName = findViewById(R.id.place);
        editHotel = findViewById(R.id.hotel);
        vacationID = getIntent().getIntExtra("id", -1);
        editStartDate = findViewById(R.id.startdate);
        editEndDate = findViewById(R.id.enddate);

        //now get info from recycler and have it populate on the vacation details page when clicked
        vacationName = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        startDate = getIntent().getStringExtra("startdate");
        endDate = getIntent().getStringExtra("enddate");

        editName.setText(vacationName);
        editHotel.setText(hotel);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        fabExcursion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionList.class);
                startActivity(intent);
            }
        });
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
        //excursionAdapter.setExcurions(allExcursions);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.savenew) {
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

                    //make a new vacation
                    vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repository.insert(vacation);
                    //close the screen
                    this.finish();

                }
            }
            //if we're modifying the vacation
            else {
                vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }

        }
        return true;
    }
}