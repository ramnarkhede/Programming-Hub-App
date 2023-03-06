package com.itsram.quizapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataStructureAdapter extends RecyclerView.Adapter<DataStructureAdapter.myviewHolder> {

    private List<DataStructureModel> dataStructureModelsList;

    public DataStructureAdapter(List<DataStructureModel> dataStructureModelsList) {
        this.dataStructureModelsList = dataStructureModelsList;
    }


    @NonNull
    @Override
    public DataStructureAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_theory,parent,false);
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataStructureAdapter.myviewHolder holder, int position) {
        holder.setData(dataStructureModelsList.get(position).getFilename(),dataStructureModelsList.get(position).getFileurl());
    }

    @Override
    public int getItemCount() {
        return dataStructureModelsList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        TextView filename;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            filename=itemView.findViewById(R.id.filename);

        }
        private void setData(final String filename, final String fileurl){

            this.filename.setText(filename);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent setIntent=new Intent(itemView.getContext(),PdfViewActivity.class);
                    setIntent.putExtra("filename",filename);
                    setIntent.putExtra("fileurl",fileurl);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }


}
