package com.example.vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.List;

public class VacationList extends AppCompatActivity {
private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fabVacation = findViewById(R.id.fabVacationDetails);
        fabVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        //query the db so define the repository
        repository=new Repository(getApplication());
        //get list of all the vacations
        List<Vacation> allVacations=repository.getmAllVacations();
        final VacationAdapter vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //put list of vacations onto recyclerview
        vacationAdapter.setVacations(allVacations);

        //to display the intent message in the main Activity class
        //System.out.println(getIntent().getStringExtra("test"));

    }
    //menu created in res file, adding it to the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;

    }

    //want the screen to update after items have been added to the vacation list
    @Override
    public void onResume(){

        super.onResume();
        //on resume gets products from the db and ads them to the recyclerview again (kinda like a refresh)
        List<Vacation> allVacations=repository.getmAllVacations();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    //to work with things on the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.sample){
            //adding sample data
            repository=new Repository(getApplication());
            //notification in app
//            Toast.makeText(VacationList.this, "put in sample data", Toast.LENGTH_LONG).show();
            Vacation vacation=new Vacation(0,"Hawaii", "HolidayInn", "11/12/12","11/12/12");
            repository.insert(vacation);
            vacation=new Vacation(0,"New York", "HolidayInn", "11/12/12","11/12/12");
            Excursion excursion=new Excursion(0,"kayak", "11/12/12", 12);
            repository.insert(excursion);
            excursion=new Excursion(0,"snorkel", "11/12/12", 12);
            repository.insert(excursion);

            return true;
        }
        //if we had a back arrow, we can use android built in ones.
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
        
        

    }
}