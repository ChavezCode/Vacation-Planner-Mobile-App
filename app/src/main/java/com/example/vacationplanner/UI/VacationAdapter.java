package com.example.vacationplanner.UI;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplanner.R;
import com.example.vacationplanner.entities.Vacation;

import java.util.List;

//to help with displaying vacation list with recycler view
public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    //list of vacations for setting vacation items from activity into recyclerview
    private List<Vacation> mVacations;

    //delcare context
    private final Context context;

    //declare layoutinlfater
    private final LayoutInflater mInflater;
    //get context from layout inflater
    public VacationAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        //add the textview that is on the list item layout
        private final TextView vacationItemView;
        //if you have two text views like we do for excursions, you would list both

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            //from the vacation_list_item layout
            vacationItemView=itemView.findViewById(R.id.textView8);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get position clicked on
                    int position=getAdapterPosition();
                    //find current vacation and find the list you clicked
                    final Vacation current=mVacations.get(position);
                    //go to details activity screen
                    Intent intent=new Intent(context,VacationDetails.class);
                    //what information do I want to send over?
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
    //adapter has 3 methods that are needed and populated
    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.vacation_list_item,parent, false);
        return new VacationViewHolder(itemView);
    }

    //what we display on recycler view
    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations!=null){
            Vacation current=mVacations.get(position);
            String name=current.getVacationName();
            holder.vacationItemView.setText(name);
        }
        else{
            holder.vacationItemView.setText("No Vacation Name");
        }


    }

    //how many items are in the recyclerview
    @Override
    public int getItemCount() {
        //make sure it is not null in case it is never initialized
        if(mVacations!=null) {
            return mVacations.size();
        }
        else return 0;
    }


    //set vacations from the activity into the recyclerview
    public void setVacations(List<Vacation> vacations){
        mVacations=vacations;
        notifyDataSetChanged();
    }


}
