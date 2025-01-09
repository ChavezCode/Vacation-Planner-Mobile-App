package com.example.vacationplanner.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplanner.entities.Vacation;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private List<Vacation> allVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public ReportAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    public class ReportViewHolder extends RecyclerView.ViewHolder{

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            //get textviews from reportview
        }
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
