package com.example.vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

public class VacationList extends AppCompatActivity {
private Repository repository;
private SearchView searchView;
private List<Vacation> vacationList;
private VacationAdapter vacationAdapter;


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
        //query the db so define the repository
        repository=new Repository(getApplication());
        //adding searchview
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();//removes cursor from searchview
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        //get list of all the vacations
        vacationList=repository.getmAllVacations();
        vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //put list of vacations onto recyclerview
        vacationAdapter.setVacations(vacationList);

        //to display the intent message in the main Activity class
        //System.out.println(getIntent().getStringExtra("test"));

    }
//method to filter vacation list for search
    private void filterList(String text) {
        //show all items if text is empty
//        if (text.isEmpty()) {
//            vacationAdapter.setFilteredList(vacationList);
//            return;
//        }
        //search
        List<Vacation> filteredList = new ArrayList<>();
        for (Vacation vacation : vacationList)
            if (vacation.getVacationName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(vacation);
            }
        if (filteredList.isEmpty()){
            Toast.makeText(this,"No items found", Toast.LENGTH_SHORT).show();

        }else{
            vacationAdapter.setFilteredList(filteredList);

        }
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
        //on resume gets products from the db and adds them to the recyclerview again (kinda like a refresh)
        vacationList=repository.getmAllVacations();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(vacationList);

    }


    //to work with things on the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.sample){
            //adding sample data
            repository=new Repository(getApplication());
            //notification in app
//            Toast.makeText(VacationList.this, "put in sample data", Toast.LENGTH_LONG).show();
            Vacation vacation=new Vacation(0,"Hawaii", "HolidayInn", "12/21/24","12/31/24");
            repository.insert(vacation);
            vacation=new Vacation(0,"New York", "HolidayInn", "12/21/24","12/31/24");
            Excursion excursion=new Excursion(0,"kayak", "12/25/24", vacation.getVacationID());
            repository.insert(excursion);
            excursion=new Excursion(0,"snorkel", "12/25/24", vacation.getVacationID());
            repository.insert(excursion);

            return true;
        }
        //if we had a back arrow, we can use android built in ones.
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }

        //generate a report
        if (item.getItemId()==R.id.report){
            Intent intent = new Intent(VacationList.this, Report.class);
            startActivity(intent);
        }
        return true;



    }
}