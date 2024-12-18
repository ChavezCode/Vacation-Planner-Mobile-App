package com.example.vacationplanner.database;

import android.app.Application;

import com.example.vacationplanner.dao.ExcursionDAO;
import com.example.vacationplanner.dao.VacationDAO;
import com.example.vacationplanner.entities.Excursion;
import com.example.vacationplanner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private VacationDAO mVacationDAO;
    private ExcursionDAO mExcursionDAO;

    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursion;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //run database builder to get instance of db. if db exists, then we will get existing instance of db.
    public Repository(Application application) {
        VacationDatabaseBuilder db=VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO=db.excursionDAO();
        mVacationDAO= db.vacationDAO();

    }

    //to get things from db, use executor

    public List<Vacation> getmAllVacations(){
        databaseExecutor.execute(() ->{
            mAllVacations=mVacationDAO.getAllVacations();
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVacations;
    }

    //insert vacation
    public void insert(Vacation vacation){
        databaseExecutor.execute(() ->{
            mVacationDAO.insert(vacation);
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    //update vacation
    public void update(Vacation vacation){
        databaseExecutor.execute(() ->{
            mVacationDAO.update(vacation);
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    //delete vacation
    public void delete(Vacation vacation){
        databaseExecutor.execute(() ->{
            mVacationDAO.delete(vacation);
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //all excursions
    public List<Excursion> getmAllExcursion(){
        databaseExecutor.execute(() ->{
            mAllExcursion=mExcursionDAO.getAllExcursions();
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursion;
    }

    //associated excursions
    public List<Excursion> getAssociatedExcursion(int vacationID){
        databaseExecutor.execute(() ->{
            mAllExcursion=mExcursionDAO.getAssociatedExcursions(vacationID);
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursion;
    }




    //insert excursion
    public void insert(Excursion excursion){
        databaseExecutor.execute(() ->{
            mExcursionDAO.insert(excursion);
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //update excursion
    public void update(Excursion excursion){
        databaseExecutor.execute(() ->{
            mExcursionDAO.update(excursion);
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //delete excursion
    public void delete(Excursion excursion){
        databaseExecutor.execute(() ->{
            mExcursionDAO.delete(excursion);
        });

        //give the executor some time to run and return list of vacations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
