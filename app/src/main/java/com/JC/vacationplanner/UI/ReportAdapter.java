package com.JC.vacationplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.JC.vacationplanner.R;
import com.JC.vacationplanner.entities.Vacation;

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
        private final TextView reportName;
        private final TextView reportStay;
        private final TextView reportStart;
        private final TextView reportEnd;


        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            //get textviews from reportview

            reportName = itemView.findViewById(R.id.reportViewName);
            reportStay = itemView.findViewById(R.id.reportViewStay);
            reportStart = itemView.findViewById(R.id.reportViewStartDate);
            reportEnd = itemView.findViewById(R.id.reportViewEndDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final Vacation current=allVacations.get(position);
                    Intent intent = new Intent(context,Report.class);
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("name", current.getVacationName());
                    intent.putExtra("hotel", current.getHotel());
                    intent.putExtra("startdate",current.getStartDate());
                    intent.putExtra("enddate", current.getEndDate());
                    //start activity
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        if(allVacations!=null){
            Vacation current=allVacations.get(position);
            String name=current.getVacationName();
            String stay=current.getHotel();
            String start=current.getStartDate();
            String end=current.getEndDate();
            holder.reportName.setText(name);
            holder.reportStay.setText(stay);
            holder.reportStart.setText(start);
            holder.reportEnd.setText(end);
        }
        else{
            holder.reportName.setText("No Vacations");
        }

    }

    @Override
    public int getItemCount() {
        if (allVacations!=null){
            return allVacations.size();
        }else {
            return 0;
        }
    }
    public void setReport(List<Vacation> vacations){
        allVacations=vacations;
        notifyDataSetChanged();
    }


}
