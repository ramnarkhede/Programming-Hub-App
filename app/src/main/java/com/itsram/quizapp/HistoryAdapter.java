package com.itsram.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    List<HistoryData> list
            = Collections.emptyList();

    public HistoryAdapter(List<HistoryData>list)
    {
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //iewHolder.examName
        //            .setText(list.get(position).name);
        holder.date.setText(list.get(position).date);
        holder.catSet.setText(list.get(position).catSet);
        holder.score.setText(list.get(position).score);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date,score,catSet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.dateAndTime);
            score=itemView.findViewById(R.id.scr);
            catSet=itemView.findViewById(R.id.catSet);
        }

    }
}
