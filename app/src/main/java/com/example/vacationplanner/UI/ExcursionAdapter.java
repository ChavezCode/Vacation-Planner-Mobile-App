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
import com.example.vacationplanner.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        //we have two text views on the excursion list item layout
        private final TextView excursionItemView;
        private final TextView excursionItemView2;


        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView=itemView.findViewById(R.id.textView9);
            excursionItemView2=itemView.findViewById(R.id.textView11);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final Excursion current=mExcursions.get(position);
                    Intent intent=new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("name", current.getExcursionName());
                    intent.putExtra("date", current.getDate());
                    intent.putExtra("vacaID", current.getVacationID());
                    context.startActivity(intent);
                }
            });
        }
    }

    public ExcursionAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.excursion_list_item, parent, false);

        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if(mExcursions!=null){
            Excursion current=mExcursions.get(position);
            String name=current.getExcursionName();
            int vacaID= current.getVacationID();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(Integer.toString(vacaID));
        }
        else{
            holder.excursionItemView.setText("No Excursion Name");
            holder.excursionItemView2.setText("No Vacation ID");
        }
    }

    @Override
    public int getItemCount() {
        if(mExcursions!=null){
            return mExcursions.size();
        }
        else return 0;
    }

    public void setExcurions(List<Excursion> excursions){
        mExcursions=excursions;
        notifyDataSetChanged();
    }


}
