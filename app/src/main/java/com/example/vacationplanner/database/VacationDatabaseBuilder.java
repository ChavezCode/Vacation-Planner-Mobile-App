package com.example.vacationplanner.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vacationplanner.dao.ExcursionDAO;
import com.example.vacationplanner.dao.VacationDAO;
import com.example.vacationplanner.entities.Excursion;
import com.example.vacationplanner.entities.Vacation;


//need to build the enetities first to build db
@Database(entities = {Vacation.class, Excursion.class}, version = 4, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    //insert vacationDao, excursiondao and instance of DB to build it
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    public static volatile VacationDatabaseBuilder INSTANCE;

    //syncrhonous or asynchronous db
    static VacationDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null) {
            synchronized (VacationDatabaseBuilder.class){
                if (INSTANCE==null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),VacationDatabaseBuilder.class, "MyVacationDatabase.DB")
                            .fallbackToDestructiveMigration()
                            //to create a syncrhonouse DB
                            //.allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
