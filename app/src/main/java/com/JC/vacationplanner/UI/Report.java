package com.example.vacationplanner.UI;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplanner.R;
import com.example.vacationplanner.database.Repository;
import com.example.vacationplanner.entities.Vacation;

import java.util.List;

public class Report extends AppCompatActivity {
    private ReportAdapter reportAdapter;
    @Override
    protected void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Repository repository = new Repository(getApplication());
        RecyclerView recyclerView=findViewById(R.id.recyclerReport);
        List<Vacation> allVacations=repository.getmAllVacations();
        reportAdapter=new ReportAdapter(this);
        recyclerView.setAdapter(reportAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter.setReport(allVacations);

    }
}
